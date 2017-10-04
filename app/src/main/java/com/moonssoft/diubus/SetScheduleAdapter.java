package com.moonssoft.diubus;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MoonS on 27-01-17.
 */

public class SetScheduleAdapter extends ArrayAdapter<BusItems> {
    public SetScheduleAdapter(Context context, int resource, List<BusItems> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = ((Activity) getContext()).getLayoutInflater().
                    inflate(R.layout.bus_list_item, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.bus_name_textView);
        TextView textView2 = (TextView) convertView.findViewById(R.id.bus_time_textView);
        TextView textView3 = (TextView) convertView.findViewById(R.id.bus_from_textView);
        TextView textView4 = (TextView) convertView.findViewById(R.id.bus_to_textView);

        BusItems item = getItem(position);

        textView.setText(item.getBusName());
        textView2.setText(item.getBusTime());

        textView3.setText(item.getBusFrom());
        textView4.setText(item.getBusTo());


        return convertView;
    }
}
