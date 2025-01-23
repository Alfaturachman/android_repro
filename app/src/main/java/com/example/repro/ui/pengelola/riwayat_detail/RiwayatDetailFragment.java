package com.example.repro.ui.pengelola.riwayat_detail;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.repro.R;

public class RiwayatDetailFragment extends Fragment {

    private static final String ARG_ID_PEMASOK = "id_pemasok";

    public static RiwayatDetailFragment newInstance(int idPemasok) {
        RiwayatDetailFragment fragment = new RiwayatDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_PEMASOK, idPemasok);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // Ambil ID Pemasok yang dikirim melalui Bundle
        int idPemasok = getArguments().getInt(ARG_ID_PEMASOK);

        // Inflate layout dan lakukan operasi lain yang diperlukan

        return inflater.inflate(R.layout.fragment_riwayat_detail, container, false);
    }
}
