package com.example.repro.ui.pemasok;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.repro.R;

public class PemasokFragment extends Fragment {

    private PemasokViewModel mViewModel;

    public static PemasokFragment newInstance() {
        return new PemasokFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pemasok, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialize the ViewModel
        mViewModel = new ViewModelProvider(this).get(PemasokViewModel.class);

    }
}