package com.labAdvance.carRental.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.labAdvance.carRental.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterAc extends AppCompatActivity {
    private EditText email , firstName, lastName ,  password ,confirm  ,phone ;
    private Spinner country , city , gender ;
    private CheckBox agree ;
    private Button register ;
    private ImageView profile ;
    private TextView upload ;
    public static final int PICK_IMAGE = 1;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            //TODO: action
            upload.setText("");
            upload.setCompoundDrawablesWithIntrinsicBounds(null, null ,null ,null);
            Uri imageUri = data.getData();

            try {
                InputStream stream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                Drawable drawable = new BitmapDrawable(getResources(),bitmap);

                upload.setBackground(drawable);

            } catch (FileNotFoundException e) {
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * initialize Components
         *
         */
        email = (EditText)findViewById(R.id.registerEmail) ;
        firstName = (EditText)findViewById(R.id.registerFirstName) ;
        lastName = (EditText)findViewById(R.id.registerLastName) ;
        password = (EditText)findViewById(R.id.registerPassword) ;
        confirm = (EditText)findViewById(R.id.registerConfirmPassword) ;
        phone = (EditText)findViewById(R.id.registerPhone) ;

        country = (Spinner)findViewById(R.id.registerCountry);
        gender = (Spinner)findViewById(R.id.registerGender);
        city = (Spinner)findViewById(R.id.registerCity);

        agree = (CheckBox)findViewById(R.id.registerAgree);
        register = (Button)findViewById(R.id.registerButton) ;

        //profile  = (ImageView)findViewById(R.id.registerProfilePic);
        upload  =(TextView)findViewById(R.id.registerUploadProfile);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }
}
