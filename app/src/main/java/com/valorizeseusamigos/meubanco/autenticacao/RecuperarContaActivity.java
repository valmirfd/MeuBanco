package com.valorizeseusamigos.meubanco.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.valorizeseusamigos.meubanco.R;
import com.valorizeseusamigos.meubanco.databinding.ActivityRecuperarContaBinding;
import com.valorizeseusamigos.meubanco.helper.FirebaseHelper;

public class RecuperarContaActivity extends AppCompatActivity {

    private ActivityRecuperarContaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperarContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();

    }

    private void validaDados(){
        String email = binding.edtEmail.getText().toString().trim();

        if(!email.isEmpty()){

            binding.progressBar.setVisibility(View.VISIBLE);
            recuperarConta(email);

        }else {
           binding.edtEmail.requestFocus();
           binding.edtEmail.setError("Campo obrigatório");
        }
    }

    private void recuperarConta(String email) {

        FirebaseHelper.getAuth().sendPasswordResetEmail(
                email
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(this, "Acabamos de enviar um link para seu email!", Toast.LENGTH_SHORT).show();
            }else {

                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
            binding.progressBar.setVisibility(View.GONE);
        });
    }

    private void configClicks(){

        binding.btnRecuperarConta.setOnClickListener(v -> validaDados());
    }
}