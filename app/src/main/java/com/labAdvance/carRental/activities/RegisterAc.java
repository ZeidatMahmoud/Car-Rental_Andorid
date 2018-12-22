package com.labAdvance.carRental.activities;

import android.content.Intent;
import android.database.DataSetObserver;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.labAdvance.carRental.R;
import com.labAdvance.carRental.database.SqliteHelper;
import com.labAdvance.carRental.models.RegisterModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterAc extends AppCompatActivity {
    private EditText email, firstName, lastName, password, confirm, phone;
    private TextView emailError, firstNameError, lastNameError, passwordError, confirmPasswordError;
    private Spinner country, city, gender;
    private CheckBox agree;
    private Button register;
    private ImageView profile;
    private TextView upload;
    public static final int PICK_IMAGE = 1;
    private SqliteHelper mydb;
    private String choosenCountry;
    private ArrayAdapter adapter;
    private List<String> list = new ArrayList<>();
    private TextView phoneCountry;
    private byte[] profileBytes ;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            //TODO: action
            upload.setText("");
            upload.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            Uri imageUri = data.getData();

            try {
                InputStream stream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);

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
        mydb = new SqliteHelper(this);

        /**
         * initialize Components
         *
         */
        email = (EditText) findViewById(R.id.registerEmail);
        firstName = (EditText) findViewById(R.id.registerFirstName);
        lastName = (EditText) findViewById(R.id.registerLastName);
        password = (EditText) findViewById(R.id.registerPassword);
        confirm = (EditText) findViewById(R.id.registerConfirmPassword);
        phone = (EditText) findViewById(R.id.registerPhone);

        country = (Spinner) findViewById(R.id.registerCountry);
        gender = (Spinner) findViewById(R.id.registerGender);
        city = (Spinner) findViewById(R.id.registerCity);

        agree = (CheckBox) findViewById(R.id.registerAgree);
        register = (Button) findViewById(R.id.registerButton);
        upload = (TextView) findViewById(R.id.registerUploadProfile);

        //error TextViews
        emailError = (TextView) findViewById(R.id.registerErrorEmail);
        firstNameError = (TextView) findViewById(R.id.registerErrorFirstName);
        lastNameError = (TextView) findViewById(R.id.registerErrorLastName);
        passwordError = (TextView) findViewById(R.id.registerErrorPassword);
        confirmPasswordError = (TextView) findViewById(R.id.registerErrorConfirm);
        //error TextViews finished

        phoneCountry = (TextView) findViewById(R.id.registerPhoneCountry);


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
                Drawable errorBorder = getResources().getDrawable(R.drawable.txt_border_error);
                Drawable errorIcon = getResources().getDrawable(R.drawable.error);
                Drawable successBordar = getResources().getDrawable(R.drawable.txt_true_green);
                Drawable successIcon = getResources().getDrawable(R.drawable.true_green);
                /**
                 * email validation
                 */
                switch (isEmailValid(email.getText().toString())) {
                    case 1:
                        doOnValidationError(email, errorIcon, errorBorder, emailError, "Email is Used");
                        return;
                    case 2:
                        doOnValidationError(email, errorIcon, errorBorder, emailError, "Email is invalid");
                        return;
                    default:
                        doOnValidationSuccess(email, successIcon, successBordar, emailError, "");

                }
                /**
                 * First name and Last name Validation
                 */
                if (!isNameValid(firstName.getText().toString())) {
                    doOnValidationError(firstName, errorIcon, errorBorder, firstNameError, "Name is not valid");
                    return;
                } else {
                    doOnValidationSuccess(firstName, successIcon, successBordar, firstNameError, "");

                }

                if (!isNameValid(lastName.getText().toString())) {
                    doOnValidationError(lastName, errorIcon, errorBorder, lastNameError, "Name is not valid");
                    return;
                } else {
                    doOnValidationSuccess(lastName, successIcon, successBordar, lastNameError, "");

                }
                /**
                 * password validation
                 */
                switch (isPasswordsValid(password.getText().toString(), confirm.getText().toString(),
                        firstName.getText().toString(), lastName.getText().toString())) {
                    case 1:
                        doOnValidationError(password, errorIcon, errorBorder, passwordError, "Password is short !");
                        return;
                    case 2:
                        doOnValidationError(password, errorIcon, errorBorder, passwordError, "Password must not equal First name !");
                        return;
                    case 3:
                        doOnValidationError(password, errorIcon, errorBorder, passwordError, "Password must not equal Last name !");
                        return;
                    case 4:
                        doOnValidationError(password, errorIcon, errorBorder, passwordError, "Passwords not match !");
                        return;
                    default:
                        doOnValidationSuccess(password, successIcon, successBordar, passwordError, "");
                        doOnValidationSuccess(confirm, successIcon, successBordar, confirmPasswordError, "");
                }


                if (!agree.isChecked()) {
                    agree.setTextColor(getResources().getColor(R.color.RedForError));
                    agree.setCompoundDrawablesWithIntrinsicBounds(null, null, errorIcon, null);
                    Toast.makeText(RegisterAc.this, "accept the agreement", Toast.LENGTH_SHORT).show();
                    return;
                }
                try{
                    profileBytes = convertDrawableToByte(upload.getBackground());
                }
                catch (Exception e){
                    Toast.makeText(RegisterAc.this, "Choose Profile Picture", Toast.LENGTH_SHORT).show();
                    return;
                }


                RegisterModel registerModel = new RegisterModel(email.getText().toString(),
                        firstName.getText().toString(),
                        lastName.getText().toString(),
                        password.getText().toString(),
                        gender.getSelectedItem().toString(),
                        country.getSelectedItem().toString(),
                        city.getSelectedItem().toString(),
                        phoneCountry.getText().toString().concat(phone.getText().toString()), profileBytes);
                try{
                    boolean res = mydb.insertRegsiterData(registerModel);
                    if (res) {
                        Intent i = new Intent(RegisterAc.this,Login.class);
                        startActivity(i);
                        finish();
                    }
                }catch (Exception e){
                    Toast.makeText(RegisterAc.this, "Error Try Again", Toast.LENGTH_SHORT).show();
                }



            }
        });

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setCityAndPhoneAccordingToCountry(country.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

    }

    /**
     * validate the email address
     *
     * @param email
     * @return
     */
    public int isEmailValid(String email) {
        if (mydb.checkExistingEmail(email)) {
            return 1;
        }
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches())
            return 2;
        else
            return 0;

    }

    /**
     * validate Passwords
     *
     * @param password
     * @param confirm
     * @return
     */
    public int isPasswordsValid(String password, String confirm, String FirstName, String LastName) {
        if (password.length() < 5)
            return 1;
        if (password.equals(FirstName))
            return 2;
        if (password.equals(LastName))
            return 3;
        if (!password.equals(confirm))
            return 4;
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

    /**
     * set List of Country Cities
     * set the Start of Phone number
     *
     * @param country
     */
    public void setCityAndPhoneAccordingToCountry(String country) {
        switch (country) {
            case "Palestine":
                phoneCountry.setText("+970");
                list = Arrays.asList(getResources().getStringArray(R.array.palestine_city));
                adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
                city.setAdapter(adapter);
                return;
            case "Lebanon":
                phoneCountry.setText("+961");
                list = Arrays.asList(getResources().getStringArray(R.array.lebanon_city));
                adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
                city.setAdapter(adapter);
                return;
            case "Algeria":
                phoneCountry.setText("+213");
                list = Arrays.asList(getResources().getStringArray(R.array.algiria_city));
                adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
                city.setAdapter(adapter);
                return;
            case "Iraq":
                phoneCountry.setText("+964");
                list = Arrays.asList(getResources().getStringArray(R.array.iraq_city));
                adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
                city.setAdapter(adapter);
                return;

        }

    }

    /**
     * change the border color and icon for error
     * @param text
     * @param icon
     * @param border
     * @param errorText
     * @param errorMessage
     */
    public void doOnValidationError(EditText text, Drawable icon, Drawable border
            , TextView errorText, String errorMessage) {
        errorText.setTextSize(12);
        text.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
        text.setBackground(border);
        text.setPadding(0, 0, 20, 0);
        errorText.setText(errorMessage);
    }

    /**
     * change the borderColor and icon
     * @param text
     * @param icon
     * @param border
     * @param errorText
     * @param errorMessage
     */
    public void doOnValidationSuccess(EditText text, Drawable icon, Drawable border
            , TextView errorText, String errorMessage) {
        text.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
        text.setBackground(border);
        text.setPadding(0, 0, 20, 0);
        errorText.setText(errorMessage);

    }

    /**
     * convert image profile to byte inorder to save in database
     * @param d
     * @return
     */
    public byte[] convertDrawableToByte(Drawable d) {
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        return bitmapdata;
    }


}
