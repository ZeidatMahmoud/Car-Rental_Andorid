package com.labAdvance.carRental.Helpers;

import com.labAdvance.carRental.models.Car;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    @GET("v2/5bfea5963100006300bb4d9a?fbclid=IwAR2jbVqB1fKsvvliKbERRZCkI9VLeZnrT8Dg_LJ6mBMCkWAnyaBDkpkEIR8/")
    Call<List<Car>> getCarDetails();
}
