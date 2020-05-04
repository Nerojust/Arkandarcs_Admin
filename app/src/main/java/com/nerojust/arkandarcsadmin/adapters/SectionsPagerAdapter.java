package com.nerojust.arkandarcsadmin.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.fragments.CompletedFragment;
import com.nerojust.arkandarcsadmin.fragments.NewOrderFragment;
import com.nerojust.arkandarcsadmin.fragments.ReadyToShipFragment;
import com.nerojust.arkandarcsadmin.fragments.ShippedFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.pending, R.string.ready_to_ship, R.string.shipped, R.string.completed};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NewOrderFragment.newInstance(position);
            case 1:
                return ReadyToShipFragment.newInstance(position);
            case 2:
                return ShippedFragment.newInstance(position);
            case 3:
                return CompletedFragment.newInstance(position);
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 4;
    }
}