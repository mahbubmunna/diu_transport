package com.moonssoft.diubus;


import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetBusScheduleFragment extends Fragment {

    private EditText mEditTextName;
    static EditText mEditTextTime;
    private EditText mEditTextTo;
    private EditText mEditTextFrom;
    private Button mButton;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefAll;

    public static String mTime;


    public SetBusScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_schedule, container, false);

        mDatabase = FirebaseDatabase.getInstance();

        //Reference to the child
        mRefAll = mDatabase.getReference().child("all_info");

        mEditTextName = (EditText) view.findViewById(R.id.editText_bus_name);
       mEditTextTime = (EditText) view.findViewById(R.id.editText_bus_time);
        mEditTextFrom = (EditText) view.findViewById(R.id.editText_bus_from);
        mEditTextTo = (EditText) view.findViewById(R.id.editText_bus_to);

        mEditTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.support.v4.app.DialogFragment timeFragment = new TimePickerFragment();
                timeFragment.show(getFragmentManager(), "Pick time");

            }
        });



        mButton = (Button) view.findViewById(R.id.button_add);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = mEditTextName.getText().toString().toUpperCase();
                String from = mEditTextFrom.getText().toString().toUpperCase();
                String to = mEditTextTo.getText().toString().toUpperCase();
                String toFrom = from + "-" + to;

                if (toFrom.contentEquals("PC-MC") ||
                        toFrom.contentEquals("PC-UC") ||
                        toFrom.contentEquals("MC-PC") ||
                        toFrom.contentEquals("UC-PC")){

                }else {
                    toFrom = "others";
                }

                BusItems busAll = new BusItems(name, mTime, from, to, toFrom);


                if (mEditTextName.getText().length() > 0 &&
                        mEditTextTime.getText().length() > 0 &&
                        mEditTextFrom.getText().length() > 0 &&
                        mEditTextTo.getText().length() > 0 ){


                    //Push the data the Base
                    mRefAll.push().setValue(busAll);

                    mEditTextName.setText("");
                    mEditTextTo.setText("");
                    mEditTextFrom.setText("");
                    mEditTextTime.setText("");

                    Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_SHORT).show();

                    //Notification code
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());

                    mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                    mBuilder.setContentTitle("Schedule changed, Click to view!");
                    mBuilder.setContentText("Bus Name: Shurja  Time: 8.30");

                    NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(001, mBuilder.build());
                }else{
                    Toast.makeText(getContext(), "Please fill the form properly.", Toast.LENGTH_SHORT).show();
                }


            }
        });



        return view;
    }

}
