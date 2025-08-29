package com.example.medesafio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(this);

        TextView txtUser = findViewById(R.id.txtUser);
        TextView txtEmail = findViewById(R.id.txtEmail);

        String username = session.getUsername();
        String email = session.getEmail();

        txtUser.setText("Se segure firme " + username);
        txtEmail.setText(email);

        ImageButton btnLogout = findViewById(R.id.btnBack);
        btnLogout.setOnClickListener(v -> {

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Sair")
                    .setMessage("Deseja realmente sair ?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        session.logout();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    })
                    .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }
}