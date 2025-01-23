package com.example.repro.ui.pengelola;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repro.api.ApiService;
import com.example.repro.R;
import com.example.repro.api.RetrofitClient;
import com.example.repro.api.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengelolaFragment extends Fragment {
    private RecyclerView recyclerView;
    private PemasokAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pengelola, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewDaftarPemasok); // Menggunakan `root` untuk menemukan ID
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Mengatur layout manager RecyclerView

        fetchPemasokData(); // Memanggil data dari API

        return root;
    }

    private void fetchPemasokData() {
        // Menggunakan instance Retrofit dari RetrofitClient
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<ApiResponse<List<Pemasok>>> call = apiService.getPemasokList();
        call.enqueue(new Callback<ApiResponse<List<Pemasok>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Pemasok>>> call, Response<ApiResponse<List<Pemasok>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    // Data berhasil diambil
                    List<Pemasok> pemasokList = response.body().getData();
                    adapter = new PemasokAdapter(getContext(), pemasokList);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Pemasok>>> call, Throwable t) {
                // Handle kegagalan mengambil data
                Toast.makeText(getContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
