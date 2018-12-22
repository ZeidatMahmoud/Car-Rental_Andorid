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

import com.labAdvance.carRental.R;
import com.labAdvance.carRental.database.SqliteHelper;
import com.labAdvance.carRental.models.Car;

import java.util.List;

public class ReserveAdapter extends ArrayAdapter<Car> {
    private List<Car> list ;
    private Context context ;
    private SqliteHelper mydb ;
    private boolean pressed  =true ;

    public ReserveAdapter(Context context, List<Car>list) {
        super(context, 0 ,list);
        this.list =list ;
        this.context = context ;
    }

    @Override
    public View getView(int position, @NonNull View convertView, @Nullable ViewGroup parent) {

        View listItem  = convertView ;
        if(listItem == null){
            listItem = LayoutInflater.from(this.context).inflate(R.layout.car_menu_list_adapter, parent, false);
        }
        mydb = new SqliteHelper(context) ;
        final Car car = list.get(position) ;
        TextView name = (TextView)listItem.findViewById(R.id.name) ;
        TextView factory = (TextView)listItem.findViewById(R.id.factory) ;
        TextView nameContent = (TextView)listItem.findViewById(R.id.nameContent) ;
        TextView favtoryContent  =(TextView)listItem.findViewById(R.id.factoryContent);
        final ImageButton reserved = (ImageButton) listItem.findViewById(R.id.carMenuListReserved);
        reserved.setImageResource(R.drawable.reserverd);
        name.setText("Car Name : ");
        factory.setText("Factory : ");
        nameContent.setText(car.getMake());
        favtoryContent.setText(car.getModel());


        reserved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserved.setImageResource(R.drawable.un_reserved);
                mydb.removeReserved(car);


        }

        });

        return listItem ;


    }
}
