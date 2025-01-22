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

    @POST("post_pemasok.php")
    Call<ResponseBody> registerUser(@Body RequestBody body);

    @GET("get_ambil.php")
    Call<List<Ambil>> getAmbil();
}
