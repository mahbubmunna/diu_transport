package com.moonssoft.diubus;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowScheduleFragment extends Fragment {

    private ListView mListView;
    private SetScheduleAdapter mAdapter;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefAll;
    private ChildEventListener mChildListener;

    private int position;
    private ArrayList<String> positionList;
    ArrayList<BusItems> items;

    public ShowScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_schedule, container, false);


        mDatabase = FirebaseDatabase.getInstance();

        mRefAll = mDatabase.getReference().child("all_info");

        positionList = new ArrayList<>();

        mListView = (ListView) view.findViewById(R.id.list_view_schedule);

        items = new ArrayList<>();
        mAdapter = new SetScheduleAdapter(getContext(), R.layout.bus_list_item, items);

        mListView.setAdapter(mAdapter);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                actionMode.setTitle("Actions");
                position = i;

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                actionMode.getMenuInflater().inflate(R.menu.actions_bus_items, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_delete:
                        //Getting network access
                        ConnectivityManager cm =
                                (ConnectivityManager)getContext()
                                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        boolean isConnected = activeNetwork != null &&
                                activeNetwork.isConnectedOrConnecting();

                        if (isConnected){
                            mAdapter.remove(items.get(position));
                            mAdapter.notifyDataSetChanged();
                            mRefAll.child(positionList.get(position)).removeValue();


                            //Message for Success
                            ShowDialogFragment dialog = new ShowDialogFragment();
                            ShowDialogFragment.setDialog(dialog,"Info", "Item deleted.");
                            dialog.show(getFragmentManager(), "TAG");

                        } else {
                            //Message for error
                            ShowDialogFragment dialog = new ShowDialogFragment();
                            ShowDialogFragment.setDialog(dialog,"Warning", "Check Network Connection!");
                            dialog.show(getFragmentManager(), "TAG");
                        }

                        actionMode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });


        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                BusItems busName = dataSnapshot.getValue(BusItems.class);
                String key = dataSnapshot.getKey();
                positionList.add(key);
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
        mRefAll.addChildEventListener(mChildListener);
        mRefAll.keepSynced(false);



        return view;
    }


}
