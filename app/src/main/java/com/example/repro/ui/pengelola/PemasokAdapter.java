package com.example.repro.ui.pengelola;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repro.R;

import java.util.List;

public class PemasokAdapter extends RecyclerView.Adapter<PemasokAdapter.ViewHolder> {

    private Context context;
    private List<Pemasok> pemasokList;

    public PemasokAdapter(Context context, List<Pemasok> pemasokList) {
        this.context = context;
        this.pemasokList = pemasokList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaPemasok, tvNamaUsaha, tvNoHp, tvAlamat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaPemasok = itemView.findViewById(R.id.tv_nama_pemasok);
            tvNamaUsaha = itemView.findViewById(R.id.tv_nama_usaha);
            tvNoHp = itemView.findViewById(R.id.tv_no_hp);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
        }
    }
}