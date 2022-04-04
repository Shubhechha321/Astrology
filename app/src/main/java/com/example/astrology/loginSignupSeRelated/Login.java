package com.example.astrology.loginSignupSeRelated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.astrology.Activities.MainActivity;
import com.example.astrology.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class  Login extends AppCompatActivity {
    Button login;
    TextView dontHaveacc;
    FirebaseAuth mAuth;
    FirebaseUser user;
EditText email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        email= findViewById(R.id.fullnamel);
        pass = findViewById(R.id.pass);
        login= findViewById(R.id.yo);
        dontHaveacc= findViewById(R.id.dontHaveacc);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginuser();
            }
        });



        dontHaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp1.class));
            }
        });


    }

    private void loginuser() {

        mAuth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(Login.this, "logged in succesfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, MainActivity.class));

            }
        });
    }
}





