package com.highsenbergs.ecofriendly.network;

import com.highsenbergs.ecofriendly.model.Contacts;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FollowerApiInterface {
    @GET("user?page=2")
    Call<Contacts> getHeadlines();
}
