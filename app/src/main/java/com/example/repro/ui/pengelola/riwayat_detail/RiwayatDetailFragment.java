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

    private RiwayatDetailViewModel mViewModel;

    public static RiwayatDetailFragment newInstance() {
        return new RiwayatDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_riwayat_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RiwayatDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}