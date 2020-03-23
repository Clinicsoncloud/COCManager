package com.coc.cocmanager.Fragments;

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
import com.google.android.material.tabs.TabLayout;

/**
 *created by ketan 23-3-2020
 */
public class InstallationHomeFragment extends Fragment {

    //region variables
    private ViewPager pager;
    private TabLayout tabLayout;

    //endregion

    public InstallationHomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InstallationHomeFragment newInstance() {
        InstallationHomeFragment fragment = new InstallationHomeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_installation_home, container, false);

        setupUI(rootView);
        setupEvents();
        initializeData();
        return rootView;
    }

    /**
     *
     */
    private void initializeData() {
        pager.setAdapter(new InstallationViewPagerAdapter(getChildFragmentManager()));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        InstallationViewPagerAdapter adapter = new InstallationViewPagerAdapter(getChildFragmentManager());
        tabLayout.setupWithViewPager(pager);
        pager.setAdapter(adapter);
    }

    /**
     *
     */
    private void setupEvents() {

    }

    /**
     *
     * @param rootView
     */
    private void setupUI(View rootView) {
        pager = rootView.findViewById(R.id.viewpager_instatllation);
        tabLayout = rootView.findViewById(R.id.tab_layout);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) { }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
