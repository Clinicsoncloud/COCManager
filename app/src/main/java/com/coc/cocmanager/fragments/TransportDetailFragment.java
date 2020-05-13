package com.coc.cocmanager.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.Calendar;
import java.util.HashMap;
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
    Spinner tvClientName;
    @BindView(R.id.edt_location)
    TextInputEditText edtLocation;
    @BindView(R.id.edt_address)
    TextInputEditText edtAddress;
    @BindView(R.id.btn_consumables)
    Button btnConsumables;
    @BindView(R.id.iv_minus_lancets)
    ImageView ivMinusLancets;
    @BindView(R.id.tv_count_lancets)
    MaterialTextView tvCountLancets;
    @BindView(R.id.iv_plus_lancets)
    ImageView ivPlusLancets;
    @BindView(R.id.ll_plus_minus)
    LinearLayout llPlusMinus;
    @BindView(R.id.iv_minus_hb_strips)
    ImageView ivMinusHbStrips;
    @BindView(R.id.tv_qty_hb_strips)
    MaterialTextView tvQtyHbStrips;
    @BindView(R.id.iv_plus_hb_strips)
    ImageView ivPlusHbStrips;
    @BindView(R.id.iv_minus_sugar_strips)
    ImageView ivMinusSugarStrips;
    @BindView(R.id.tv_qty_sugar_strips)
    MaterialTextView tvQtySugarStrips;
    @BindView(R.id.iv_plus_sugar_strips)
    ImageView ivPlusSugarStrips;
    @BindView(R.id.iv_minus_screw_drivers)
    ImageView ivMinusScrewDrivers;
    @BindView(R.id.tv_qty_screw_drivers)
    MaterialTextView tvQtyScrewDrivers;
    @BindView(R.id.iv_plus_screw_drivers)
    ImageView ivPlusScrewDrivers;
    @BindView(R.id.iv_minus_cells)
    ImageView ivMinusCells;
    @BindView(R.id.tv_qty_cells)
    MaterialTextView tvQtyCells;
    @BindView(R.id.iv_plus_cells)
    ImageView ivPlusCells;
    @BindView(R.id.expandable_consumables)
    ExpandableRelativeLayout expandableConsumables;
    @BindView(R.id.btn_move_to_pipeline)
    Button btnMoveToPipeline;

    // region variables

    private int count = 0;
    private int mYear, mMonth, mDay;
    private String selected_position;
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
        ButterKnife.bind(this, rootView);

        setupEvents();
        initializeData();
        return rootView;
    }

    private void initializeData() {
        getSelectedPosition();
        getTransportDetails();
    }

    private void setupEvents() {

        tvActofitExpiry.setOnClickListener(View -> {
            openCalender();
        });

        btnConsumables.setOnClickListener(v -> {
            expandView();
        });

        ivMinusCells.setOnClickListener(this);
        ivMinusLancets.setOnClickListener(this);
        ivMinusHbStrips.setOnClickListener(this);
        ivMinusSugarStrips.setOnClickListener(this);
        ivMinusScrewDrivers.setOnClickListener(this);

        ivPlusCells.setOnClickListener(this);
        ivPlusLancets.setOnClickListener(this);
        ivPlusHbStrips.setOnClickListener(this);
        ivPlusSugarStrips.setOnClickListener(this);
        ivPlusScrewDrivers.setOnClickListener(this);
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
        } catch (Exception e) {
        }
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
                removeCount(tvCountLancets);
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
                addCount(tvCountLancets);
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
