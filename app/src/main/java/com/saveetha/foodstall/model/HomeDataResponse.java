package com.saveetha.foodstall.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HomeDataResponse {
    @SerializedName("status") private String status;
    @SerializedName("data") private HomeData data;
    public String getStatus() { return status; }
    public HomeData getData() { return data; }

    public static class HomeData {
        @SerializedName("user_fullname") private String userFullname;
        @SerializedName("specials") private List<SpecialDish> specials;
        @SerializedName("stalls") private List<Stall> stalls;
        @SerializedName("popular_dishes") private List<PopularDish> popularDishes;
        public String getUserFullname() { return userFullname; }
        public List<SpecialDish> getSpecials() { return specials; }
        public List<Stall> getStalls() { return stalls; }
        public List<PopularDish> getPopularDishes() { return popularDishes; }
    }
}