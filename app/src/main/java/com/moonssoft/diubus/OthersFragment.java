package com.moonssoft.diubus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class OthersFragment extends Fragment {
    private ListView mListView;
    private SetScheduleAdapter mAdapter;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefAll;
    private ChildEventListener mChildListener;

    public OthersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_schedule, container, false);


        mDatabase = FirebaseDatabase.getInstance();

        mRefAll = mDatabase.getReference("all_info");
        Query query = mRefAll.orderByChild("toFrom").equalTo("others");

        mListView = (ListView) view.findViewById(R.id.list_view_schedule);

        //For Hiding the non required view
        View view1 = (View) view.findViewById(R.id.title_show_schedule);
        view1.setVisibility(View.GONE);


        ArrayList<BusItems> items = new ArrayList<>();
        mAdapter = new SetScheduleAdapter(getContext(), R.layout.bus_list_item, items);
        mListView.setAdapter(mAdapter);


        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                BusItems busName = dataSnapshot.getValue(BusItems.class);
                mAdapter.add(busName);
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



        return view;
    }

}
