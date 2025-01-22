package com.example.repro.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LogoutActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflasi layout kosong untuk fragment ini, meskipun proses logout tidak memerlukan tampilan
        View view = inflater.inflate(android.R.layout.simple_list_item_1, container, false);

        // Proses logout
        logoutUser();

        return view;
    }

    private void logoutUser() {
        // Hapus session dari SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

        // Arahkan ke LoginActivity
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Membersihkan tumpukan aktivitas
        startActivity(intent);

        // Tutup aktivitas saat ini
        requireActivity().finish();
    }
}
