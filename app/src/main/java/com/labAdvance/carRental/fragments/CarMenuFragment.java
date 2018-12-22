package com.labAdvance.carRental.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.labAdvance.carRental.R;
import com.labAdvance.carRental.adapters.CarMenuAdapter;
import com.labAdvance.carRental.database.SqliteHelper;
import com.labAdvance.carRental.models.Car;

import java.util.ArrayList;
import java.util.List;

public class CarMenuFragment extends Fragment {
    private CarMenuAdapter adapter ;
    private SqliteHelper mydb ;
    private ArrayList<Car> list ;
    private ListView listView ;
    private EditText search  ;
    private String searchedText ;
    private ArrayList<Car> searchedList ;


    public CarMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mydb = new SqliteHelper(getActivity()) ;
        list = mydb.getAllCars() ;
        searchedList = new ArrayList<>() ;
        listView = getView().findViewById(R.id.carMenuList) ;
        search = (EditText) getView().findViewById(R.id.carMenuSearch);
        adapter = new CarMenuAdapter(getContext(),list) ;
        listView.setAdapter(adapter);

        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                searchedText = search.getText().toString() ;
                Log.d("TAG", "onFocusChange: ");
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchedText = search.getText().toString() ;
                if(searchedText.equals("")){
                    listView.setAdapter(new CarMenuAdapter(getContext(), mydb.getAllCars()));
                }
                else {
                    for (Car car : list) {
                        if (car.getMake().equals(searchedText)
                                || car.getModel().toLowerCase().equals(searchedText)
                                || car.getMake().toLowerCase().equals(searchedText)
                                || car.getMake().equals(searchedText)
                                || car.getPrice().equals(searchedText)) {
                            searchedList.add(car);
                        }
                        listView.setAdapter(new CarMenuAdapter(getContext(), searchedList));
                    }
                }

            }
        });




    }
}
