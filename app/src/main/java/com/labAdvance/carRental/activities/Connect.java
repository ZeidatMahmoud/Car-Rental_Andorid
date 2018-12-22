package com.labAdvance.carRental.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.labAdvance.carRental.Helpers.API;
import com.labAdvance.carRental.R;
import com.labAdvance.carRental.database.SqliteHelper;
import com.labAdvance.carRental.models.Car;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connect extends AppCompatActivity {
    private Button connect;
    private SqliteHelper mydb;
    private ProgressBar bar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        mydb = new SqliteHelper(this);
        connect = (Button) findViewById(R.id.connectButton);
        bar = (ProgressBar)findViewById(R.id.connectProgress);
        bar.setVisibility(View.INVISIBLE);
        /**
         * if user is checked to keep log in
         * then he will not connect to server
         * he just go to home activity
         */
       if(mydb.isUserLoggedIn() ){
           Intent i = new Intent(Connect.this , Home.class) ;
           startActivity(i);
           finish();
       }


        /**
         * connect to server and get the data on click ,
         * save it to database table name Car Table
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final API service = retrofit.create(API.class);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                Call<List<Car>> call = service.getCarDetails();
                call.enqueue(new Callback<List<Car>>() {
                    @Override
                    public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                        mydb.deleteAllCars();
                        for(Car car :response.body()){
                            mydb.insertCarData(car);
                        }
                        Intent i = new Intent(Connect.this, Login.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<List<Car>> call, Throwable t) {
                        bar.setVisibility(View.INVISIBLE);
                        Toast.makeText(Connect.this, "Not Connected , Try again", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });




    }

}
