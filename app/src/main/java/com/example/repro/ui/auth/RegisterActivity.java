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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText, businessNameEditText, phoneEditText, addressEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi EditText
        nameEditText = findViewById(R.id.etNamaPemilik);
        businessNameEditText = findViewById(R.id.etNamaUsaha);
        phoneEditText = findViewById(R.id.etNomorHP);
        addressEditText = findViewById(R.id.etAlamat);
        emailEditText = findViewById(R.id.etEmail);
        passwordEditText = findViewById(R.id.etPassword);
        confirmPasswordEditText = findViewById(R.id.etKonfirmasiPassword);

        // Inisialisasi tombol
        registerButton = findViewById(R.id.btn_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String businessName = businessNameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                registerUser(name, businessName, phone, address, email, password);
            }
        });
    }

    private void registerUser(String name, String businessName, String phone, String address, String email, String password) {
        ApiService apiInterface = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<String> call = apiInterface.registerUser(name, businessName, phone, address, email, password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String result = response.body();
                    if (result != null && result.equals("New record created successfully")) {
                        // Berhasil register
                        Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                        // Arahkan ke halaman login
                        finish(); // Menutup aktivitas ini dan kembali ke login
                    } else {
                        // Gagal register
                        Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
