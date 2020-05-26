package com.coc.cocmanager.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.content.Context;

import android.view.View;

import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.adapter.CheckAvailabilityListAdapter;
import com.coc.cocmanager.adapter.ItemListAdapter;
import com.coc.cocmanager.interfaces.ListClickListener;
import com.coc.cocmanager.model.CheckAvailabilityModel;
import com.coc.cocmanager.model.ItemsCategory_Response;
import com.google.android.material.textfield.TextInputEditText;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by ketan 27-3-2020
 */
public class CheckAvailabilityFragment extends Fragment implements ListClickListener {

    //region variables

    private TextInputEditText edtKioskNumbers;
    private Button btnSearch;
    private RecyclerView rvAvailableList;

    //endregion

    public CheckAvailabilityFragment() {
        // Required empty public constructor
    }

    public static CheckAvailabilityFragment newInstance(String param1, String param2) {
        CheckAvailabilityFragment fragment = new CheckAvailabilityFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_check_availability, container, false);

        setupUI(rootView);
        setupEvents();
        return rootView;
    }

    private void setupEvents() {
        btnSearch.setOnClickListener(v -> {checkAvailabilityofKioskItems();});
    }

    private void checkAvailabilityofKioskItems() {
        try{
        Map<String, String> headerParams = new HashMap<>();
        Map<String, String> requestBodyParams = new HashMap<>();
        requestBodyParams.put(Constants.Fields.KIOSK_COUNT, edtKioskNumbers.getText().toString());

        HttpService.accessWebServices(
                getContext(),
                ApiUtils.CHECK_AVAILABILITY,
                requestBodyParams,
                headerParams,
                (response, error, status) -> handleAPIResponse(response, error, status));
    } catch (Exception e) { }
    }

    private void handleAPIResponse(String response, VolleyError error, String status) {
        try {
            if (status.equals("response")) {
                CheckAvailabilityModel checkAvailableData = (CheckAvailabilityModel) Utils.parseResponse(response, CheckAvailabilityModel.class);
                if (checkAvailableData.getFound()) {
                    setAvailableListAdapter(checkAvailableData.getData());
                    Toast.makeText(getContext(), "list loaded sucessfully", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAvailableListAdapter(List<CheckAvailabilityModel.CheckAvailabilityInfo> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvAvailableList.setLayoutManager(linearLayoutManager);
        CheckAvailabilityListAdapter adapter = new CheckAvailabilityListAdapter(getContext(), list);
        rvAvailableList.setAdapter(adapter);
        adapter.setListClickListener(this);
    }

    private void setupUI(View rootView) {
        edtKioskNumbers = rootView.findViewById(R.id.edt_kiosk_no);
        btnSearch = rootView.findViewById(R.id.btn_search);
        rvAvailableList = rootView.findViewById(R.id.rv_availabel_list);
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

    @Override
    public void click(int position, int value) { }
}
