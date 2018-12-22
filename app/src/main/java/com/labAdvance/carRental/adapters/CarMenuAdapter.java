package com.labAdvance.carRental.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.labAdvance.carRental.R;

import com.labAdvance.carRental.database.SqliteHelper;
import com.labAdvance.carRental.models.Car;
import com.labAdvance.carRental.models.RegisterModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CarMenuAdapter extends ArrayAdapter<Car> {
    private ArrayList<Car> list;
    private Context context;
    private boolean isPressed = true , pressed = true;

    private SqliteHelper mydb;
    private SharedPreferences sharedPreferences;

    public CarMenuAdapter(Context context, ArrayList<Car> list) {
        super(context, 0, list);
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {


        View listItem = convertView;

        if (listItem == null) {
            try {
                listItem = LayoutInflater.from(this.context).inflate(R.layout.car_menu_list_adapter, parent, false);

            } catch (Exception e) {
                Log.d("TAG", "getView: " + e.toString());
            }
        }
        sharedPreferences  = context.getSharedPreferences("Data" ,Context.MODE_PRIVATE) ;
        final String userEmail = sharedPreferences.getString("userName", "no");
        final Car car = list.get(position);
        TextView name = (TextView) listItem.findViewById(R.id.name);
        TextView factory = (TextView) listItem.findViewById(R.id.factory);
        TextView nameContent = (TextView) listItem.findViewById(R.id.nameContent);
        TextView favtoryContent = (TextView) listItem.findViewById(R.id.factoryContent);
        final ImageButton like = (ImageButton) listItem.findViewById(R.id.love);
        final ImageButton reserve = (ImageButton) listItem.findViewById(R.id.carMenuListReserved);
        name.setText("Car Name : ");
        factory.setText("Factory : ");
        nameContent.setText(car.getMake());
        favtoryContent.setText(car.getModel());
        mydb = new SqliteHelper(context);


        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPressed) {
                    like.setImageResource(R.drawable.love);
                    mydb.insertFavoriteCars(car);

                } else {
                    like.setImageResource(R.drawable.your_fav);
                    mydb.removeFavoirite(car);

                }

                isPressed = !isPressed;

            }
        });

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pressed) {
                    reserve.setImageResource(R.drawable.reserverd);
                    mydb.insertReserverdCars(car ,userEmail);

                } else {
                    reserve.setImageResource(R.drawable.un_reserved);
                    mydb.removeReserved(car);

                }

                pressed = !pressed;

            }

        });


        return listItem;

    }
}
