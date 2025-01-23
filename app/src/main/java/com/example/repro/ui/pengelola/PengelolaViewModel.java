package com.example.repro.ui.pengelola;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.repro.api.Ambil;
import com.example.repro.api.ApiService;
import com.example.repro.api.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengelolaViewModel extends ViewModel {
    private final MutableLiveData<List<Ambil>> ambil = new MutableLiveData<>();
    private final ApiService apiService;
    private final MutableLiveData<String> mText;

    public PengelolaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is pengelola fragment");

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        fetchAmbil(); // Panggil data API saat ViewModel dibuat
    }

    private void fetchAmbil() {
        apiService.getAmbil().enqueue(new Callback<List<Ambil>>() {
            @Override
            public void onResponse(Call<List<Ambil>> call, Response<List<Ambil>> response) {
                if (response.isSuccessful()) {
                    ambil.setValue(response.body());
                    Log.d("PengelolaViewModel", "Data retrieved: " + response.body().toString());
                } else {
                    Log.e("PengelolaViewModel", "Response failed: " + response.message());
                    ambil.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Ambil>> call, Throwable t) {
                Log.e("PengelolaViewModel", "API call failed: " + t.getMessage());
                ambil.setValue(null);
            }
        });
    }

    public LiveData<List<Ambil>> getAmbil() {
        return ambil;
    }

    public LiveData<String> getText() {
        return mText;
    }
}
