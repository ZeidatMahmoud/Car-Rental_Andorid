package com.labAdvance.carRental.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.labAdvance.carRental.R;
import com.labAdvance.carRental.database.SqliteHelper;
import com.labAdvance.carRental.models.LoginUser;

public class Login extends AppCompatActivity {
    private EditText email, password;
    private Button login, register;
    private CheckBox checkBox;
    private SqliteHelper mydb;
    private boolean check;

    //For Remember me in login Screen
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String Shared_Name = "Remember";
    private static final String For_Home = "Data" ;
    private static final String emailKey = "userName";
    private static final String passwordKey = "Password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mydb = new SqliteHelper(this);
        email = (EditText) findViewById(R.id.loginEmail);
        password = (EditText) findViewById(R.id.loginPassword);
        login = (Button) findViewById(R.id.loginbutton);
        register = (Button) findViewById(R.id.loginRegister);
        checkBox = (CheckBox) findViewById(R.id.loginCheck);

        sharedPreferences = getSharedPreferences(Shared_Name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        LoginUser remeber = getRemeberedData();
        if (!remeber.getEmail().equals("no")) {
            Intent i = new Intent(Login.this, Home.class);
            startActivity(i);
            finish();
        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, RegisterAc.class);
                startActivity(i);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordDB = "nil" ;
                try{
                    passwordDB = mydb.getThisEmailPassword(email.getText().toString());

                }
                catch (Exception e){

                }
                if (passwordDB.equals(password.getText().toString())) {
                    if (checkBox.isChecked()) {
                        editor.putString(emailKey, email.getText().toString());
                        editor.putString(passwordKey, password.getText().toString());
                        editor.commit();

                    }
                    sendDataToHome(email.getText().toString());
                    Intent i = new Intent(Login.this, Home.class);
                    startActivity(i);
                    finish();


                } else {
                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public LoginUser getRemeberedData() {
        String email = sharedPreferences.getString(emailKey, "no");
        String password = sharedPreferences.getString(passwordKey, "no");
        return new LoginUser(email, password);
    }

    /**
     * send the email to Home activity to get login data
     * @param email
     */
    public void sendDataToHome(String email){
        SharedPreferences homeShared = getSharedPreferences(For_Home ,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = homeShared.edit() ;
        editor.putString(emailKey ,email).commit() ;

    }
}
