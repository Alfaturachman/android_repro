package com.example.repro.ui.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.repro.ApiService;
import com.example.repro.MainActivity;
import com.example.repro.R;
import com.example.repro.RetrofitClient;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
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
                String no_hp = etNomorHp.getText().toString().trim();
                String alamat = etAlamat.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etKonfirmasiPassword.getText().toString().trim();

                // Validasi input
                if (nama_pemilik.isEmpty() || nama_usaha.isEmpty() || no_hp.isEmpty() || alamat.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kirim data ke server
                registerUser(nama_pemilik, nama_usaha, no_hp, alamat, email, password);
            }
        });
    }

    private void registerUser(String nama_pemilik, String nama_usaha, String no_hp, String alamat, String email, String password) {
        ApiService apiInterface = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Log.d(TAG, "Data sebelum dikirim: " +
                "nama_pemilik=" + nama_pemilik +
                ", nama_usaha=" + nama_usaha +
                ", no_hp=" + no_hp +
                ", alamat=" + alamat +
                ", email=" + email +
                ", password=" + password);

        // Buat object JSON
        Map<String, String> requestData = new HashMap<>();
        requestData.put("email", email);
        requestData.put("password", password);
        requestData.put("nama", nama_pemilik);
        requestData.put("nama_usaha", nama_usaha);
        requestData.put("no_hp", no_hp);
        requestData.put("alamat", alamat);

        // Buat RequestBody dari Map
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(requestData).toString());

        // Kirim ke server
        Call<ResponseBody> call = apiInterface.registerUser(body);

        // Lakukan request menggunakan enqueue
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Log.d(TAG, "Registration successful: " + result); // Log sukses

                        // Periksa jika respons berisi message yang sukses
                        JSONObject jsonResponse = new JSONObject(result);
                        if (jsonResponse.has("message") && jsonResponse.getString("message").contains("berhasil ditambahkan")) {
                            Toast.makeText(RegisterActivity.this, "Register berhasil", Toast.LENGTH_SHORT).show();

                            // Redirect to RegisterActivity
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed: " + result, Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Registration failed: " + result); // Log error dari server
                        }
                    } catch (Exception e) {
                        Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error parsing response: ", e); // Log parsing error
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Server response error: " + response.message()); // Log error response
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Network error: ", t); // Log network error
            }
        });
    }

}
