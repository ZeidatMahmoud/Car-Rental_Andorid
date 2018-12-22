package com.labAdvance.carRental.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.labAdvance.carRental.R;
import com.labAdvance.carRental.activities.Home;
import com.labAdvance.carRental.database.SqliteHelper;
import com.labAdvance.carRental.models.RegisterModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProfileFragment extends Fragment {
    private ImageView profile ;
    private Button change ,save  ;
    private EditText firstName ,lastName ,password ,phone ;
    private SqliteHelper mydb ;
    private RegisterModel registerModel ;
    private TextView error ;

    private SharedPreferences sharedPreferences ;
    private static final int PICK_IMAGE = 1 ;


    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mydb = new SqliteHelper(getActivity()) ;
        profile =(ImageView)getView().findViewById(R.id.profileFragmentProfile) ;
        change =(Button)getView().findViewById(R.id.changeProfilePic) ;
        save =(Button)getView().findViewById(R.id.profileSave) ;

        firstName =(EditText)getView().findViewById(R.id.profileFragmentFirstName) ;
        lastName = (EditText)getView().findViewById(R.id.profileFragmentLastName) ;
        password  =(EditText)getView().findViewById(R.id.profileFragmentPass) ;
        phone =(EditText)getView().findViewById(R.id.profileFragmentPhone) ;
        error  = (TextView)getView().findViewById(R.id.errorMessage);

        sharedPreferences = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userName", "no");
        if(!userEmail.equals("no")){
            RegisterModel user = mydb.getUserData(userEmail) ;
            registerModel = user ;
            byte[] array = user.getProfile();
            Bitmap bitmap = BitmapFactory.decodeByteArray(array, 0, array.length);
            Drawable image = new BitmapDrawable(getResources(), bitmap);
            registerModel.setProfile(array);

            profile.setBackground(image);
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            password.setText(user.getPassword());
            phone.setText(user.getPhone());

        }
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isNameValid(firstName.getText().toString())){
                    //Toast.makeText(getActivity(), " Name is not Valid", Toast.LENGTH_SHORT).show();
                    error.setText("Name is not Valid");
                    return;
                }

                if(!isNameValid(lastName.getText().toString())){
                    //Toast.makeText(getActivity(), " Name is not Valid", Toast.LENGTH_SHORT).show();
                    error.setText("Name is not Valid");

                    return;
                }

                switch (isPasswordsValid(password.getText().toString(),
                        firstName.getText().toString(), lastName.getText().toString())) {
                    case 1:
                        //Toast.makeText(getActivity(), " Password is Short", Toast.LENGTH_SHORT).show();
                        error.setText("Password is Short");


                        return;
                    case 2:
                        //Toast.makeText(getActivity(), " Password must not equal First name !", Toast.LENGTH_SHORT).show();
                        error.setText("Password must not equal First name");

                        return;
                    case 3:
                        //Toast.makeText(getActivity(), " Password must not equal Last name !", Toast.LENGTH_SHORT).show();
                        error.setText("Password must not equal Last name");


                        return;
                    default:
                        //Toast.makeText(getActivity(), " Error !", Toast.LENGTH_SHORT).show();


                }
               registerModel.setFirstName(firstName.getText().toString());
                registerModel.setLastName(lastName.getText().toString());
                registerModel.setPassword(password.getText().toString());
                registerModel.setPhone(phone.getText().toString());
                registerModel.setProfile(convertDrawableToByte(profile.getBackground()));

                mydb.updateRegisterData(registerModel);




            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            //TODO: action
            Uri imageUri = data.getData();

            try {
                InputStream stream = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);

                profile.setBackground(drawable);

            } catch (FileNotFoundException e) {

            }

        }
    }


    /**
     * validate Passwords
     *
     * @param password
     * @param
     * @return
     */
    public int isPasswordsValid(String password, String FirstName, String LastName) {
        if (password.length() < 5)
            return 1;
        if (password.equals(FirstName))
            return 2;
        if (password.equals(LastName))
            return 3;
        else
            return 5;
    }

    /**
     * validate FirstName and LastName
     *
     * @param name
     * @return
     */
    public boolean isNameValid(String name) {
        if (name.matches("[a-zA-Z_]+")) {
            return true;
        }
        return false;
    }

    public byte[] convertDrawableToByte(Drawable d) {
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        return bitmapdata;
    }


}
