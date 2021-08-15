package com.usama.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    EditText email, password;
    Button login;
    TextView sign;
    ProgressBar bar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.emailL);
        password = findViewById(R.id.passwordL);
        bar = findViewById(R.id.barL);
        login = findViewById(R.id.signL);
        sign = findViewById(R.id.signupL);

        fAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailSignIn= email.getText().toString().trim();
                String passwordSignIn = password.getText().toString().trim();

                if (TextUtils.isEmpty( emailSignIn)) {
                    email.setError("Email Is Required");
                    return;
                }
                if (TextUtils.isEmpty(passwordSignIn)) {
                    password.setError("Password Is Required");
                    return;
                }
                if (password.length() < 6) {
                    password.setError("Password must be >= Character");
                    return;
                }
                bar.setVisibility(View.VISIBLE);

                //Authentication  the user
                fAuth.signInWithEmailAndPassword( emailSignIn, passwordSignIn).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignIn.this, "Logged is successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SignIn.this,HomeActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(SignIn.this, "Error ....!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            bar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignIn.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}