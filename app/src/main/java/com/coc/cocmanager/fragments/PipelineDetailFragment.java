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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.coc.cocmanager.model.ClinicListModel;
import com.coc.cocmanager.model.UserData;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
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
public class PipelineDetailFragment extends Fragment {

    @BindView(R.id.tv_installation_type)
    MaterialTextView tvInstallationType;
    @BindView(R.id.tv_clinic_name)
    MaterialTextView tvClinicName;
    @BindView(R.id.tv_clinic_id)
    MaterialTextView tvClinicId;
    @BindView(R.id.edt_gmail_id)
    TextInputEditText edtGmailId;
    @BindView(R.id.edt_gmail_password)
    TextInputEditText edtGmailPassword;
    @BindView(R.id.edt_actofit_id)
    TextInputEditText edtActofitId;
    @BindView(R.id.edt_actofit_password)
    TextInputEditText edtActofitPassword;
    @BindView(R.id.tv_actofit_expiry)
    MaterialTextView tvActofitExpiry;
    @BindView(R.id.tv_client_name)
    Spinner spnClientName;
    @BindView(R.id.edt_location)
    TextInputEditText edtLocation;
    @BindView(R.id.edt_address)
    TextInputEditText edtAddress;
    @BindView(R.id.btn_consumables)
    Button btnConsumables;
    @BindView(R.id.iv_minus)
    ImageView ivMinus;
    @BindView(R.id.tv_count)
    MaterialTextView tvCount;
    @BindView(R.id.iv_plus)
    ImageView ivPlus;
    @BindView(R.id.ll_plus_minus)
    LinearLayout llPlusMinus;
    @BindView(R.id.expandable_consumables)
    ExpandableRelativeLayout expandableConsumables;
    @BindView(R.id.btn_save)
    Button btnSave;
    //region variables
    private int mYear, mMonth, mDay;

    private int count = 0;
    private TextView tvQty;

    private String clinic_id;
    private String selected_position;
    private ArrayList<String> clientNamelist;
    private String assign_user_id;
    //endregion

    public static PipelineDetailFragment newInstance(String param1, String param2) {
        PipelineDetailFragment fragment = new PipelineDetailFragment();
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
        View rootView = inflater.inflate(R.layout.layout_pipeline_detail, container, false);
        ButterKnife.bind(this, rootView);

        setupUI(rootView);
        setupEvents();
        initializeData();
        return rootView;
    }


    private void setupEvents() {
        tvActofitExpiry.setOnClickListener(v -> {
            openCalender();
        });

        btnConsumables.setOnClickListener(v -> {
            expandableConsumables.toggle();
        });

        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count >= 0) {
                    count = count + 1;
                    tvQty.setText("" + count);
                }
            }
        });

        ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 0) {
                    count = count - 1;
                    tvQty.setText("" + count);
                } else
                    Toast.makeText(getContext(), "Can't minus now", Toast.LENGTH_SHORT).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid())
                    moveToTransport();
            }
        });
    }

    private boolean valid() {
        if (edtLocation.getText().length() == 0) {
            Toast.makeText(getContext(), "Please Enter Location", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tvClinicName.getText().length() == 0) {
            Toast.makeText(getContext(), "Please Enter Clinic", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtGmailId.getText().length() == 0) {
            edtGmailId.setError("Please Enter Gmail id");
            return false;
        } else if (edtGmailPassword.getText().length() == 0) {
            edtGmailPassword.setError("Please Enter Password");
            return false;
        } else if (edtActofitId.getText().length() == 0) {
            edtActofitId.setError("Please Enter Actofit ID");
            return false;
        } else if (edtActofitPassword.getText().length() == 0) {
            edtActofitPassword.setError("Please Enter Actofit Password");
            return false;
        } else if (spnClientName.getSelectedItemId() == -1) {
            Toast.makeText(getContext(), "Please select the client Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtAddress.getText().length() == 0) {
            edtAddress.setError("Please Enter Address");
            return false;
        }
        return true;
    }

    private void moveToTransport() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                clinic_id = tvClinicId.getText().toString();
                params.put(Constants.Fields.INSTALLATION_STEP, "Transport");
                params.put(Constants.Fields.ASSIGN_USER_ID, assign_user_id);
                params.put(Constants.Fields.GMAIL_ID, edtGmailId.getText().toString());
                params.put(Constants.Fields.ACTOFIT_ID, edtActofitId.getText().toString());
                params.put(Constants.Fields.GMAIL_PASSWORD, edtGmailPassword.getText().toString());
                params.put(Constants.Fields.ACTOFIT_PASSWORD, edtActofitPassword.getText().toString());
                params.put(Constants.Fields.ACTOFIT_END_DATE, Utils.get_yyyy_mm_dd_HMS(tvActofitExpiry.getText().toString()));
                Map<String, String> headerParams = new HashMap<>();

                String url = ApiUtils.ADD_TO_PIPELINE + clinic_id;

                HttpService.accessWebServicess(
                        getContext(),
                        url,
                        Request.Method.PUT,
                        params,
                        headerParams,
                        (response, error, status) -> handleMoveToTransportResponse(response, error, status));

            } else {
                Toast.makeText(getContext(), "No Internet connection, Please Try again", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
        }
    }

    private void handleMoveToTransportResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                ClinicListModel clinicData = (ClinicListModel) Utils.parseResponse(response, ClinicListModel.class);
                if (clinicData.getFound()) {
                    //TODO AFTER SUCCESS
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initializeData() {
        getPosition();
        showPipelineDetails();
    }

    private void getPosition() {
        selected_position = getArguments().getString("position");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showPipelineDetails() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.INSTALLATION_STEP, "Pipeline");

                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.CLINIC_LIST,
                        params, headerParams,
                        (response, error, status) -> handleAPIResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                ClinicListModel clinicData = (ClinicListModel) Utils.parseResponse(response, ClinicListModel.class);
                if (clinicData.getFound()) {
                    //TODO AFTER SUCCESS
                    setPipelineDetailData(clinicData.getData().get(Integer.parseInt(selected_position)));
                    getClientList();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getClientList() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.USERTYPE, "Customer");

                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.USER_LIST,
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
        int i;
        clientNamelist = new ArrayList<>();
        clientNamelist.add("Select Client");

        for (i = 0; i < data.size(); i++) {
            clientNamelist.add(data.get(i).getFirst_name());
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

    private void setPipelineDetailData(ClinicListModel.ClinicListInfo clinicListInfo) {
        tvClinicId.setText(clinicListInfo.getId());
        tvClinicName.setText(clinicListInfo.getName());
        edtAddress.setText(clinicListInfo.getAddress());
        edtGmailId.setText(clinicListInfo.getGmailid());
        edtActofitId.setText(clinicListInfo.getActofit_id());
        edtLocation.setText(clinicListInfo.getLocation().getName());
        edtGmailPassword.setText(clinicListInfo.getGmail_password());
        tvActofitExpiry.setText(clinicListInfo.getActofit_end_date());
        edtActofitPassword.setText(clinicListInfo.getActofit_password());
    }

    private void openCalender() {
        // Get Current Date
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

    private void setupUI(View rootView) {
        ivPlus = rootView.findViewById(R.id.iv_plus);
        tvQty = rootView.findViewById(R.id.tv_count);
        ivMinus = rootView.findViewById(R.id.iv_minus);
        btnConsumables = rootView.findViewById(R.id.btn_consumables);
        tvActofitExpiry = rootView.findViewById(R.id.tv_actofit_expiry);
        expandableConsumables = rootView.findViewById(R.id.expandable_consumables);
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
