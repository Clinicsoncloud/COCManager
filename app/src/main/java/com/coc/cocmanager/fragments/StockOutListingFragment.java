package com.coc.cocmanager.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
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
import com.coc.cocmanager.adapter.StockOutListAdapter;
import com.coc.cocmanager.interfaces.ListClickListener;
import com.coc.cocmanager.model.StockInListModel;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by ketan 28-3-2020
 */
public class StockOutListingFragment extends Fragment implements ListClickListener, View.OnClickListener {

    //region variables
    @BindView(R.id.tv_start_date)
    TextView tvStartDate;
    @BindView(R.id.tv_end_date)
    TextView tvEndDate;
    @BindView(R.id.iv_remove_stock)
    ImageView ivRemoveStock;
    @BindView(R.id.rv_stock_out_list)
    RecyclerView rvStockOutList;
    @BindView(R.id.btn_filter_stock_out)
    Button btnFilterStockOut;

    private int mDay;
    private int mYear;
    private int mMonth;
    private String selected_id;
    private StockInListModel stockData;
    private StockOutListAdapter adapter;
    //endregion

    public StockOutListingFragment() {
        // Required empty public constructor
    }

    public static StockOutListingFragment newInstance() {
        StockOutListingFragment fragment = new StockOutListingFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_stock_out_listing, container, false);
        ButterKnife.bind(this, rootView);

        setupEvents();
        initializeData();
        return rootView;
    }

    private void setupEvents() {
        tvEndDate.setOnClickListener(this);
        tvStartDate.setOnClickListener(this);
        ivRemoveStock.setOnClickListener(this);
        btnFilterStockOut.setOnClickListener(this);

    }

    private void initializeData() {
        getStockOutHistoryList();
    }

    private void getStockOutHistoryList() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.STOCK_TYPE, Constants.Fields.OUT);

                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.STOCK_LIST,
                        params, headerParams,
                        (response, error, status) -> handleAPIResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) {
        }
    }

    private void handleAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                stockData = (StockInListModel) Utils.parseResponse(response, StockInListModel.class);
                if (stockData.getFound()) {
                    //TODO AFTER SUCCESS
                    setStockOutListAdapter(stockData.getData());
                    Toast.makeText(getContext(), "Stock Out List Successfully loaded", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setStockOutListAdapter(List<StockInListModel.StockInInfo> data) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvStockOutList.setLayoutManager(linearLayoutManager);
        adapter = new StockOutListAdapter(getContext(), data);
        rvStockOutList.setAdapter(adapter);
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
        if (value == 0) {
            openDetailViewOfStockHistory(position);
        }
    }

    private void openDetailViewOfStockHistory(int position) {
        selected_id = stockData.getData().get(position).getId();

        Fragment fragment = new StockOutDetailsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.Fields.SELECTED_ID, selected_id);
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

            case R.id.iv_remove_stock:
                openAddStock();
                break;

            case R.id.btn_filter_stock_out:
                filterStockHistoryList();
                break;
        }
    }

    private void filterStockHistoryList() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.STOCK_TYPE, Constants.Fields.OUT);
                params.put(Constants.Fields.END_DATE, tvEndDate.getText().toString());
                params.put(Constants.Fields.START_DATE, tvStartDate.getText().toString());

                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.STOCK_LIST,
                        params, headerParams,
                        (response, error, status) -> handleFilterAPIResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) { }
    }

    private void handleFilterAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                stockData = (StockInListModel) Utils.parseResponse(response, StockInListModel.class);
                if (stockData.getFound()) {
                    //TODO AFTER SUCCESS
                    setStockOutListAdapter(stockData.getData());
                    Toast.makeText(getContext(), "Stock Out List Filterd Successfully", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     */
    private void openAddStock() {
        Fragment fragment = new StockOutFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }

    /**
     * @param textView
     */
    private void openCalender(TextView textView) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
