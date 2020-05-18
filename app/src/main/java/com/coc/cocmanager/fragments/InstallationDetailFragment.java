package com.coc.cocmanager.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.textview.MaterialTextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by 23-3-2020
 */

public class InstallationDetailFragment extends Fragment {

    //region variables
    @BindView(R.id.tv_client_name)
    MaterialTextView tvClientName;
    @BindView(R.id.tv_clinic_id)
    MaterialTextView tvClinicId;
    @BindView(R.id.tv_installation_date)
    MaterialTextView tvInstallationDate;
    @BindView(R.id.tv_clinic_name)
    MaterialTextView tvClinicName;
    @BindView(R.id.tv_location)
    MaterialTextView tvLocation;
    @BindView(R.id.tv_address)
    MaterialTextView tvAddress;
    @BindView(R.id.tv_status)
    MaterialTextView tvStatus;
    @BindView(R.id.tv_app_version)
    MaterialTextView tvAppVersion;
    @BindView(R.id.tv_last_sync)
    MaterialTextView tvLastSync;
    @BindView(R.id.tv_licence_expiry)
    MaterialTextView tvLicenceExpiry;
    @BindView(R.id.tv_actofit_expiry)
    MaterialTextView tvActofitExpiry;
    @BindView(R.id.tv_gmail_id)
    MaterialTextView tvGmailId;
    @BindView(R.id.tv_gmail_password)
    MaterialTextView tvGmailPassword;
    @BindView(R.id.tv_actofit_id)
    MaterialTextView tvActofitId;
    @BindView(R.id.tv_actofit_password)
    MaterialTextView tvActofitPassword;
    @BindView(R.id.tv_installed_by)
    MaterialTextView tvInstalledBy;
    @BindView(R.id.tv_operator_name)
    MaterialTextView tvOperatorName;
    @BindView(R.id.tv_operator_number)
    MaterialTextView tvOperatorNumber;

    private String selected_position;
    private ClinicListModel clinicData;

    //endregion variables

    // region methods
    public InstallationDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InstallationDetailFragment newInstance(String param1, String param2) {
        InstallationDetailFragment fragment = new InstallationDetailFragment();
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
        View rootView = inflater.inflate(R.layout.layout_installation_details, container, false);
        ButterKnife.bind(this,rootView);

        initializeData();
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initializeData() {
        getPosition();
        getInstallationDetails();
    }

    private void getPosition() {
        selected_position = getArguments().getString(Constants.Fields.POSITION);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getInstallationDetails() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.STATUS,Constants.Fields.TRUE);
                params.put(Constants.Fields.INSTALLATION_STEP, Constants.Fields.TYPE_INSTALLED);

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
                clinicData = (ClinicListModel) Utils.parseResponse(response, ClinicListModel.class);
                if (clinicData.getFound()) {
                    //TODO AFTER SUCCESS
                    setInstalledDetailData(clinicData.getData().get(Integer.parseInt(selected_position)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setInstalledDetailData(ClinicListModel.ClinicListInfo data) {
        if (data.getStatus())
            tvStatus.setText("Active");
        else
            tvStatus.setText("In Active");

        tvClinicId.setText(data.getId());
        tvClinicName.setText(data.getName());
        tvGmailId.setText(data.getGmailid());
        tvAddress.setText(data.getAddress());
        tvActofitId.setText(data.getActofit_id());
        tvAppVersion.setText(data.getApp_version());
        tvLastSync.setText(data.getLast_sync_date());
        tvInstalledBy.setText(data.getInstalled_by());
        tvLocation.setText(data.getLocation().getName());
        tvGmailPassword.setText(data.getGmail_password());
        tvActofitExpiry.setText(data.getActofit_end_date());
        tvActofitPassword.setText(data.getActofit_password());
        tvLicenceExpiry.setText(data.getLicense_expiry_date());
        tvOperatorName.setText(data.getMachine_operator_name());
        tvInstallationDate.setText(data.getInstallation_date());
        tvOperatorNumber.setText(data.getMachine_operator_mobile_number());
        tvClientName.setText(data.getUser().getFirstname() + data.getUser().getLastname());
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
