package com.coc.cocmanager.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.coc.cocmanager.fragments.AvailableStockFragment;
import com.coc.cocmanager.fragments.StockInFragment;
import com.coc.cocmanager.fragments.StockOutListingFragment;

/**
 * created by ketan 23-3-2020
 */

public class InventoryViewPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitles = new String[]{"Available", "Stock In", "Stock Out"};

    public InventoryViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {

            case 0:
                return AvailableStockFragment.newInstance();
            case 1:
                return StockInFragment.newInstance();
            case 2:
                return StockOutListingFragment.newInstance();
            default:
                return AvailableStockFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
