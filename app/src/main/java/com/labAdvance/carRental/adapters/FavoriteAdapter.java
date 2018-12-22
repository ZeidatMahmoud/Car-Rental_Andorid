package com.labAdvance.carRental.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.labAdvance.carRental.R ;

import com.labAdvance.carRental.database.SqliteHelper;
import com.labAdvance.carRental.models.Car;

import java.util.List;

public class FavoriteAdapter extends ArrayAdapter<Car> {
    private List<Car> list ;
    private Context context ;
    private SqliteHelper mydb ;
    public FavoriteAdapter(Context context, List<Car> list , SqliteHelper mydb) {
        super(context, 0 ,list);
        this.list =list ;
        this.context = context ;
        this.mydb = mydb ;
    }


    @Override
    public View getView(int position, @NonNull View convertView, @Nullable ViewGroup parent) {

        View listItem  = convertView ;
        if(listItem == null){
            listItem = LayoutInflater.from(this.context).inflate(R.layout.car_menu_list_adapter, parent, false);
        }
        //mydb = new SqliteHelper(context) ;
        final Car car = list.get(position) ;
        TextView name = (TextView)listItem.findViewById(R.id.name) ;
        TextView factory = (TextView)listItem.findViewById(R.id.factory) ;
        TextView nameContent = (TextView)listItem.findViewById(R.id.nameContent) ;
        TextView favtoryContent  =(TextView)listItem.findViewById(R.id.factoryContent);
        final ImageButton like = (ImageButton) listItem.findViewById(R.id.love);
        like.setImageResource(R.drawable.love);
        name.setText("Car Name : ");
        factory.setText("Factory : ");
        nameContent.setText(car.getMake());
        favtoryContent.setText(car.getModel());

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like.setImageResource(R.drawable.your_fav);
                mydb.removeFavoirite(car);

            }
        });

        return listItem ;


    }
}
