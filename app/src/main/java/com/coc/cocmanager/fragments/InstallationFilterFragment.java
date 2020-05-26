package com.coc.cocmanager.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.model.LocationModel;
import com.coc.cocmanager.model.UserData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstallationFilterFragment extends Fragment {

    //region variable
    @BindView(R.id.tv_start_date)
    TextView tvStartDate;
    @BindView(R.id.tv_end_date)
    TextView tvEndDate;
    @BindView(R.id.auto_tv_location)
    AutoCompleteTextView autoTvLocation;
    @BindView(R.id.auto_tv_clinet_name)
    AutoCompleteTextView autoTvClinetName;
    @BindView(R.id.btn_filter)
    Button btnFilter;

    private LocationModel locationModel;
    private ArrayList<String> clientList;
    private ArrayList<String> locationList;

    private int mYear, mMonth, mDay;
    final Calendar c = Calendar.getInstance();
    //endregion

    //region methods

    public InstallationFilterFragment() {
        // Required empty public constructor
    }

    public static InstallationFilterFragment newInstance(String param1, String param2) {
        InstallationFilterFragment fragment = new InstallationFilterFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_installation_filter, container, false);
        ButterKnife.bind(this, rootView);

        setupEvents();
        initializeData();
        return rootView;
    }

    private void setupEvents() {
        tvEndDate.setOnClickListener(v -> {
            openCalender(tvEndDate);
        });

        tvStartDate.setOnClickListener(v -> {
            openCalender(tvStartDate);
        });

        btnFilter.setOnClickListener(v -> {
            openInstallationScreen();
        });
    }

    private void openInstallationScreen() {
        Fragment fragment = new InstallationHomeFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }

    /**
     * created the openCalender function to open a calender on textview click
     * sets the date to the textview
     *
     * @param textView
     */
    private void openCalender(TextView textView) {
        // Get Current Date
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        textView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void initializeData() {
        getClientList();
        getLocationList();
    }


    private void getClientList() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.STATUS, Constants.Fields.TRUE);

                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.CLIENT_LIST,
                        params, headerParams,
                        (response, error, status) -> handleClientNameResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) {
        }
    }

    private void handleClientNameResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                UserData userData = (UserData) Utils.parseResponse(response, UserData.class);
                if (userData.getFound()) {
                    //TODO AFTER SUCCESS
                    setClientList(userData.getData());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setClientList(List<UserData.User_Info> data) {
        clientList = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            clientList.add(data.get(i).getFirst_name() + " " + data.get(i).getLast_name());
        }
        String[] list = clientList.toArray(new String[clientList.size()]);
        ArrayAdapter<?> adapter = new ArrayAdapter<Object>(getContext(), android.R.layout.simple_dropdown_item_1line, list);
        autoTvClinetName.setAdapter(adapter);
        if (clientList.size() < 40)
            autoTvClinetName.setThreshold(1);
        else
            autoTvClinetName.setThreshold(2);
    }

    private void getLocationList() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServicesGet(
                        getContext(), ApiUtils.LOCATION_LIST,
                        params, headerParams,
                        (response, error, status) -> handleLocationResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) {
        }
    }

    private void handleLocationResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                locationModel = (LocationModel) Utils.parseResponse(response, LocationModel.class);
                if (locationModel.getFound() && locationModel.getData() != null) {
                    //TODO AFTER SUCCESS
                    setLocationList(locationModel.getData());
                    Toast.makeText(getContext(), "Successfully loaded list", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setLocationList(List<LocationModel.LocationInfo> data) {
        locationList = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            locationList.add(data.get(i).getName());
        }

        String[] list = locationList.toArray(new String[locationList.size()]);  // terms is a List<String>
        ArrayAdapter<?> adapter = new ArrayAdapter<Object>(getContext(), android.R.layout.simple_dropdown_item_1line, list);
        autoTvLocation.setAdapter(adapter);  // keywordField is a AutoCompleteTextView
        if (locationList.size() < 40)
            autoTvLocation.setThreshold(1);
        else
            autoTvLocation.setThreshold(2);
    }

    //endregion
}
