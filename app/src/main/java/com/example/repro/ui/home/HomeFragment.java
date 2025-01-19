package com.example.repro.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.example.repro.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private LineChart lineChart;  // Mengubah barChart menjadi lineChart
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inisialisasi ViewModel
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        // Mengambil referensi binding dan root view
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Mengambil LineChart dari binding
        lineChart = binding.lineChart;

        // Siapkan data untuk LineChart
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1f, 30f));
        entries.add(new Entry(2f, 50f));
        entries.add(new Entry(3f, 70f));
        entries.add(new Entry(4f, 100f));

        // Set data set untuk LineChart
        LineDataSet dataSet = new LineDataSet(entries, "Label Data");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // Buat LineData
        LineData lineData = new LineData(dataSet);

        // Set data ke chart
        lineChart.setData(lineData);

        // Menampilkan chart
        lineChart.invalidate();

        // Kembalikan root view
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
