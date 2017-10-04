package com.moonssoft.diubus;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by MoonS on 25-01-17.
 */

public class CategoryAdapter extends FragmentStatePagerAdapter {
    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new PC2MCFragment();
            case 1: return new MC2PCFragment();
            case 2: return new PC2UCFragment();
            case 3: return new UC2PCFragment();
            case 4: return new OthersFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return mContext.getString(R.string.category_pc2mc);
            case 1: return mContext.getString(R.string.category_mc2pc);
            case 2: return mContext.getString(R.string.category_pc2uc);
            case 3: return mContext.getString(R.string.category_uc2pc);
            case 4: return mContext.getString(R.string.category_cnb);
            default: return null;
        }
    }
}
