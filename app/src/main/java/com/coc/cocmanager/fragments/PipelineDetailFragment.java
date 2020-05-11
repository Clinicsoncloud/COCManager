package com.coc.cocmanager.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
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
    @BindView(R.id.spn_client_name)
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

    private String selected_position;
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
                Toast.makeText(getContext(), "Can't minus right now", Toast.LENGTH_SHORT).show();
            }
        });
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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setPipelineDetailData(ClinicListModel.ClinicListInfo clinicListInfo) {
        tvClinicId.setText(clinicListInfo.getId());
        tvClinicName.setText(clinicListInfo.getName());
        edtAddress.setText(clinicListInfo.getAddress());
        edtGmailId.setText(clinicListInfo.getGmailid());
        edtActofitId.setText(clinicListInfo.getActofit_id());
        edtGmailPassword.setText(clinicListInfo.getGmail_password());
        tvActofitExpiry.setText(clinicListInfo.getActofit_end_date());
        edtActofitPassword.setText(clinicListInfo.getActofit_password());
        edtLocation.setText(clinicListInfo.getLocation().getData().get(Integer.parseInt(selected_position)).getName());
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
