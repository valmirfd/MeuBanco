package com.valorizeseusamigos.meubanco.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.valorizeseusamigos.meubanco.MainActivity;
import com.valorizeseusamigos.meubanco.R;
import com.valorizeseusamigos.meubanco.databinding.ActivityLoginBinding;
import com.valorizeseusamigos.meubanco.helper.FirebaseHelper;
import com.valorizeseusamigos.meubanco.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }

    private void validaDados(){
        String email = binding.edtEmail.getText().toString().trim();
        String senha = binding.edtSenha.getText().toString().trim();


         if(!email.isEmpty()){
             if(!senha.isEmpty()){

                 binding.progressBar.setVisibility(View.VISIBLE);

                 logar(email, senha);

             }else {
                 binding.edtSenha.requestFocus();
                 binding.edtSenha.setError("Por favor digite sua senha");
             }
         }else {
             binding.edtEmail.requestFocus();
             binding.edtEmail.setError("Por favor digite seu email");
         }

    }

    private void criarConta(){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    private void recuperarConta(){
        startActivity(new Intent(this, RecuperarContaActivity.class));
    }

    private void logar(String email, String senha) {

        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }else {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configClicks(){
        binding.btnLogin.setOnClickListener(v -> validaDados());
        binding.textCriarConta.setOnClickListener(v -> criarConta());
        binding.textRecuperarConta.setOnClickListener(v -> recuperarConta());
    }


}