package com.example.medesafio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUser, edtEmail, edtSenha, edtSenhaConfirmacao;
    private Button btnLogin;
    private ImageButton btnBack;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        session = new SessionManager(this);

        edtUser = findViewById(R.id.edtUser);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtSenhaConfirmacao = findViewById(R.id.edtSenhaConfirmacao);
        btnLogin = findViewById(R.id.btnLogin);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        btnLogin.setOnClickListener(v -> {
            String username = edtUser.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtSenha.getText().toString().trim();
            String confirmPassword = edtSenhaConfirmacao.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edtEmail.setError("E-mail inválido");
                return;
            }

            if (!password.equals(confirmPassword)) {
                edtSenhaConfirmacao.setError("As senhas não coincidem");
                return;
            }

            boolean saved = session.saveUser(username, email, password);
            if (saved) {
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao cadastrar. Email ou usuário já existe.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}