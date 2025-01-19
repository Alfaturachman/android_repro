package com.example.repro.ui.pengelola;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.repro.Ambil;
import com.example.repro.R;

public class PengelolaFragment extends Fragment {
    private PengelolaViewModel pengelolaViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pengelola, container, false);

        TextView textView = root.findViewById(R.id.text_pengelola);
        TableLayout tableLayout = root.findViewById(R.id.table_layout);

        pengelolaViewModel = new ViewModelProvider(this).get(PengelolaViewModel.class);

        // Set the initial text
        pengelolaViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Observe data and add rows dynamically
        pengelolaViewModel.getAmbil().observe(getViewLifecycleOwner(), ambilList -> {
            if (ambilList != null) {
                for (Ambil ambil : ambilList) {
                    TableRow row = new TableRow(getContext());

                    // ID Column
                    TextView idTextView = new TextView(getContext());
                    idTextView.setText(String.valueOf(ambil.getIdAmbil())); // Ganti dengan atribut ID yang sesuai
                    idTextView.setPadding(8, 8, 8, 8);
                    row.addView(idTextView);

                    // Nama Column
                    TextView namaTextView = new TextView(getContext());
                    namaTextView.setText(ambil.getNamaPemasok()); // Ganti dengan atribut Nama yang sesuai
                    namaTextView.setPadding(8, 8, 8, 8);
                    row.addView(namaTextView);

                    tableLayout.addView(row);
                }
            }
        });

        return root;
    }
}
