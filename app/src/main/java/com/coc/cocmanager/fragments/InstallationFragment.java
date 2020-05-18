package com.coc.cocmanager.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.adapter.InstallationsListAdapter;
import com.coc.cocmanager.interfaces.ListClickListener;
import com.coc.cocmanager.model.ClinicListModel;
import com.coc.cocmanager.model.LocationModel;
import com.coc.cocmanager.model.UserData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by ketan 23-3-2020
 */
public class InstallationFragment extends Fragment implements ListClickListener, View.OnClickListener {

    //region variables
    private int mYear, mMonth, mDay;
    private RecyclerView rvInstallations;
    private Context context = getContext();

    private TextView tvEndDate;
    private TextView tvStartDate;
    private LocationModel locationModel;
    private AutoCompleteTextView autotvLocation;
    private AutoCompleteTextView autotvClientName;
    final Calendar c = Calendar.getInstance();
    private List<String> clientList;
    private List<String> locationList;
    //endregion

    //region methods

    public InstallationFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InstallationFragment newInstance() {
        InstallationFragment fragment = new InstallationFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_installation, container, false);

        setupUI(rootView);
        setupEvents();
        initalizeData();
        return rootView;
    }

    /**
     * set up the click events on views
     */
    private void setupEvents() {
        tvEndDate.setOnClickListener(this);
        tvStartDate.setOnClickListener(this);
    }

    /**
     * Initialization of methods
     */
    private void initalizeData() {
        getClientList();
        getLocationList();
        getInstalledClinicList();
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
        } catch (Exception e) { }
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
            clientList.add(data.get(i).getFirst_name() +" "+data.get(i).getLast_name());
        }
        String[] list = clientList.toArray(new String[clientList.size()]);  // terms is a List<String>
        ArrayAdapter<?> adapter = new ArrayAdapter<Object>(getContext(), android.R.layout.simple_dropdown_item_1line, list);
        autotvClientName.setAdapter(adapter);  // keywordField is a AutoCompleteTextView
        if(clientList.size() < 40)
            autotvClientName.setThreshold(1);
        else
            autotvClientName.setThreshold(2);
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
        autotvLocation.setAdapter(adapter);  // keywordField is a AutoCompleteTextView
        if(locationList.size() < 40)
            autotvLocation.setThreshold(1);
        else
            autotvLocation.setThreshold(2);
    }

    private void getInstalledClinicList() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String ,String> params = new HashMap<>();
                params.put(Constants.Fields.STATUS,Constants.Fields.TRUE);
                params.put(Constants.Fields.INSTALLATION_STEP,Constants.Fields.TYPE_INSTALLED);

                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.CLINIC_LIST,
                        params, headerParams,
                        (response, error, status) -> handleAPIResponse(response, error, status));
            } else {
                Utils.showToast(getContext(),"No Internet connectivity..!");
            }
        } catch (Exception e) { }
    }

    private void handleAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                ClinicListModel clinicData = (ClinicListModel) Utils.parseResponse(response,ClinicListModel.class);
                if(clinicData.getFound()){
                    //TODO AFTER SUCCESS
                    setInstallationListAdapter(clinicData.getData());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * created the openCalender function to open a calender on textview click
     * sets the date to the textview
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
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                        textView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void setupUI(View rootView) {
        tvEndDate = rootView.findViewById(R.id.tv_end_date);
        tvStartDate = rootView.findViewById(R.id.tv_start_date);
        autotvLocation = rootView.findViewById(R.id.auto_tv_location);
        rvInstallations = rootView.findViewById(R.id.rv_installations);
        autotvClientName = rootView.findViewById(R.id.auto_tv_clinet_name);
    }

    /**
     * This method is used to set the installation list
     * Adapter is created and set to the recyclerview variables
     * @param list
     */
    private void setInstallationListAdapter(List<ClinicListModel.ClinicListInfo> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rvInstallations.setLayoutManager(linearLayoutManager);
        InstallationsListAdapter adapter = new InstallationsListAdapter(context, list);
        rvInstallations.setAdapter(adapter);
        adapter.setListClickListener(this);
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

    @Override
    public void click(int position, int value) {
        Fragment fragment = new InstallationDetailFragment();
        Bundle args = new Bundle();
        args.putString(Constants.Fields.POSITION,""+position);
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start_date:
                openCalender(tvStartDate);
                break;

            case R.id.tv_end_date:
                openCalender(tvEndDate);
                break;
        }
    }
    //endregion
}
