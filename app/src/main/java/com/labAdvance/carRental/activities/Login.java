package com.labAdvance.carRental.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.labAdvance.carRental.R ;

public class Login extends AppCompatActivity {
    private EditText email , password ;
    private Button login , register ;
    private CheckBox checkBox ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.loginEmail);
        password  =(EditText)findViewById(R.id.loginPassword);
        login = (Button)findViewById(R.id.loginbutton) ;
        register  =(Button)findViewById(R.id.loginRegister) ;
        checkBox = (CheckBox)findViewById(R.id.loginCheck);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });
    }
}
