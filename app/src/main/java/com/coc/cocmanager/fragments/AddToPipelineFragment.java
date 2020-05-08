package com.coc.cocmanager.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.model.ClinicListModel;
import com.coc.cocmanager.model.LocationModel;
import com.coc.cocmanager.model.LoginData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by ketan 23-3-2020
 */
public class AddToPipelineFragment extends Fragment {

    @BindView(R.id.edt_installation_type)
    MaterialTextView edtInstallationType;
    @BindView(R.id.spn_location)
    Spinner spnLocation;
    @BindView(R.id.spn_clinic_name)
    Spinner spnClinicName;
    @BindView(R.id.edt_gmail_id)
    TextInputEditText edtGmailId;
    @BindView(R.id.edt_password)
    TextInputEditText edtPassword;
    @BindView(R.id.edt_actofit_id)
    TextInputEditText edtActofitId;
    @BindView(R.id.edt_actofit_password)
    TextInputEditText edtActofitPassword;
    @BindView(R.id.spn_client_name)
    Spinner spnClientName;
    @BindView(R.id.btn_save)
    Button btnSave;
    //region variables
    private int mDay;
    private int mYear;
    private int mMonth;

    private LocationModel locationModel;
    private ClinicListModel clinicListData;

    private TextView tvActofitExpiry;

    private String location_id;
    private ArrayList<String> clinicList;
    private ArrayList<String> locationList;
    //endregion

    //region methods
    public AddToPipelineFragment() {
        // Required empty public constructor
    }

    public static AddToPipelineFragment newInstance() {
        AddToPipelineFragment fragment = new AddToPipelineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_new, container, false);
        ButterKnife.bind(this, rootView);
        setupUI(rootView);
        setupEvents();
        initializeData();
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initializeData() {
        getLocationList();
//        addNewKioskTOPipeline();
    }

    private void getLocationList() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();

                Map<String, String> headerParams;
                headerParams = new HashMap<>();

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
                    setLocationListToSpinner(locationModel.getData());
                    Toast.makeText(getContext(), "Successfully loaded list", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getClinicList(String locationId) {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.STATUS, "true");
                params.put(Constants.Fields.LOCATION_ID, locationId);

                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.CLINIC_LIST,
                        params, headerParams,
                        (response, error, status) -> handleClinicAPIResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) {
        }
    }

    private void handleClinicAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                clinicListData = (ClinicListModel) Utils.parseResponse(response, ClinicListModel.class);
                if (clinicListData.getFound() && clinicListData.getData() != null) {
                    //TODO AFTER SUCCESS
                    setClinicListToSpinner(clinicListData.getData());
                    Toast.makeText(getContext(), "Successfully loaded list", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setLocationListToSpinner(List<LocationModel.LocationInfo> data) {
        locationList = new ArrayList<>();
        locationList.add("Select Location");

        for(int i = 0 ; i< data.size() ; i++) {
            locationList.add(data.get(i).getName());
        }

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(getContext().getApplicationContext(),
                R.layout.simple_item_selected, locationList);
        dataAdapter.setDropDownViewResource(R.layout.simple_item);
        spnLocation.setAdapter(dataAdapter);

        spnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    int pos = (int) spnLocation.getSelectedItemId();
                    location_id = data.get(pos).getId();
                    getClinicList(location_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setClinicListToSpinner(List<ClinicListModel.ClinicListInfo> data) {
        clinicList = new ArrayList<>();
        clinicList.add("Select Clinic");

        for (int i = 0; i < data.size(); i++) {
            clinicList.add(data.get(i).getName());
        }

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(getContext().getApplicationContext(),
                R.layout.simple_item_selected, clinicList);
        dataAdapter.setDropDownViewResource(R.layout.simple_item);
        spnClinicName.setAdapter(dataAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addNewKioskTOPipeline() {
        try {
            if (Utils.isOnline(getContext())) {

                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.USERNAME, "");
                params.put(Constants.Fields.PASSWORD, "");
                params.put(Constants.Fields.USERTYPE, "");

                Map<String, String> headerParams;
                headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.LOGIN_URL,
                        params, headerParams,
                        (response, error, status) -> handleAPIResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) { }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                LoginData patientData = (LoginData) Utils.parseResponse(response, LoginData.class);
                if (patientData.getFound()) {
                    //TODO AFTER SUCCESS
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setupEvents() {
        tvActofitExpiry.setOnClickListener(v -> {
            openCalender();
        });
    }

    private void openCalender() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        tvActofitExpiry.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void setupUI(View rootView) {
        tvActofitExpiry = rootView.findViewById(R.id.tv_actofit_expiry);
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

    //endregion
}
