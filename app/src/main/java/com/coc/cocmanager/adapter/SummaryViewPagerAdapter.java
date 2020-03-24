package com.coc.cocmanager.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.coc.cocmanager.Fragments.InstallationFragment;
import com.coc.cocmanager.Fragments.InstallationSummaryFragment;
import com.coc.cocmanager.Fragments.InventoryFragment;
import com.coc.cocmanager.Fragments.InventorySummaryFragment;
import com.coc.cocmanager.Fragments.PipelineFragment;
import com.coc.cocmanager.Fragments.TransportFragment;

/**
 * created by ketan 23-3-2020
 */

public class SummaryViewPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitles = new String[]{"Installation", "Inventory"};

    public SummaryViewPagerAdapter(FragmentManager fm) {
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
                return InstallationSummaryFragment.newInstance();
            case 1:
                return InventorySummaryFragment.newInstance();
            default:
                return InstallationSummaryFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
