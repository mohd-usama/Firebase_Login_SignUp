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

public class MainActivity extends AppCompatActivity {
    EditText name,email,pass,phone;
    Button submit;
    TextView login;
    ProgressBar bar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.emailL);
        email=findViewById(R.id.emailR);
        pass=findViewById(R.id.passwordL);
        phone=findViewById(R.id.phoneR);
        submit =findViewById(R.id.signL);
        login=findViewById(R.id.textSign);
        bar =findViewById(R.id.barR);

        fAuth=FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null)
        {
            //startActivity(new Intent(getApplicationContext(),HomePage.class));
            finish();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailSignUp=email.getText().toString().trim();
                String passwordSignUp=pass.getText().toString().trim();

                if(TextUtils.isEmpty(emailSignUp))
                {
                    email.setError("Email Is Required");
                    return;
                }
                if(TextUtils.isEmpty(passwordSignUp))
                {
                    pass.setError("Password Is Required");
                    return;
                }
                if(pass.length() < 6)
                {
                    pass.setError("Password must be >= Character");
                    return;
                }
                bar.setVisibility(View.VISIBLE);

                //register the user to firebase.
                fAuth.createUserWithEmailAndPassword(emailSignUp,passwordSignUp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Error...! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            bar.setVisibility(View.GONE);
                        }
                        }
                });
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SignIn.class);
                startActivity(intent);
            }
        });
    }
}