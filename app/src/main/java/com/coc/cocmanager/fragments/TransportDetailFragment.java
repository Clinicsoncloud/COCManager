package com.coc.cocmanager.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import androidx.fragment.app.Fragment;

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
public class TransportDetailFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.tv_installation_type)
    MaterialTextView tvInstallationType;
    @BindView(R.id.tv_clinic_name)
    MaterialTextView tvClinicName;
    @BindView(R.id.tv_clinic_id)
    MaterialTextView tvClinicId;
    @BindView(R.id.tv_gmail_id)
    MaterialTextView tvGmailId;
    @BindView(R.id.tv_gmail_password)
    MaterialTextView tvGmailPassword;
    @BindView(R.id.tv_actofit_id)
    MaterialTextView tvActofitId;
    @BindView(R.id.tv_actofit_password)
    MaterialTextView tvActofitPassword;
    @BindView(R.id.tv_actofit_expiry)
    MaterialTextView tvActofitExpiry;
    @BindView(R.id.spn_client_name)
    Spinner spnClientName;
    @BindView(R.id.edt_location)
    TextInputEditText edtLocation;
    @BindView(R.id.edt_address)
    TextInputEditText edtAddress;
    @BindView(R.id.btn_consumables)
    Button btnConsumables;
    ImageView ivMinusLancets;
    MaterialTextView tvQtyLancets;
    ImageView ivPlusLancets;
    ImageView ivMinusHbStrips;
    MaterialTextView tvQtyHbStrips;
    ImageView ivPlusHbStrips;
    ImageView ivMinusSugarStrips;
    MaterialTextView tvQtySugarStrips;
    ImageView ivPlusSugarStrips;
    ImageView ivMinusScrewDrivers;
    MaterialTextView tvQtyScrewDrivers;
    ImageView ivPlusScrewDrivers;
    ImageView ivMinusCells;
    MaterialTextView tvQtyCells;
    ImageView ivPlusCells;
    @BindView(R.id.expandable_consumables)
    ExpandableRelativeLayout expandableConsumables;
    @BindView(R.id.btn_move_to_pipeline)
    Button btnMoveToPipeline;

    // region variables

    private int count = 0;
    private String assign_user_id;
    private int mYear, mMonth, mDay;
    private String selected_position;
    private ArrayList<String> clientNamelist;
    //endregion

    public TransportDetailFragment() {
        // Required empty public constructor
    }

    public static TransportDetailFragment newInstance(String param1, String param2) {
        TransportDetailFragment fragment = new TransportDetailFragment();
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
        View rootView = inflater.inflate(R.layout.layout_transport_detail, container, false);
        ButterKnife.bind(this,rootView);

        setupUI(rootView);
        setupEvents();
        initializeData();
        return rootView;
    }

    private void setupUI(View rootView) {

        tvQtyCells = rootView.findViewById(R.id.tv_qty_cells);
        tvQtyLancets = rootView.findViewById(R.id.tv_qty_lancets);
        tvQtyHbStrips = rootView.findViewById(R.id.tv_qty_hb_strips);
        tvQtySugarStrips = rootView.findViewById(R.id.tv_qty_sugar_strips);
        tvQtyScrewDrivers = rootView.findViewById(R.id.tv_qty_screw_drivers);

        ivMinusCells = rootView.findViewById(R.id.iv_minus_cell);
        ivMinusLancets = rootView.findViewById(R.id.iv_minus_lancet);
        ivMinusHbStrips = rootView.findViewById(R.id.iv_minus_hb_strip);
        ivMinusSugarStrips = rootView.findViewById(R.id.iv_minus_sugar_strip);
        ivMinusScrewDrivers = rootView.findViewById(R.id.iv_minus_screw_driver);

        ivPlusCells = rootView.findViewById(R.id.iv_plus_cell);
        ivPlusLancets = rootView.findViewById(R.id.iv_plus_lancet);
        ivPlusHbStrips = rootView.findViewById(R.id.iv_plus_hb_strip);
        ivPlusSugarStrips = rootView.findViewById(R.id.iv_plus_sugar_strip);
        ivPlusScrewDrivers = rootView.findViewById(R.id.iv_plus_screw_driver);
    }

    private void initializeData() {
        getClientNameList();
        getSelectedPosition();
        getTransportDetails();
    }

    private void getClientNameList() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put("status", "true");
                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.CLIENT_LIST,
                        params, headerParams,
                        (response, error, status) -> handleClientAPIResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) {
        }
    }

    private void handleClientAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                UserData userData = (UserData) Utils.parseResponse(response, UserData.class);
                if (userData.getFound()) {
                    //TODO AFTER SUCCESS
                    setClientNameList(userData.getData());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setClientNameList(List<UserData.User_Info> data) {
        int i;
        clientNamelist = new ArrayList<>();
        clientNamelist.add("Select Client");

        for (i = 0; i < data.size(); i++) {
            clientNamelist.add(data.get(i).getFirst_name()+ " " +data.get(i).getLast_name());
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

    private void setupEvents() {

        tvActofitExpiry.setOnClickListener(View -> {
            openCalender();
        });

        btnConsumables.setOnClickListener(v -> {
            expandView();
        });

        ivMinusCells.setOnClickListener(v -> {removeCount(tvQtyCells);});
        ivMinusLancets.setOnClickListener(v -> {removeCount(tvQtyLancets);});
        ivMinusHbStrips.setOnClickListener(v -> {removeCount(tvQtyHbStrips);});
        ivMinusSugarStrips.setOnClickListener(v -> {removeCount(tvQtySugarStrips);});
        ivMinusScrewDrivers.setOnClickListener(v -> {removeCount(tvQtyScrewDrivers);});

        ivPlusCells.setOnClickListener(v -> {addCount(tvQtyCells);});
        ivPlusLancets.setOnClickListener(v -> {addCount(tvQtyLancets);});
        ivPlusHbStrips.setOnClickListener(v -> {addCount(tvQtyHbStrips);});
        ivPlusSugarStrips.setOnClickListener(v -> {addCount(tvQtySugarStrips);});
        ivPlusScrewDrivers.setOnClickListener(v -> {addCount(tvQtyScrewDrivers);});
    }

    private void expandView() {
        expandableConsumables.toggle();
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
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        tvActofitExpiry.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void getTransportDetails() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.INSTALLATION_STEP, "Transport");
                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.CLINIC_LIST,
                        params, headerParams,
                        (response, error, status) -> handleAPIResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) { }
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

    private void getSelectedPosition() {
        selected_position = getArguments().getString("position");
    }

    private void setTransportDetails(ClinicListModel.ClinicListInfo clinicListInfo) {
        tvClinicId.setText(clinicListInfo.getId());
        tvClinicName.setText(clinicListInfo.getName());
        tvGmailId.setText(clinicListInfo.getGmailid());
        edtAddress.setText(clinicListInfo.getAddress());
        tvActofitId.setText(clinicListInfo.getActofit_id());
        edtLocation.setText(clinicListInfo.getLocation().getName());
        tvGmailPassword.setText(clinicListInfo.getGmail_password());
        tvActofitExpiry.setText(clinicListInfo.getActofit_end_date());
        tvActofitPassword.setText(clinicListInfo.getActofit_password());
    }

    private void handleAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                ClinicListModel clinicData = (ClinicListModel) Utils.parseResponse(response, ClinicListModel.class);
                if (clinicData.getFound()) {
                    //TODO AFTER SUCCESS
                    setTransportDetails(clinicData.getData().get(Integer.parseInt(selected_position)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_minus_lancets:
                removeCount(tvQtyLancets);
                break;

            case R.id.iv_minus_hb_strips:
                removeCount(tvQtyHbStrips);
                break;

            case R.id.iv_minus_sugar_strips:
                removeCount(tvQtySugarStrips);
                break;

            case R.id.iv_minus_screw_drivers:
                removeCount(tvQtyScrewDrivers);
                break;

            case R.id.iv_minus_cells:
                removeCount(tvQtyCells);
                break;

            case R.id.iv_plus_lancets:
                addCount(tvQtyLancets);
                break;

            case R.id.iv_plus_hb_strips:
                addCount(tvQtyHbStrips);
                break;

            case R.id.iv_plus_sugar_strips:
                addCount(tvQtySugarStrips);
                break;

            case R.id.iv_plus_screw_drivers:
                addCount(tvQtyScrewDrivers);
                break;

            case R.id.iv_plus_cells:
                addCount(tvQtyCells);
                break;
        }
    }

    private void addCount(MaterialTextView tvAddCount) {
        if (count != 0) {
            count = count + 1;
            tvAddCount.setText("" + count);
            Log.e("countplus_log"," = "+count);
        }
    }

    private void removeCount(MaterialTextView tvCount) {
        if (count > 0) {
            count = count - 1;
            tvCount.setText("" + count);
            Log.e("countminus_log"," = "+count);
        }
    }
}
