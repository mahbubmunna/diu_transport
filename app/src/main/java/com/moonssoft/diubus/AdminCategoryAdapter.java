package com.moonssoft.diubus;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by MoonS on 27-01-17.
 */

public class AdminCategoryAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    public AdminCategoryAdapter(Context context, android.support.v4.app.FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new ShowScheduleFragment();
            case 1: return new SetBusScheduleFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return mContext.getString(R.string.show_schedule);
            case 1: return mContext.getString(R.string.add_schedule);
            default: return null;
        }
    }
}
