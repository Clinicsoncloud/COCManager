package com.coc.cocmanager.fragments;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.adapter.StockInDetailsListAdapter;
import com.coc.cocmanager.adapter.UpdateItemListAdapter;
import com.coc.cocmanager.model.StockInDetailModel;
import com.coc.cocmanager.model.StockInListModel;
import com.coc.cocmanager.services.DateService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
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
    @BindView(R.id.iv_edit)
    ImageView ivEdit;

    private String selected_id;
    private Dialog updateDialog;
    private Button btnSaveUpdate;
    private JSONArray itemsArray;
    private RecyclerView rvUpdatedItem;
    private StockInDetailModel stockData;
    private EditText edtDescreptionUpdate;
    private StockInDetailsListAdapter adapter;
    private UpdateItemListAdapter updateAdapter;

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
        ButterKnife.bind(this, rootView);

        setupEvents();
        initializeData();
        return rootView;
    }

    private void setupEvents() {
        ivEdit.setOnClickListener(v -> {
            updateStockDetails();
        });
    }

    private void updateStockDetails() {
        showPopupToUpdateStock();
    }

    private void showPopupToUpdateStock() {
        updateDialog = new Dialog(getActivity());
        View rootview = View.inflate(getContext(), R.layout.dialog_update_stock_items, null);
        updateDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        updateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        updateDialog.setContentView(rootview);
        updateDialog.show();
        updateDialog.setCancelable(true);

        btnSaveUpdate = updateDialog.findViewById(R.id.btn_save_update);
        edtDescreptionUpdate = updateDialog.findViewById(R.id.edt_description);
        rvUpdatedItem = (RecyclerView) updateDialog.findViewById(R.id.rv_update_item_list);

        if (stockData.getData() != null) {
            setAdapterForUpdating(rvUpdatedItem,stockData.getData().getStockItems());
        }

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStockItems();
            }
        });
    }

    private void updateStockItems() {
        try {
            if (Utils.isOnline(getContext())) {
                String url = ApiUtils.STOCK_IN_DETAILS + "/" + selected_id;
                Map<String, String> params = new HashMap<>();
                Map<String, String> headerParams = new HashMap<>();

                itemsArray = new JSONArray();
                for (int i = 0; i < stockData.getData().getStockItems().size(); i++) {
                    JSONObject itemsObj = new JSONObject();
                    itemsObj.put(Constants.Fields.ITEM_ID, stockData.getData().getStockItems().get(i).getId());
                    itemsObj.put(Constants.Fields.QUANTITY, stockData.getData().getStockItems().get(i).getQuantity());
                    itemsArray.put(itemsObj);
                }

                JSONObject requestObject = new JSONObject();
                requestObject.put(Constants.Fields.STOCK_ITEMS, itemsArray);
                requestObject.put(Constants.Fields.STOCK_TYPE, Constants.Fields.IN);
                requestObject.put(Constants.Fields.CREATED_AT, DateService.getCurrentDateTime(DateService.MM_DD_YYY_HH_MM));
                requestObject.put(Constants.Fields.DESCRIPTION, edtDescreptionUpdate.getText().toString());

                Log.e("url_log", " = " + url);
                HttpService.accessWebServicesJSON(
                        getContext(),
                        url,
                        Request.Method.PUT,
                        requestObject,
                        (response, error, status) -> handleUpdateAPIResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) {
        }
    }

    private void handleUpdateAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                stockData = (StockInDetailModel) Utils.parseResponse(response, StockInDetailModel.class);
                if (stockData.getFound()) {
                    //TODO AFTER SUCCESS
                    Toast.makeText(getContext(), "Stock List Details fetched Successfully", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapterForUpdating(RecyclerView rvUpdatedItem, List<StockInListModel.StockItems> stockItems) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvUpdatedItem.setLayoutManager(linearLayoutManager);
        updateAdapter = new UpdateItemListAdapter(getContext(), stockItems);
        rvUpdatedItem.setAdapter(adapter);
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
