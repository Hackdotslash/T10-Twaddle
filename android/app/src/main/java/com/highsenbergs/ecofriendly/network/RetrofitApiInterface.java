package com.highsenbergs.ecofriendly.network;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.gson.JsonObject;
import com.highsenbergs.ecofriendly.model.CarPool;
import com.highsenbergs.ecofriendly.model.EndTripCoins;
import com.highsenbergs.ecofriendly.model.OngoingJourneyCoordinates;
import com.highsenbergs.ecofriendly.model.SelfDriveStart;
import com.highsenbergs.ecofriendly.model.UserDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface RetrofitApiInterface {
    @POST("user_register?")
    Call<UserDetails> createUser(@Body UserDetails userDetails);

    @POST("connectivity?")
    Call<SelfDriveStart> startSelfDrive(@Body SelfDriveStart selfDriveStartAPI);

    @HTTP(method = "DELETE", path = "/connectivity?", hasBody = true)
    Call<SelfDriveStart> endSelfDrive(@Body SelfDriveStart selfDriveStartAPI);

    @POST("carpool?")
    Call<CarPool> startCarPooling(@Body CarPool carPool);

    @HTTP(method = "DELETE", path = "/carpool?", hasBody = true)
    Call<CarPool> endCarPooling(@Body CarPool carPool);

    @POST("drive_data")
    Call<EndTripCoins> sendJourneyCoordinates(@Body JsonObject drive_data );
}
