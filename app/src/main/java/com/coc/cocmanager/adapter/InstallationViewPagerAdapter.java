package com.coc.cocmanager.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.coc.cocmanager.fragments.InstallationFragment;
import com.coc.cocmanager.fragments.PipelineFragment;
import com.coc.cocmanager.fragments.TransportFragment;

/**
 * created by ketan 23-3-2020
 */

public class InstallationViewPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitles = new String[]{"Installation", "Transport", "Pipeline"};

    public InstallationViewPagerAdapter(FragmentManager fm) {
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
                return InstallationFragment.newInstance();
            case 1:
                return TransportFragment.newInstance();
            case 2:
                return PipelineFragment.newInstance();
            default:
                return InstallationFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
