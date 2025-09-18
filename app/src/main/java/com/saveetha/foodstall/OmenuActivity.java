package com.saveetha.foodstall;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.saveetha.foodstall.adapter.OOwnerMenuAdapter;
import com.saveetha.foodstall.model.OGetMenuResponse;
import com.saveetha.foodstall.model.OMenuItem;
import com.saveetha.foodstall.model.StatusResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OmenuActivity extends AppCompatActivity {

    private SwitchMaterial workingTodaySwitch;
    private FrameLayout loadingOverlay;
    private RecyclerView regularItemsRecyclerView, comboDealsRecyclerView;
    private Button saveStallDetailsButton, addMenuItemButton, addComboButton;
    private OOwnerMenuAdapter regularMenuAdapter, comboMenuAdapter;
    private List<OMenuItem> regularItemsList = new ArrayList<>();
    private List<OMenuItem> comboItemsList = new ArrayList<>();
    private String stallId;
    private LinearLayout controlsContainer, operatingHoursContainer;
    private TextView statusMessageTextView;
    private EditText openingTimeEditText, closingTimeEditText;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private CircleImageView currentDialogImageView;
    private Uri selectedImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.omenu);
        SharedPreferences sp = getSharedPreferences("StallSpotPrefs", MODE_PRIVATE);
        stallId = sp.getString("STALL_ID", null);
        if (stallId == null || stallId.isEmpty()) {
            Toast.makeText(this, "Error: Stall ID not found. Please log in again.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        bindViews();
        setupGalleryLauncher();
        setupListeners();
        setupRecyclerViews();
        setupBottomNavigation();
        fetchDataFromServer();
    }

    private void bindViews() {
        workingTodaySwitch = findViewById(R.id.workingTodaySwitch);
        openingTimeEditText = findViewById(R.id.openingTimeEditText);
        closingTimeEditText = findViewById(R.id.closingTimeEditText);
        operatingHoursContainer = findViewById(R.id.operatingHoursContainer);
        saveStallDetailsButton = findViewById(R.id.saveStallDetailsButton);
        addMenuItemButton = findViewById(R.id.addMenuItemButton);
        addComboButton = findViewById(R.id.addComboButton);
        regularItemsRecyclerView = findViewById(R.id.regularItemsRecyclerView);
        comboDealsRecyclerView = findViewById(R.id.comboDealsRecyclerView);
        loadingOverlay = findViewById(R.id.loadingOverlay);
        controlsContainer = findViewById(R.id.controlsContainer);
        statusMessageTextView = findViewById(R.id.statusMessageTextView);
    }

    private void setupGalleryLauncher() {
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        if (currentDialogImageView != null) {
                            currentDialogImageView.setImageURI(selectedImageUri);
                        }
                    }
                });
    }

    private void setupListeners() {
        workingTodaySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> operatingHoursContainer.setVisibility(isChecked ? View.VISIBLE : View.GONE));
        saveStallDetailsButton.setOnClickListener(v -> updateStallStatus());
        addMenuItemButton.setOnClickListener(v -> showAddEditItemDialog(null, "Regular"));
        addComboButton.setOnClickListener(v -> showAddEditItemDialog(null, "Combo"));
        openingTimeEditText.setOnClickListener(v -> showTimePickerDialog(openingTimeEditText));
        closingTimeEditText.setOnClickListener(v -> showTimePickerDialog(closingTimeEditText));
    }

    private void showTimePickerDialog(final EditText timeEditText) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minuteOfHour) -> {
            String amPm = (hourOfDay < 12) ? "AM" : "PM";
            int displayHour = (hourOfDay > 12) ? hourOfDay - 12 : hourOfDay;
            if (displayHour == 0) displayHour = 12;
            String time = String.format(Locale.getDefault(), "%d:%02d %s", displayHour, minuteOfHour, amPm);
            timeEditText.setText(time);
        }, hour, minute, false);
        timePickerDialog.show();
    }

    private void setupRecyclerViews() {
        OOwnerMenuAdapter.OnMenuItemClickListener listener = new OOwnerMenuAdapter.OnMenuItemClickListener() {
            @Override public void onEditClick(OMenuItem item) { showAddEditItemDialog(item, item.getCategory()); }
            @Override public void onDeleteClick(OMenuItem item) { showDeleteConfirmationDialog(item); }
        };
        regularMenuAdapter = new OOwnerMenuAdapter(regularItemsList, listener);
        regularItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        regularItemsRecyclerView.setAdapter(regularMenuAdapter);
        comboMenuAdapter = new OOwnerMenuAdapter(comboItemsList, listener);
        comboDealsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        comboDealsRecyclerView.setAdapter(comboMenuAdapter);
    }

    private void fetchDataFromServer() {
        startLoadingAnimation();
        ApiClient.getClient().create(ApiService.class).getOwnerMenuDetails("get_menu_details", stallId)
                .enqueue(new Callback<OGetMenuResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<OGetMenuResponse> call, @NonNull Response<OGetMenuResponse> response) {
                        stopLoadingAnimation();
                        if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                            OGetMenuResponse.MenuData data = response.body().getData();
                            handleStallStatus(data.getStallDetails());
                            if (data.getStallDetails() != null && data.getStallDetails().getApproval() == 1) {
                                if (data.getMenuItems() != null) {
                                    regularItemsList.clear();
                                    comboItemsList.clear();
                                    for (OMenuItem item : data.getMenuItems()) {
                                        if ("Combo".equalsIgnoreCase(item.getCategory()) || "Today's Special".equalsIgnoreCase(item.getCategory())) {
                                            comboItemsList.add(item);
                                        } else {
                                            regularItemsList.add(item);
                                        }
                                    }
                                    regularMenuAdapter.notifyDataSetChanged();
                                    comboMenuAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            statusMessageTextView.setVisibility(View.VISIBLE);
                            statusMessageTextView.setText("Failed to load menu data.");
                            controlsContainer.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<OGetMenuResponse> call, @NonNull Throwable t) {
                        stopLoadingAnimation();
                        statusMessageTextView.setVisibility(View.VISIBLE);
                        statusMessageTextView.setText("Network Error: " + t.getMessage());
                        controlsContainer.setVisibility(View.GONE);
                    }
                });
    }

    private void handleStallStatus(OGetMenuResponse.StallDetails details) {
        if (details == null) {
            statusMessageTextView.setText("Could not retrieve stall status.");
            statusMessageTextView.setVisibility(View.VISIBLE);
            controlsContainer.setVisibility(View.GONE);
            return;
        }
        if (details.getApproval() == 1) {
            statusMessageTextView.setVisibility(View.GONE);
            controlsContainer.setVisibility(View.VISIBLE);
            workingTodaySwitch.setChecked(details.isOpenToday());
            operatingHoursContainer.setVisibility(details.isOpenToday() ? View.VISIBLE : View.GONE);
            openingTimeEditText.setText(details.getOpeningHours());
            closingTimeEditText.setText(details.getClosingHours());
        } else {
            controlsContainer.setVisibility(View.GONE);
            statusMessageTextView.setVisibility(View.VISIBLE);
            statusMessageTextView.setText(details.getApproval() == 0 ? "Your stall is pending approval.\nMenu management is disabled." : "Your stall application was rejected.\nMenu management is disabled.");
        }
    }

    private void showAddEditItemDialog(final OMenuItem itemToEdit, final String defaultCategory) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_item, null);
        builder.setView(view);

        selectedImageUri = null;
        final CircleImageView dialogImageView = view.findViewById(R.id.dialogItemImageView);
        currentDialogImageView = dialogImageView;
        dialogImageView.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(galleryIntent);
        });

        final EditText nameEditText = view.findViewById(R.id.dialogItemNameEditText);
        final EditText priceEditText = view.findViewById(R.id.dialogItemPriceEditText);
        final AutoCompleteTextView categoryAutoComplete = view.findViewById(R.id.dialogItemCategoryAutoComplete);

        String[] categories = new String[]{"Regular", "Combo", "Today's Special"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, categories);
        categoryAutoComplete.setAdapter(adapter);
        builder.setTitle(itemToEdit == null ? "Add New Item" : "Edit Item");

        if (itemToEdit != null) {
            nameEditText.setText(itemToEdit.getName());
            priceEditText.setText(String.valueOf(itemToEdit.getPrice()));
            categoryAutoComplete.setText(itemToEdit.getCategory(), false);
            if (itemToEdit.getItemImage() != null && !itemToEdit.getItemImage().isEmpty()) {
                Glide.with(this).load(ApiClient.BASE_URL + "uploads/" + itemToEdit.getItemImage()).placeholder(R.drawable.ic_camera_background).into(dialogImageView);
            }
        } else {
            categoryAutoComplete.setText(defaultCategory, false);
        }

        builder.setPositiveButton(itemToEdit == null ? "Add" : "Save", (dialog, which) -> {
            String name = nameEditText.getText().toString().trim();
            String price = priceEditText.getText().toString().trim();
            String category = categoryAutoComplete.getText().toString().trim();
            if (name.isEmpty() || price.isEmpty() || category.isEmpty()) { Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show(); return; }
            if (itemToEdit == null) { addItemToServer(name, price, category, selectedImageUri); }
            else { updateItemOnServer(itemToEdit, name, price, category, selectedImageUri); }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void addItemToServer(String name, String price, String category, @Nullable Uri imageUri) {
        startLoadingAnimation();
        Map<String, RequestBody> fields = new HashMap<>();
        fields.put("action", createPartFromString("add_item"));
        fields.put("stall_id", createPartFromString(stallId));
        fields.put("item_name", createPartFromString(name));
        fields.put("item_price", createPartFromString(price));
        fields.put("item_category", createPartFromString(category));
        MultipartBody.Part imagePart = (imageUri != null) ? prepareFilePart("item_image", imageUri) : null;
        ApiClient.getClient().create(ApiService.class).addMenuItem(fields, imagePart).enqueue(new Cb("Item added!", "Failed to add."));
    }

    private void updateItemOnServer(OMenuItem item, String name, String price, String category, @Nullable Uri imageUri) {
        startLoadingAnimation();
        Map<String, RequestBody> fields = new HashMap<>();
        fields.put("action", createPartFromString("update_item"));
        fields.put("stall_id", createPartFromString(stallId));
        fields.put("item_id", createPartFromString(String.valueOf(item.getItemId())));
        fields.put("item_name", createPartFromString(name));
        fields.put("item_price", createPartFromString(price));
        fields.put("item_category", createPartFromString(category));
        fields.put("existing_image_url", createPartFromString(item.getItemImage() != null ? item.getItemImage() : ""));
        MultipartBody.Part imagePart = (imageUri != null) ? prepareFilePart("item_image", imageUri) : null;
        ApiClient.getClient().create(ApiService.class).updateMenuItem(fields, imagePart).enqueue(new Cb("Item updated!", "Failed to update."));
    }

    private void showDeleteConfirmationDialog(final OMenuItem item) { new AlertDialog.Builder(this).setTitle("Delete Item").setMessage("Delete '" + item.getName() + "'?").setPositiveButton("Delete", (d, w) -> deleteItemFromServer(item)).setNegativeButton("Cancel", null).show(); }
    private void deleteItemFromServer(OMenuItem item) { startLoadingAnimation(); ApiClient.getClient().create(ApiService.class).deleteMenuItem("delete_item", item.getItemId()).enqueue(new Cb("Item deleted.", "Failed to delete.")); }
    private void updateStallStatus() { startLoadingAnimation(); ApiClient.getClient().create(ApiService.class).updateStallStatus("update_stall_status", stallId, workingTodaySwitch.isChecked() ? 1 : 0, openingTimeEditText.getText().toString(), closingTimeEditText.getText().toString()).enqueue(new Cb("Status updated.", "Failed to update status.")); }
    private RequestBody createPartFromString(String s) { return RequestBody.create(MultipartBody.FORM, s != null ? s : ""); }
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        try {
            File file = new File(FileUtils.getPath(this, fileUri));
            RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
            return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
        } catch (Exception e) { e.printStackTrace(); return null; }
    }

    private class Cb implements Callback<StatusResponse> {
        private final String successMsg, errorMsg;
        Cb(String s, String e){ this.successMsg = s; this.errorMsg = e; }
        @Override
        public void onResponse(@NonNull Call<StatusResponse> call, @NonNull Response<StatusResponse> r) {
            stopLoadingAnimation();
            if(r.isSuccessful() && r.body() != null && "success".equals(r.body().getStatus())) {
                Toast.makeText(OmenuActivity.this, successMsg, Toast.LENGTH_SHORT).show();
                fetchDataFromServer();
            } else {
                String errorMessage = errorMsg;
                if (r.body() != null && r.body().getMessage() != null) {
                    errorMessage = r.body().getMessage();
                }
                Toast.makeText(OmenuActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onFailure(@NonNull Call<StatusResponse> call, @NonNull Throwable t) {
            stopLoadingAnimation();
            Toast.makeText(OmenuActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void startLoadingAnimation() {
        if(loadingOverlay != null) {
            ImageView loadingIcon = loadingOverlay.findViewById(R.id.loadingIcon);
            loadingOverlay.setVisibility(View.VISIBLE);
            if (loadingIcon != null) {
                Animation rotation = AnimationUtils.loadAnimation(this, R.anim.hourglass_rotation);
                loadingIcon.startAnimation(rotation);
            }
        }
    }

    private void stopLoadingAnimation() {
        if (loadingOverlay != null) {
            ImageView loadingIcon = loadingOverlay.findViewById(R.id.loadingIcon);
            if(loadingIcon != null) loadingIcon.clearAnimation();
            loadingOverlay.setVisibility(View.GONE);
        }
    }

    private void setupBottomNavigation() {
        BottomNavigationView bnv = findViewById(R.id.bottom_navigation_bar);
        bnv.setSelectedItemId(R.id.nav_owner_menu);
        bnv.setOnItemSelectedListener(item -> {
            int i = item.getItemId();
            if(i==R.id.nav_owner_menu) return true;
            else if(i==R.id.nav_owner_home) { startActivity(new Intent(getApplicationContext(), OhomeActivity.class)); overridePendingTransition(0,0); finish(); return true; }
            else if(i==R.id.nav_owner_orders) { startActivity(new Intent(getApplicationContext(), OordersActivity.class)); overridePendingTransition(0,0); finish(); return true; }
            else if(i==R.id.nav_owner_profile) { startActivity(new Intent(getApplicationContext(), OprofileActivity.class)); overridePendingTransition(0,0); finish(); return true; }
            return false;
        });
    }
}
