package com.coc.cocmanager.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.adapter.ItemListAdapter;
import com.coc.cocmanager.adapter.StockListAdapter;
import com.coc.cocmanager.adapter.StockOutListAdapter;
import com.coc.cocmanager.interfaces.ListClickListener;
import com.coc.cocmanager.model.ItemCategoryInfo;
import com.coc.cocmanager.model.ItemsCategory_Response;
import com.coc.cocmanager.model.Items_Response;
import com.coc.cocmanager.model.StockItem;
import com.coc.cocmanager.model.StockSubItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by ketan 25-3-2020
 */
public class AvailableStockFragment extends Fragment implements ListClickListener {

    //region variables
    private Spinner spnCategory;
    private ItemListAdapter adapter;
    private String item_category_id;
    private ArrayList itemsCategoryList;
    private ArrayList itemsCategoryIDList;
    private RecyclerView rvAvailableStock;
    private ItemsCategory_Response itemsCategory_response;
    //endregion

    public AvailableStockFragment() {
        // Required empty public constructor
    }

    public static AvailableStockFragment newInstance() {
        AvailableStockFragment fragment = new AvailableStockFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_available_stock, container, false);

        setupUI(rootView);
        initializeData();
        return rootView;
    }

    private void initializeData() {
        getCategoryList();
    }

    private void getCategoryList() {
        try {
            Map<String, String> headerParams = new HashMap<>();
            Map<String, String> requestBodyParams = new HashMap<>();
            requestBodyParams.put(Constants.Fields.STATUS,Constants.Fields.TRUE);

            HttpService.accessWebServices(
                    getContext(),
                    ApiUtils.ITEM_CATEGORY_LIST,
                    requestBodyParams,
                    headerParams,
                    (response, error, status) -> handleCategoryAPIResponse(response, error, status));
        } catch (Exception e) {
        }
    }

    private void handleCategoryAPIResponse(String response, VolleyError error, String status) {
        try {
          if (status.equals("response")) {
                itemsCategory_response = (ItemsCategory_Response) Utils.parseResponse(response, ItemsCategory_Response.class);
                if (itemsCategory_response.getFound()) {
                    setCategoryList(itemsCategory_response.getData());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCategoryList(List<ItemCategoryInfo> data) {
        itemsCategoryList = new ArrayList<>();
        itemsCategoryList.add("Select Category");

        for (int i = 0; i < data.size(); i++) {
            itemsCategoryList.add(data.get(i).getName());
        }

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(getContext().getApplicationContext(),
                R.layout.simple_item_selected, itemsCategoryList);
        dataAdapter.setDropDownViewResource(R.layout.simple_item);
        spnCategory.setAdapter(dataAdapter);

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    item_category_id = data.get(position -1).getId();
                    getItemListOfSelectedCategory(item_category_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void getItemListOfSelectedCategory(String item_category_id) {
        try {
            Map<String, String> headerParams = new HashMap<>();
            Map<String, String> requestBodyParams = new HashMap<>();

            requestBodyParams.put(Constants.Fields.ITEM_CATEGORY_ID, item_category_id);
            requestBodyParams.put(Constants.Fields.STATUS, Constants.Fields.TRUE);
            HttpService.accessWebServices(
                    getContext(),
                    ApiUtils.ITEM_LIST,
                    requestBodyParams,
                    headerParams,
                    (response, error, status) -> handleItemsAPIResponse(response, error, status));
        } catch (Exception e) {
        }
    }

    private void handleItemsAPIResponse(String response, VolleyError error, String status) {
        try {
            if (status.equals("response")) {
                Items_Response items_response = (Items_Response) Utils.parseResponse(response, Items_Response.class);
                if (items_response.getFound()) {
                    setStockListAdapter(items_response.getData());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setStockListAdapter(List<Items_Response.Items_Data> data) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvAvailableStock.setLayoutManager(linearLayoutManager);
        adapter = new ItemListAdapter(getContext(), data);
        rvAvailableStock.setAdapter(adapter);
        adapter.setListClickListener(this);
        adapter.notifyDataSetChanged();
    }

    private void setupUI(View rootView) {
        spnCategory = rootView.findViewById(R.id.spn_select_category);
        rvAvailableStock = rootView.findViewById(R.id.rv_available_stock_list);
    }

    @Override
    public void click(int position, int value) {

    }
}
