package com.example.astrology.loginSignupSeRelated;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.astrology.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExpertSignUp1 extends AppCompatActivity {
    FloatingActionButton exsignup2;
    EditText exname,exmobile,exemail;
    TextView exalreadyHaveacc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_sign_up1);
        exname =findViewById(R.id.Exfullname);
        exemail =findViewById(R.id.Exemailid);
        exmobile =findViewById(R.id.Exmobile);
        exsignup2= findViewById(R.id.Exsignup2);

        exalreadyHaveacc= findViewById(R.id.ExalreadyHaveacc);
        exalreadyHaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpertSignUp1.this, Login.class));
            }
        });

        exsignup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(ExpertSignUp1.this,ExpertSignUp2.class);
                intent.putExtra("exname",exname.getText().toString());
                intent.putExtra("exemail",exemail.getText().toString());
                intent.putExtra("exmobile",exmobile.getText().toString());
                startActivity(intent);
            }
        });
    }
}