package com.coc.cocmanager.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.model.InstallationSummaryModel;
import com.coc.cocmanager.model.StockInListModel;
import com.google.android.material.textview.MaterialTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by ketan 23-3-2020
 */
public class InstallationSummaryFragment extends Fragment {

    //region variables
    @BindView(R.id.tv_installation_count)
    MaterialTextView tvInstallationCount;
    @BindView(R.id.tv_transport_count)
    MaterialTextView tvTransportCount;
    @BindView(R.id.tv_pipeline_count)
    MaterialTextView tvPipelineCount;
    private InstallationSummaryModel summaryData;

    //endregion

    public InstallationSummaryFragment() {
        // Required empty public constructor
    }

    public static InstallationSummaryFragment newInstance() {
        InstallationSummaryFragment fragment = new InstallationSummaryFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_installation_summary, container, false);
        ButterKnife.bind(this,rootView);

        initializeData();
        return rootView;
    }

    /**
     * initialize the method getInstallationSummaryData()
     */
    private void initializeData() {
        getInstallationSummaryData();
    }

    /**
     * get InstallationSummary Data
     * calling the post api SUMMARY count
     */
    private void getInstallationSummaryData() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.SUMMARY,
                        params, headerParams,
                        (response, error, status) -> handleAPIResponse(response, error, status));
            } else {
                Toast.makeText(getContext(), "No Internet connectivity..!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) { }
    }

    /**
     * handle the response of the api
     * check for response in status
     * parse the response to the InstallationSummaryModel class
     * set summary data
     * @param response
     * @param error
     * @param status
     */
    private void handleAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                summaryData = (InstallationSummaryModel) Utils.parseResponse(response, InstallationSummaryModel.class);
                if (summaryData.getFound()) {
                    //TODO AFTER SUCCESS
                    setSummaryData(summaryData.getData());
                    Toast.makeText(getContext(), "summary data loaded Successfully", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * set the count data of installation transport pipeline
     * @param data
     */
    private void setSummaryData(InstallationSummaryModel.SummaryInfo data) {
        tvPipelineCount.setText(data.getPipelineCount());
        tvTransportCount.setText(data.getTransportCount());
        tvInstallationCount.setText(data.getInstalledCount());
    }

}
