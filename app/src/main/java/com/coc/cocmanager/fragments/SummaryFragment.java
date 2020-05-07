package com.coc.cocmanager.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coc.cocmanager.R;
import com.coc.cocmanager.adapter.InstallationViewPagerAdapter;
import com.coc.cocmanager.adapter.SummaryViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class SummaryFragment extends Fragment {

    //region variables
    private ViewPager summaryPager;
    private TabLayout summaryTabLayout;
    //endregion

    public SummaryFragment() {
        // Required empty public constructor
    }

    public static SummaryFragment newInstance(String param1, String param2) {
        SummaryFragment fragment = new SummaryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_summary, container, false);
        setupUI(rootView);
        initializeData();
        return rootView;
    }

    private void initializeData() {
        setViewpagerAdapter();
    }

    private void setViewpagerAdapter() {
        summaryPager.setAdapter(new InstallationViewPagerAdapter(getChildFragmentManager()));
        summaryTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        SummaryViewPagerAdapter adapter = new SummaryViewPagerAdapter(getChildFragmentManager());
        summaryTabLayout.setupWithViewPager(summaryPager);
        summaryPager.setAdapter(adapter);
    }

    private void setupUI(View rootView) {
        summaryPager = rootView.findViewById(R.id.viewpager_summary);
        summaryTabLayout = rootView.findViewById(R.id.tab_layout_summary);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
