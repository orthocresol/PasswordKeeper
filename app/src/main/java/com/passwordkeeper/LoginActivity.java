package com.passwordkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    Button btn_login, btn_register;
    FirebaseAuth auth;
    EditText et_email, et_password;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        btn_login = findViewById(R.id.loginLogin);
        btn_register = findViewById(R.id.loginRegister);
        et_email = findViewById(R.id.loginEmail);
        et_password = findViewById(R.id.loginPassword);
        progressBar = findViewById(R.id.loginProgressbar);

        progressBar.setVisibility(View.INVISIBLE);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login processing
                progressBar.setVisibility(View.VISIBLE);
                authenticateUser();

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void authenticateUser() {
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if(email.length() == 0){
            et_email.setError("This field cannot be blank");
            return;
        }

        if(password.length() == 0){
            et_password.setError("This field cannot be blank");
            return;
        }

        if(email.length() < 10){
            et_email.setError("Please enter a valid email address");
            return;
        }
        //login
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = auth.getCurrentUser();

                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                            // put Extra
                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);
                            finish();

                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            Log.w("Login", "Login Failed: ", task.getException());
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }
}