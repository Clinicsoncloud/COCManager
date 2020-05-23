package com.coc.cocmanager.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.model.AvailableModel;
import com.coc.cocmanager.model.StockInDetailModel;

import java.util.HashMap;
import java.util.Map;

/**
 * created by ketan 24-3-2020
 */
public class InventorySummaryFragment extends Fragment {

    //region variables
    private TextView tvAvailableCount;
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
        initializeData();
        return rootView;
    }

    private void initializeData() {
        getAvailableCount();
    }

    private void getAvailableCount() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServicesGet(
                        getContext(), ApiUtils.AVAILABLE_STOCK,
                        params, headerParams,
                        (response, error, status) -> handleAPIResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) {
        }
    }

    private void handleAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
               AvailableModel stockData = (AvailableModel) Utils.parseResponse(response, AvailableModel.class);
                if (stockData.getFound()) {
                    //TODO AFTER SUCCESS
                    tvAvailableCount.setText(stockData.getData());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
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
        tvAvailableCount = rootView.findViewById(R.id.tv_available_count);
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
