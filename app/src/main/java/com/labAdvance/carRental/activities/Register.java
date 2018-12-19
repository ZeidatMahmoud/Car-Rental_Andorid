package com.labAdvance.carRental.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.labAdvance.carRental.R ;
import com.labAdvance.carRental.database.SqliteHelper;
import com.labAdvance.carRental.models.LoginUser;
import com.labAdvance.carRental.models.RegisterModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private EditText email , fullname , password ,confirm ;
    //private TextView errorEmail , errorfullName, errorPassword , errorConfirm ;
    private Button register ,login ;
    private SqliteHelper mydb ;
    private boolean checkEmail ;
    //private CheckBox agree ;
    private int checkPassword ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         email  = (EditText)findViewById(R.id.registerEmail);
         fullname  = (EditText)findViewById(R.id.registerFirstName);
         password  =(EditText)findViewById(R.id.registerPassword);
         confirm = (EditText)findViewById(R.id.registerConfirmPassword) ;
         register = (Button) findViewById(R.id.registerButton) ;
//         errorEmail  = (TextView)findViewById(R.id.registerErrorEmail);
//         errorfullName  =(TextView)findViewById(R.id.registerErrorFullName);
//        errorPassword  =(TextView)findViewById(R.id.registerErrorPassword);
//        errorConfirm  =(TextView)findViewById(R.id.registerErrorConfirm);
       // agree = (CheckBox)findViewById(R.id.registerAgree) ;


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
                     changeBordarAndIcon(errorBorder ,errorIcon ,email);
                    // setErrorViews(errorEmail ,"This Email is NOT valid ");
                     return;

                 }
                 if(!isNameValid(fullname.getText().toString())){
                     changeBordarAndIcon(errorBorder,errorIcon, fullname);
                    // setErrorViews(errorfullName ,"Name is invalid ");
                     return;
                 }
                 //check if password Validity
                 checkPassword = isPasswordValid(password.getText().toString() , fullname.getText().toString() ,
                         confirm.getText().toString());
                 switch (checkPassword){

                     case 1:
                         changeBordarAndIcon(errorBorder,errorIcon, password);
                         changeBordarAndIcon(errorBorder,errorIcon, confirm);
                        //setErrorViews(errorPassword ,"Password length must be more than 6 characters ");
                         //setErrorViews(errorConfirm ,"Password length must be more than 6 characters ");
                         return;
                     case 2:
                         changeBordarAndIcon(errorBorder,errorIcon, password);
                         changeBordarAndIcon(errorBorder,errorIcon, confirm);
                         //setErrorViews(errorPassword ,"you should not use your name as password ");
                         //setErrorViews(errorConfirm ,"you should not use your name as password ");
                         return;
                     case 3:
                         changeBordarAndIcon(errorBorder,errorIcon, password);
                         changeBordarAndIcon(errorBorder,errorIcon, confirm);
                        // setErrorViews(errorPassword ,"Passwords do not match ");
                         //setErrorViews(errorConfirm ,"Passwords do not match ");
                         return;
                         default:
                             break;
                 }
                 checkEmail = mydb.checkExistingEmail(email.getText().toString());
                 if(checkEmail){
                     changeBordarAndIcon(errorBorder,errorIcon ,email);
                     //setErrorViews(errorEmail , "Email is Used");
                     return;
                 }

                 changeBordarAndIcon(trueBordar ,trueIcon ,email);
                 changeBordarAndIcon(trueBordar ,trueIcon ,password);
                 changeBordarAndIcon(trueBordar ,trueIcon ,confirm);
                 changeBordarAndIcon(trueBordar ,trueIcon ,fullname);
                 //agree.setCompoundDrawablesWithIntrinsicBounds(null,null,trueIcon ,null);
                // errorEmail.setText("");
                // errorPassword.setText("");
                 //errorConfirm.setText("");
                 //errorfullName.setText("");

//                 if(!agree.isChecked()){
//                     //agree.setTextColor(getResources().getColor(R.color.RedForError));
//                     agree.setCompoundDrawablesWithIntrinsicBounds(null,null,errorIcon,null);
//                     Toast.makeText(Register.this, "Please Accept the Privacy and terms of Conditions", Toast.LENGTH_SHORT).show();
//                     return;
//                 }

                 RegisterModel registerModel = new RegisterModel(email.getText().toString()
                         , fullname.getText().toString()
                         ,password.getText().toString()) ;
                 LoginUser  loginUser  = new LoginUser(email.getText().toString() , password.getText().toString() ,false);


                 //insert into database
                 mydb.insertRegsiterData(registerModel);

                 //initialize Login
                 mydb.insertLoginData(loginUser);

                 Intent intent = new Intent(Register.this, Login.class) ;
                 startActivity(intent);
                 finish();
                 //Toast.makeText(Register.this, "Registered Successfully ,hit Login Button to Log in:) " , Toast.LENGTH_LONG).show();

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

    /**
     * change the bordar color and change the right icon
     * for EditTexts
     * @param bordar
     * @param icon
     * @param text
     */
    public void changeBordarAndIcon(Drawable bordar , Drawable icon , EditText text){
        text.setCompoundDrawablesWithIntrinsicBounds(null, null ,icon ,null);
        text.setBackground(bordar);
        text.setPadding(0 ,0,20,0);
    }

    /**
     * set the error for textFields
     * @param view
     * @param error
     */
    public void setErrorViews(TextView view , String error){
        view.setText(error);
    }

    /**
     * check the validity for password
     * should not be null
     * should not equal name
     * passwords must be matched
     * @param passwordText
     * @param name
     * @param confirm
     * @return int
     */

    public int isPasswordValid(String passwordText, String name , String confirm){
        if(passwordText.length() < 5)
            return 1 ;
        if(passwordText.equals(name))
            return 2 ;
        if(!passwordText.equals(confirm))
            return 3 ;
        else
            return 4 ;

    }

    /**
     * check if the name entered by user is valid or not
     * @param name
     * @return
     */
    public boolean isNameValid(String name){
        if(name.matches("[a-zA-Z_]+")){
            return true ;
        }
        return false ;
    }

}
