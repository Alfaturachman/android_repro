package com.example.repro;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("login.php")
    Call<ResponseBody> loginUser(@Body RequestBody body);

    @POST("register.php")
    Call<String> registerUser(
            @Query("name") String name,
            @Query("business_name") String businessName,
            @Query("phone") String phone,
            @Query("address") String address,
            @Query("email") String email,
            @Query("password") String password
    );

    @GET("get_ambil.php")
    Call<List<Ambil>> getAmbil();
}
