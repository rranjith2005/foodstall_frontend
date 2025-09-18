package com.saveetha.foodstall;

import androidx.annotation.Nullable;

// UPDATED: All references to RegisterResponse are now StatusResponse
import com.saveetha.foodstall.model.FavoriteStallsResponse;
import com.saveetha.foodstall.model.OGetMenuResponse;
import com.saveetha.foodstall.model.OrderPlacementResponse;
import com.saveetha.foodstall.model.RazorpayOrderResponse;
import com.saveetha.foodstall.model.StatusResponse;
import com.saveetha.foodstall.model.HomeDataResponse;
import com.saveetha.foodstall.model.LoginResponse;
import com.saveetha.foodstall.model.StallDetailsListResponse;
import com.saveetha.foodstall.model.DashboardResponse;
import com.saveetha.foodstall.model.StallMenuResponse;
import com.saveetha.foodstall.model.TimeSlotsResponse;
import com.saveetha.foodstall.model.WalletBalanceResponse;
import com.saveetha.foodstall.model.WalletDetailsResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiService {

    @FormUrlEncoded
    @POST("Usignup.php")
    Call<StatusResponse> registerUser(
            @Field("fullname") String fullName,
            @Field("id") String studentId,
            @Field("email") String email,
            @Field("password") String password,
            @Field("confirm_password") String confirmPassword
    );

    @FormUrlEncoded
    @POST("Osignup.php")
    Call<StatusResponse> registerOwner(
            @Field("fullname") String fullName,
            @Field("phonenumber") String phoneNumber,
            @Field("password") String password,
            @Field("confirm_password") String confirmPassword
    );

    @FormUrlEncoded
    @POST("Ostallsignup.php")
    Call<StatusResponse> submitStallDetails(
            @Field("stallname") String stallName,
            @Field("ownername") String ownerName,
            @Field("phonenumber") String phoneNumber,
            @Field("email") String email,
            @Field("fulladdress") String fullAddress,
            @Field("fssainumber") String fssaiNumber,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("update_stall_status.php")
    Call<StatusResponse> updateStallStatus(
            @Field("email") String email,
            @Field("status") String status,
            @Field("reason") @Nullable String reason
    );

    @GET("Aget_pending_stalls.php")
    Call<StallDetailsListResponse> getOnlyPendingStalls();

    @FormUrlEncoded
    @POST("Aget_stalls.php")
    Call<StallDetailsListResponse> getStallsByFilter(@Field("filter") String filter);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> loginUser(
            @Field("identifier") String identifier,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("send_otp.php")
    Call<StatusResponse> sendOtp(@Field("email") String email);

    @FormUrlEncoded
    @POST("verify_otp.php")
    Call<StatusResponse> verifyOtp(
            @Field("email") String email,
            @Field("otp") String otp
    );

    @FormUrlEncoded
    @POST("reset_password.php")
    Call<StatusResponse> resetPassword(
            @Field("email") String email,
            @Field("otp") String otp,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("Odashboard_data.php")
    Call<DashboardResponse> getDashboardData(@Field("stall_id") String stallId);
    // Remove the old @Multipart updateMenu method from ApiService.java
// and ADD THESE NEW METHODS:
// Remove old owner menu methods and add these new ones.

    @FormUrlEncoded
    @POST("oupdate_menu.php")
    Call<OGetMenuResponse> getOwnerMenuDetails(@Field("action") String action, @Field("stall_id") String stallId);

    @Multipart
    @POST("oupdate_menu.php")
    Call<StatusResponse> addMenuItem(@PartMap Map<String, RequestBody> fields, @Nullable @Part MultipartBody.Part image);

    @Multipart
    @POST("oupdate_menu.php")
    Call<StatusResponse> updateMenuItem(@PartMap Map<String, RequestBody> fields, @Nullable @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("oupdate_menu.php")
    Call<StatusResponse> deleteMenuItem(@Field("action") String action, @Field("item_id") int itemId);

    @FormUrlEncoded
    @POST("oupdate_menu.php")
    Call<StatusResponse> updateStallStatus(@Field("action") String action, @Field("stall_id") String stallId, @Field("is_open_today") int isOpen, @Field("opening_hours") String openingHours, @Field("closing_hours") String closingHours);
    @FormUrlEncoded
    @POST("Uget_home_data.php")
    Call<HomeDataResponse> getHomeData(@Field("student_id") String studentId);

    @FormUrlEncoded
    @POST("Uget_stall_menu.php")
    Call<StallMenuResponse> getStallMenu(
            @Field("stall_id") String stallId,
            @Field("student_id") String studentId
    );

    @FormUrlEncoded
    @POST("Usubmit_review.php")
    Call<StatusResponse> submitReview(
            @Field("stall_id") String stallId,
            @Field("student_id") String studentId,
            @Field("rating") float rating,
            @Field("review_text") String reviewText
    );
    // Add this inside your ApiService.java interface
    @FormUrlEncoded
    @POST("Uget_pickup_slots.php")
    Call<TimeSlotsResponse> getPickupSlots(@Field("stall_id") String stallId);

    @FormUrlEncoded
    @POST("Udelete_review.php")
    Call<StatusResponse> deleteReview(
            @Field("stall_id") String stallId,
            @Field("student_id") String studentId
    );
    @FormUrlEncoded
    @POST("Uget_wallet_balance.php")
    Call<WalletBalanceResponse> getWalletBalance(@Field("student_id") String studentId);

    @FormUrlEncoded
    @POST("Ucreate_razorpay_order.php")
    Call<RazorpayOrderResponse> createRazorpayOrder(@Field("amount") double amount);

    @FormUrlEncoded
    @POST("Uplace_order.php")
    Call<OrderPlacementResponse> placeOrder(@Field("stall_id") String stallId, @Field("student_id") String studentId, @Field("total_amount") double totalAmount, @Field("payment_id") String paymentId, @Field("pickup_time") @Nullable String pickupTime, @Field("order_items") String orderItemsJson);

    @FormUrlEncoded
    @POST("Uplace_order_wallet.php")
    Call<OrderPlacementResponse> placeWalletOrder(@Field("stall_id") String stallId, @Field("student_id") String studentId, @Field("total_amount") double totalAmount, @Field("pickup_time") @Nullable String pickupTime, @Field("order_items") String orderItemsJson);
    // --- END OF ADDED SECTION ---
    @FormUrlEncoded
    @POST("Uget_wallet_details.php")
    Call<WalletDetailsResponse> getWalletDetails(@Field("student_id") String studentId);

    @FormUrlEncoded
    @POST("Uadd_money_to_wallet.php")
    Call<StatusResponse> addMoneyToWallet(
            @Field("student_id") String studentId,
            @Field("amount") double amount,
            @Field("razorpay_payment_id") String paymentId,
            @Field("razorpay_order_id") String orderId,
            @Field("razorpay_signature") String signature
    );
    @FormUrlEncoded
    @POST("Utoggle_favorite.php")
    Call<StatusResponse> toggleFavorite(@Field("student_id") String studentId, @Field("stall_id") String stallId);

    @FormUrlEncoded
    @POST("Uget_favorite_stalls.php")
    Call<FavoriteStallsResponse> getFavoriteStalls(@Field("student_id") String studentId);
}