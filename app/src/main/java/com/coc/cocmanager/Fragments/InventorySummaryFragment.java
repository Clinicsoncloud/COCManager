package com.coc.cocmanager.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.coc.cocmanager.R;

/**
 * created by ketan 24-3-2020
 */
public class InventorySummaryFragment extends Fragment {

    //region variables
    private Button btnCheckAvailability;
    //endregion

    //region methods

    public InventorySummaryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InventorySummaryFragment newInstance() {
        InventorySummaryFragment fragment = new InventorySummaryFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_inventory_summary, container, false);

        setupUI(rootView);
        setupEvents();
        return rootView;
    }

    private void setupEvents() {
        btnCheckAvailability.setOnClickListener(v -> { openCheckAvailabiltyScreen(); });
    }

    private void openCheckAvailabiltyScreen() {
        Fragment fragment = new CheckAvailabilityFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }

    private void setupUI(View rootView) {
        btnCheckAvailability = rootView.findViewById(R.id.btn_check_availability);
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

    //endregion

}
