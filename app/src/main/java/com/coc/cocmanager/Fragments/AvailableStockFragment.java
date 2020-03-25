package com.coc.cocmanager.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.coc.cocmanager.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

/**
 * created by ketan 25-3-2020
 */
public class AvailableStockFragment extends Fragment {

    //region variables
    private ExpandableRelativeLayout expandableItems;
    private ExpandableRelativeLayout expandableAddOns;
    private ExpandableRelativeLayout expandableConsumables;
    private Button btnItems;
    private Button btnAddOn;
    private Button btnConsumables;
    //endregion

    public AvailableStockFragment() {
        // Required empty public constructor
    }

    public static AvailableStockFragment newInstance() {
        AvailableStockFragment fragment = new AvailableStockFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_available_stock, container, false);
        setupUI(rootView);
        setupEvents();
        return rootView;
    }

    private void setupEvents() {
        btnItems.setOnClickListener(v -> {
            Log.e("items","click");
            expandableItems.toggle();
        });

        btnAddOn.setOnClickListener(v -> {
            Log.e("add_on","click");
            expandableAddOns.toggle();
        });

        btnConsumables.setOnClickListener(v -> {
            Log.e("consumables","click");
            expandableConsumables.toggle();
        });
    }

    private void setupUI(View rootView) {
        expandableItems = rootView.findViewById(R.id.expandable_items);
        expandableAddOns = rootView.findViewById(R.id.expandable_add_ons);
        expandableConsumables = rootView.findViewById(R.id.expandable_consumables);

        btnItems = rootView.findViewById(R.id.btn_items);
        btnAddOn = rootView.findViewById(R.id.btn_add_on);
        btnConsumables = rootView.findViewById(R.id.btn_consumables);
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
