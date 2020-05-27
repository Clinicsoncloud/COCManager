package com.coc.cocmanager.fragments;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.model.AddPipelineModel;
import com.coc.cocmanager.model.ClinicListModel;
import com.coc.cocmanager.model.LocationModel;
import com.coc.cocmanager.model.UserData;
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

    //region variables
    @BindView(R.id.tv_installation_type)
    MaterialTextView edtInstallationType;
    @BindView(R.id.spn_location)
    Spinner spnLocation;
    @BindView(R.id.spn_clinic_name)
    Spinner spnClinicName;
    @BindView(R.id.edt_gmail_id)
    TextInputEditText edtGmailId;
    @BindView(R.id.edt_gmail_password)
    TextInputEditText edtGmailPassword;
    @BindView(R.id.edt_actofit_id)
    TextInputEditText edtActofitId;
    @BindView(R.id.edt_actofit_password)
    TextInputEditText edtActofitPassword;
    @BindView(R.id.spn_client_name)
    Spinner spnClientName;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.tv_actofit_expiry)
    MaterialTextView tvActofitExpiry;
    @BindView(R.id.tv_clinic_id)
    MaterialTextView tvClinicId;

    //integer variables
    private int mDay;
    private int mYear;
    private int mMonth;

    //model class objects
    private LocationModel locationModel;
    private ClinicListModel clinicListData;

    // string variables
    private String clinic_id;
    private String location_id;
    private String selectedItem;
    private String assign_user_id;
    private ArrayList<String> clinicList;
    private ArrayList<String> locationList;
    private ArrayList<String> clientNamelist;

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

        setupEvents();
        initializeData();
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupEvents() {
        tvActofitExpiry.setOnClickListener(v -> {
            openCalender();
        });
        btnSave.setOnClickListener(v -> {
            if (validate()) {
                addNewKioskTOPipeline();
            }
        });
    }

    /**
     * check for validataion of the each field of add to pipeline form
     * @return
     */
    private boolean validate() {
        if (spnLocation.getSelectedItemId() == -1) {
            Toast.makeText(getContext(), "Please Select Location", Toast.LENGTH_SHORT).show();
            return false;
        } else if (spnClinicName.getSelectedItemId() == -1) {
            Toast.makeText(getContext(), "Please Select Clinic", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtGmailId.getText().length() == 0) {
            edtGmailId.setError("Please Enter Gmail id");
            return false;
        } else if (edtGmailPassword.getText().length() == 0) {
            edtGmailPassword.setError("Please Enter Password");
            return false;
        }else if(edtActofitId.getText().length() == 0){
            edtActofitId.setError("Please Enter Actofit ID");
            return false;
        }else if(edtActofitPassword.getText().length() == 0){
            edtActofitPassword.setError("Please Enter Actofit Password");
            return false;
        }else if(spnClientName.getSelectedItemId() == -1){
            Toast.makeText(getContext(), "Please Select Client", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * initializeData
     * calls the getClinetList() for showing client list
     * calls the getLocationList() for showing locations
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initializeData() {
        getLocationList();
        getClientList();
    }

    /**
     * open calender
     * set selected date to the textview of Actofit End Date
     */
    private void openCalender() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvActofitExpiry.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    /**
     * API call to get the Clinet List
     * call handleClientNameResponse to handle the received response from server
     */
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

    /**
     * API call to get location list
     *call handleLocationResponse method to handle the response
     */
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

    /**
     * API call to Add To Pipeline
     * send params
     * call handlePipelineResponse to handle the received response
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addNewKioskTOPipeline() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.LOCATION_ID, location_id);
                params.put(Constants.Fields.ASSIGN_USER_ID, assign_user_id);
                params.put(Constants.Fields.GMAIL_ID, edtGmailId.getText().toString());
                params.put(Constants.Fields.ACTOFIT_ID, edtActofitId.getText().toString());
                params.put(Constants.Fields.GMAIL_PASSWORD, edtGmailPassword.getText().toString());
                params.put(Constants.Fields.ACTOFIT_PASSWORD, edtActofitPassword.getText().toString());
                params.put(Constants.Fields.INSTALLATION_STEP, edtInstallationType.getText().toString());
                params.put(Constants.Fields.ACTOFIT_END_DATE, Utils.get_yyyy_mm_dd_HMS(tvActofitExpiry.getText().toString()));

                Map<String, String> headerParams = new HashMap<>();

                String url = ApiUtils.ADD_TO_PIPELINE + clinic_id;

                Log.e("url_log"," = "+url);
                Log.e("parms_log"," = "+params);

                HttpService.accessWebServicess(
                        getContext(),
                        url,
                        Request.Method.PUT,
                        params,
                        headerParams,
                        (response, error, status) -> handlePipelineResponse(response, error, status));

            } else {
                Toast.makeText(getContext(), "No Internet connection, Please Try again", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
        }
    }

    /**
     *  go back to the pipeline listing screen
     */
    private void goToPipelineListScreen() {
        Fragment fragment = new PipelineFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }

    /**
     * send location id as param to the clinic list filter
     * call the handleClinicAPIResponse to handle the response
     * @param locationId
     */
    private void getClinicList(String locationId) {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.LOCATION_ID, locationId);
                params.put(Constants.Fields.STATUS, Constants.Fields.TRUE);

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

    private void setClientList(List<UserData.User_Info> data) {
        int i;
        clientNamelist = new ArrayList<>();
        clientNamelist.add("-Select-");

        for (i = 0; i < data.size(); i++) {
            clientNamelist.add(data.get(i).getFirst_name() + " " + data.get(i).getLast_name());
        }

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(getContext().getApplicationContext(),
                R.layout.simple_item_selected, clientNamelist);
        dataAdapter.setDropDownViewResource(R.layout.simple_item);
        spnClientName.setAdapter(dataAdapter);

        spnClientName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    assign_user_id = data.get(position - 1).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setLocationListToSpinner(List<LocationModel.LocationInfo> data) {
        int i;
        locationList = new ArrayList<>();
        locationList.add("-Select-");

        for (i = 0; i < data.size(); i++) {
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
                    selectedItem = spnLocation.getSelectedItem().toString();
                    location_id = data.get(position - 1).getId();
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

        spnClinicName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    selectedItem = spnClinicName.getSelectedItem().toString();
                    clinic_id = data.get(position -1).getId();
                    tvClinicId.setText(clinic_id);
                    edtGmailId.setText(data.get(position - 1).getGmailid());
                    edtActofitId.setText(data.get(position - 1).getActofit_id());
                    edtGmailPassword.setText(data.get(position - 1).getGmail_password());
                    tvActofitExpiry.setText(data.get(position - 1).getActofit_end_date());
                    edtActofitPassword.setText(data.get(position - 1).getActofit_password());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
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

    private void handlePipelineResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                AddPipelineModel pipelineData = (AddPipelineModel) Utils.parseResponse(response, AddPipelineModel.class);
                if (pipelineData.getSuccess()) {
                    //TODO AFTER SUCCESS
                    goToPipelineListScreen();
                    Toast.makeText(getContext(), "Successfully added to pipeline", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error in updating to pipeline", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
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


    //endregion
}
