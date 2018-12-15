package com.labAdvance.carRental.activities;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.labAdvance.carRental.R ;
import com.labAdvance.carRental.database.SqliteHelper;
import com.labAdvance.carRental.models.LoginUser;
import com.labAdvance.carRental.models.RegisterModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okio.ForwardingTimeout;

public class Register extends AppCompatActivity {
    private EditText email , fullname , password ,confirm ;
    private Button register ;
    private SqliteHelper mydb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         email  = (EditText)findViewById(R.id.registerEmail);
         fullname  = (EditText)findViewById(R.id.registerFullName);
         password  =(EditText)findViewById(R.id.registerPassword);
         confirm = (EditText)findViewById(R.id.registerConfirmPassword) ;
         register = (Button) findViewById(R.id.registerButton) ;
         mydb = new SqliteHelper(this) ;

         register.setOnClickListener(new View.OnClickListener() {
             @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
             @Override
             public void onClick(View v) {
                 Drawable errorBorder  = getResources().getDrawable(R.drawable.txt_border_error) ;
                 Drawable errorIcon = getResources().getDrawable(R.drawable.error) ;
                 Drawable trueBordar = getResources().getDrawable(R.drawable.txt_true_green) ;
                 Drawable trueIcon  =getResources().getDrawable(R.drawable.true_green);
                 //check email address
                 if(!isEmailValid(email.getText().toString())){
                     changebordarAndIcon(errorBorder ,errorIcon ,email);
                     return;

                 }
                 //check if password match
                 if(!(password.getText().toString().equals(confirm.getText().toString()))){
                     changebordarAndIcon(errorBorder,errorIcon, password);
                     changebordarAndIcon(errorBorder,errorIcon, confirm);
                     return;
                 }

                 changebordarAndIcon(trueBordar ,trueIcon ,email);
                 changebordarAndIcon(trueBordar ,trueIcon ,password);
                 changebordarAndIcon(trueBordar ,trueIcon ,confirm);
                 changebordarAndIcon(trueBordar ,trueIcon ,fullname);

                 RegisterModel registerModel = new RegisterModel(email.getText().toString()
                         , fullname.getText().toString()
                         ,password.getText().toString()) ;
                 LoginUser  loginUser  = new LoginUser(email.getText().toString() , password.getText().toString() ,false);

                 //insert into database
                 mydb.insertRegsiterData(registerModel);

                 //initialize Login
                 mydb.insertLoginData(loginUser);


                 Toast.makeText(Register.this, "Registered Successfully ,hit back Button to Log in:) " , Toast.LENGTH_LONG).show();

             }
         });
    }

    /**
     *
     * @param email
     * @return boolean
     * check if the content of a Edit text is email or not
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void changebordarAndIcon(Drawable bordar , Drawable icon ,EditText text){
        text.setCompoundDrawablesWithIntrinsicBounds(null, null ,icon ,null);
        text.setBackground(bordar);
        text.setPadding(0 ,0,20,0);
    }
}
