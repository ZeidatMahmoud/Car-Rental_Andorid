package com.labAdvance.carRental.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.labAdvance.carRental.R;
import com.labAdvance.carRental.adapters.FavoriteAdapter;
import com.labAdvance.carRental.database.SqliteHelper;
import com.labAdvance.carRental.models.Car;

import java.util.ArrayList;


public class FavoritesFragment extends Fragment {
    private ArrayList<Car> list  = new ArrayList() ;
    private ListView listView ;
    private FavoriteAdapter adapter  ;
    private SqliteHelper mydb ;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try{
            mydb = new SqliteHelper(getActivity()) ;
            list = mydb.getAllFavorite() ;
            listView = getView().findViewById(R.id.FavListView) ;
            adapter = new FavoriteAdapter(getContext() ,list ,mydb) ;
            listView.setAdapter(adapter);

        }catch (Exception e){
            Toast.makeText(getActivity(), "There is no Elements", Toast.LENGTH_SHORT).show();

        }


    }
}
