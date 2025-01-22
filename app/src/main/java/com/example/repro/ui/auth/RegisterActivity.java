package com.example.repro.ui.auth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.repro.ApiService;
import com.example.repro.R;
import com.example.repro.RetrofitClient;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNamaPemilik, etNamaUsaha, etNomorHp, etAlamat, etEmail, etPassword, etKonfirmasiPassword;
    private Button registerButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi EditText
        etNamaPemilik = findViewById(R.id.etNamaPemilik);
        etNamaUsaha = findViewById(R.id.etNamaUsaha);
        etNomorHp = findViewById(R.id.etNomorHP);
        etAlamat = findViewById(R.id.etAlamat);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etKonfirmasiPassword = findViewById(R.id.etKonfirmasiPassword);

        // Inisialisasi tombol
        registerButton = findViewById(R.id.btn_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama_pemilik = etNamaPemilik.getText().toString().trim();
                String nama_usaha = etNamaUsaha.getText().toString().trim();
                String nomor_hp = etNomorHp.getText().toString().trim();
                String alamat = etAlamat.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etKonfirmasiPassword.getText().toString().trim();

                // Validasi input
                if (nama_pemilik.isEmpty() || nama_usaha.isEmpty() || nomor_hp.isEmpty() || alamat.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kirim data ke server
                registerUser(nama_pemilik, nama_usaha, nomor_hp, alamat, email, password);
            }
        });
    }

    private void registerUser(String nama_pemilik, String nama_usaha, String nomor_hp, String alamat, String email, String password) {
        ApiService apiInterface = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Buat RequestBody untuk data yang akan dikirim
        RequestBody requestBody = new FormBody.Builder()
                .add("nama_pemilik", nama_pemilik)
                .add("nama_usaha", nama_usaha)
                .add("nomor_hp", nomor_hp)
                .add("alamat", alamat)
                .add("email", email)
                .add("password", password)
                .build();

        Call<ResponseBody> call = apiInterface.registerUser(requestBody);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        // Membaca respon sebagai String
                        String result = response.body().string();
                        if (result.contains("success")) { // Sesuaikan dengan respon API
                            Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                            finish(); // Tutup aktivitas ini
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed: " + result, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
