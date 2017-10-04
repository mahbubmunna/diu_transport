package com.moonssoft.diubus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MoonS on 26-01-17.
 */

public class BusArrayAdapter extends ArrayAdapter<BusData> {
    public BusArrayAdapter(Context context,List<BusData> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        //Getting the item from the list by the position
        BusData bus = getItem(position);

        //Setting the image to the view

        //Setting the bus name to the busView
        TextView busName = (TextView) listItemView.findViewById(R.id.bus_name1);
        busName.setText(bus.getBusName());

        //Setting the bus time to the View
        TextView busTime = (TextView) listItemView.findViewById(R.id.bus_departure_time1);
        busTime.setText(bus.getBusTime());


        return listItemView;

    }
}
