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
import com.coc.cocmanager.adapter.InventoryViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class InventoryFragment extends Fragment {

    //region variables

    private TabLayout tabInventory;
    private ViewPager pagerInventory;

    //endregion
    public InventoryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InventoryFragment newInstance(String param1, String param2) {
        InventoryFragment fragment = new InventoryFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_inventory, container, false);

        setupUI(rootView);
        initializeData();
        return rootView;
    }

    private void initializeData() {

        setViewPagerAdapter();
    }

    private void setViewPagerAdapter() {
        pagerInventory.setAdapter(new InstallationViewPagerAdapter(getChildFragmentManager()));
        tabInventory.setTabGravity(TabLayout.GRAVITY_FILL);
        InventoryViewPagerAdapter adapter = new InventoryViewPagerAdapter(getChildFragmentManager());
        tabInventory.setupWithViewPager(pagerInventory);
        pagerInventory.setAdapter(adapter);
    }

    private void setupUI(View rootView) {
        tabInventory = rootView.findViewById(R.id.tab_inventory);
        pagerInventory = rootView.findViewById(R.id.pager_inventory);
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
