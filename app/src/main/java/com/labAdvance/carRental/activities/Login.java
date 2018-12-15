package com.labAdvance.carRental.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.labAdvance.carRental.R ;
import com.labAdvance.carRental.database.SqliteHelper;
import com.labAdvance.carRental.models.LoginUser;

public class Login extends AppCompatActivity {
    private EditText email , password ;
    private Button login , register ;
    private CheckBox checkBox ;
    private SqliteHelper mydb ;
    private boolean check ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mydb = new SqliteHelper(this) ;
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
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser user = new LoginUser(email.getText().toString(), password.getText().toString(), false);
                check = mydb.checkEmailAndPassword(user);
                if (check) {
                    if (checkBox.isChecked()) {
                        user.setStayLogged(true);
                        mydb.keepUserLoggedIn(user);
                    }
                    Intent i = new Intent(Login.this, Home.class);
                    startActivity(i);
                    finish();
                }
               else{
                   Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
               }


            }
        });
    }
}
