package com.example.repro.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton, pagesRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.etEmail);
        passwordEditText = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.btn_login);
        pagesRegister = findViewById(R.id.btn_halaman_register);

        // Handle login button click
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate inputs
                if (TextUtils.isEmpty(username)) {
                    usernameEditText.setError("Username is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError("Password is required");
                    return;
                }

                loginUser(username, password);
            }
        });

        // Handle register button click
        pagesRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String email, String password) {
        ApiService apiInterface = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Membuat map untuk parameter
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("email", email);
        jsonParams.put("password", password);

        // Konversi map menjadi RequestBody dengan format JSON
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new JSONObject(jsonParams).toString());

        // Mengirimkan request login ke server
        Call<ResponseBody> call = apiInterface.loginUser(body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        // Membaca response body sebagai string
                        String result = response.body().string();  // Convert ResponseBody to String

                        if (result != null && result.contains("Login successful")) {
                            // Login berhasil
                            Log.d("LoginActivity", "Login successful: " + result);
                            Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();

                            // Redirect to MainActivity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // Optional, untuk menutup LoginActivity
                        } else {
                            // Gagal login
                            Log.e("LoginActivity", "Login failed: Invalid email or password. Response: " + result);
                            Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        Log.e("LoginActivity", "Error reading response body: " + e.getMessage(), e);
                        Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Respon server tidak sukses
                    Log.e("LoginActivity", "Login failed: Response code " + response.code() + ", message: " + response.message());
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Kesalahan koneksi atau server
                Log.e("LoginActivity", "Login error: " + t.getMessage(), t);
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}