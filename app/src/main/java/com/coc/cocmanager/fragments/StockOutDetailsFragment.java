package com.coc.cocmanager.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.coc.cocmanager.adapter.StockInDetailsListAdapter;
import com.coc.cocmanager.model.StockInDetailModel;
import com.coc.cocmanager.model.StockInListModel;
import com.coc.cocmanager.services.DateService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by ketan 20-5-2020
 */
public class StockOutDetailsFragment extends Fragment {

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_descreption)
    TextView tvDescreption;
    @BindView(R.id.tv_clinic_name)
    TextView tvClinicName;
    @BindView(R.id.rv_stock_out_descreption_list)
    RecyclerView rvStockOutDescreptionList;
    private String selected_id;
    private StockInDetailModel stockData;
    private StockInDetailsListAdapter adapter;

    public StockOutDetailsFragment() {
        // Required empty public constructor
    }

    public static StockOutDetailsFragment newInstance() {
        StockOutDetailsFragment fragment = new StockOutDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View rootView = inflater.inflate(R.layout.fragment_stock_out_details, container, false);
        ButterKnife.bind(this,rootView);

        initializeData();
        return rootView;
    }

    private void initializeData() {
        getSelectedId();
        getDetailsOfStockOut();
    }

    private void getDetailsOfStockOut() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                Map<String, String> headerParams = new HashMap<>();

                String url = ApiUtils.STOCK_IN_DETAILS + "/" + selected_id;

                Log.e("url_log", " = " + url);
                HttpService.accessWebServicesGet(
                        getContext(), url,
                        params, headerParams,
                        (response, error, status) -> handleAPIResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) {
        }
    }

    private void handleAPIResponse(String response, VolleyError error, String status) {
        Log.e("status_log", " = " + status);
        Log.e("response_log", " = " + response);
        if (status.equals("response")) {
            try {
                stockData = (StockInDetailModel) Utils.parseResponse(response, StockInDetailModel.class);
                if (stockData.getFound()) {
                    //TODO AFTER SUCCESS
                    setData(stockData.getData());
                    setDetailListAdapter(stockData.getData().getStockItems());
                    Toast.makeText(getContext(), "Stock List Details fetched Successfully", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(StockInDetailModel.StockInInfo data) {
        tvDescreption.setText(data.getDescription());
        tvClinicName.setText(data.getClinic().getName());
        tvDate.setText(DateService.formatDateFromString(data.getCreated_at()));
    }

    private void setDetailListAdapter(List<StockInListModel.StockItems> data) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvStockOutDescreptionList.setLayoutManager(linearLayoutManager);
        adapter = new StockInDetailsListAdapter(getContext(), data);
        rvStockOutDescreptionList.setAdapter(adapter);
    }

    private void getSelectedId() {
        selected_id = getArguments().getString(Constants.Fields.SELECTED_ID);
    }

}
