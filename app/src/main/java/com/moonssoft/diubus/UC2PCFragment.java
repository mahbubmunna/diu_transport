package com.moonssoft.diubus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UC2PCFragment extends Fragment {
    private ListView mListView;
    private BusArrayAdapter mAdapter;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefAll;
    private ChildEventListener mChildListener;

    public UC2PCFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragments_activity_all, container, false);

        mDatabase = FirebaseDatabase.getInstance();

        mRefAll = mDatabase.getReference("all_info");
        Query query = mRefAll.orderByChild("toFrom").equalTo("UC-PC");

        //ArrayList for a list of Buses
        final ArrayList<BusData> buses = new ArrayList<BusData>();


        //Initializing the Array Adapter and the listView
        mAdapter = new BusArrayAdapter(getContext(), buses);
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);

        //Setting the adapter to the listView
        listView.setAdapter(mAdapter);


        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                BusData data = dataSnapshot.getValue(BusData.class);
                mAdapter.add(data);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        query.addChildEventListener(mChildListener);
        query.keepSynced(false);


        return rootView;
    }

}
