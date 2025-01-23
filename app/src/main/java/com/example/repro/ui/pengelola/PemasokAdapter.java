package com.example.repro.ui.pengelola;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repro.R;
import com.example.repro.ui.pengelola.riwayat_detail.RiwayatDetailFragment;

import java.util.List;

public class PemasokAdapter extends RecyclerView.Adapter<PemasokAdapter.ViewHolder> {

    private Context context;
    private List<Pemasok> pemasokList;

    // Konstruktor Adapter
    public PemasokAdapter(Context context, List<Pemasok> pemasokList) {
        this.context = context;
        this.pemasokList = pemasokList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Pastikan context diteruskan dari activity atau fragment
        View view = LayoutInflater.from(context).inflate(R.layout.daftar_pemasok, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pemasok pemasok = pemasokList.get(position);
        holder.tvNamaPemasok.setText(pemasok.getNama());
        holder.tvNamaUsaha.setText(pemasok.getNama_usaha());
        holder.tvNoHp.setText(pemasok.getNo_hp());
        holder.tvAlamat.setText(pemasok.getAlamat());
    }

    @Override
    public int getItemCount() {
        return pemasokList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaPemasok, tvNamaUsaha, tvNoHp, tvAlamat;
        ImageView btnAmbil;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaPemasok = itemView.findViewById(R.id.tv_nama_pemasok);
            tvNamaUsaha = itemView.findViewById(R.id.tv_nama_usaha);
            tvNoHp = itemView.findViewById(R.id.tv_no_hp);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            btnAmbil = itemView.findViewById(R.id.btn_detail);

            // Menggunakan getContext() untuk memastikan context
            btnAmbil.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Pemasok pemasok = pemasokList.get(position);
                    handleAmbilClick(pemasok);
                }
            });
        }

        private void handleAmbilClick(Pemasok pemasok) {
            // Ambil context dari itemView
            Context context = itemView.getContext();

            // Pastikan kita sedang berada di dalam sebuah Activity
            if (context instanceof AppCompatActivity) {
                AppCompatActivity activity = (AppCompatActivity) context;

                // Buat instance fragment dan kirimkan data
                RiwayatDetailFragment fragment = RiwayatDetailFragment.newInstance(pemasok.getId());

                // Gunakan FragmentTransaction untuk menambahkan atau mengganti fragment
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)  // Ganti dengan ID container fragment di Activity Anda
                        .addToBackStack(null)  // Opsional, untuk menambah fragment ke back stack
                        .commit();
            } else {
                Toast.makeText(context, "Activity is not an instance of AppCompatActivity", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
