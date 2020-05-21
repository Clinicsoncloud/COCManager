package com.coc.cocmanager.Activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.adapter.AddItemsListAdapter;
import com.coc.cocmanager.interfaces.RvClickListener;
import com.coc.cocmanager.model.ItemCategoryModel;
import com.coc.cocmanager.model.ItemsCategory_Response;
import com.coc.cocmanager.model.Request_Item_Data;
import com.coc.cocmanager.services.DateService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddItemsActivity extends AppCompatActivity implements RvClickListener {

    @BindView(R.id.tv_AddItem)
    TextView tvAddItem;
    @BindView(R.id.rv_AddItemsList)
    RecyclerView rvAddItemsList;
    @BindView(R.id.btn_Request)
    Button btnRequest;

    private JSONArray itemsArray;
    private ArrayList itemsCategoryList;
    private ArrayList itemsCategoryIDList;
    private List<Request_Item_Data> itemsList;
    private Request_Item_Data request_item_data;
    private AddItemsListAdapter addItemsListAdapter;
    private Context context = AddItemsActivity.this;
    private ItemsCategory_Response itemsCategory_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        ButterKnife.bind(this);

        setUpEvents();
        initializeData();
    }

    private void setUpEvents() {
        tvAddItem.setOnClickListener(view -> createDummyItemsArray());
        btnRequest.setOnClickListener(view -> submitItemRequest());
    }

    private void submitItemRequest() {
        try {
            itemsArray = new JSONArray();
            for (int i = 0; i < itemsList.size(); i++) {
                JSONObject itemsObj = new JSONObject();
                itemsObj.put("item_id", itemsList.get(i).getId());
                itemsObj.put("quantity", itemsList.get(i).getQuantity());
                itemsArray.put(itemsObj);
            }

            JSONObject requestObject = new JSONObject();
            requestObject.put("stock_items", itemsArray);
            requestObject.put("stock_type", "Out");
            requestObject.put("description", "removed at midnight for testing from coc manager app");

            Log.e("requestObject_log"," = "+requestObject);
            HttpService.accessWebServicesJSON(
                    context,
                    ApiUtils.STOCK_OUT,
                    Request.Method.POST,
                    requestObject,
                    (response, error, status) -> handleRequestItemsResponse(response, error, status));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleRequestItemsResponse(String response, VolleyError error, String status) {
        Log.e("Request_res_Log", ":" + response);
    }

    private void initializeData() {
        itemsList = new ArrayList<>();
        getCategoryData();
        createDummyItemsArray();
    }

    private void createDummyItemsArray() {
        request_item_data = new Request_Item_Data();
        request_item_data.setId("");
        request_item_data.setQuantity("");
        itemsList.add(request_item_data);

        if (addItemsListAdapter != null) {
            addItemsListAdapter.notifyDataSetChanged();
        }
    }

    private void getCategoryData() {
        try {
            Map<String, String> headerParams = new HashMap<>();
            Map<String, String> requestBodyParams = new HashMap<>();

            requestBodyParams.put("status","true");

            HttpService.accessWebServices(
                    context,
                    ApiUtils.ITEM_CATEGORY_LIST,
                    requestBodyParams,
                    headerParams,
                    (response, error, status) -> handleCategoryAPIResponse(response, error, status));
        } catch (Exception e) {
        }
    }

    private void handleCategoryAPIResponse(String response, VolleyError error, String status) {
        try {
            Log.e("res_Category", ":" + response);

            if (status.equals("response")) {

                itemsCategory_response = (ItemsCategory_Response) Utils.parseResponse(response, ItemsCategory_Response.class);

                if (itemsCategory_response.getFound()) {

                    itemsCategoryList = new ArrayList();
                    itemsCategoryIDList = new ArrayList();

                    itemsCategoryList.add("Select Category");
                    itemsCategoryIDList.add("0");

                    for (int i = 0; i < itemsCategory_response.getData().size(); i++) {

                        itemsCategoryList.add(itemsCategory_response.getData().get(i).getName());
                        itemsCategoryIDList.add(itemsCategory_response.getData().get(i).getId());
                    }
                    Log.e("itemsCategoryList_Size", ":" + itemsCategoryList.size());

                    setItemsListAdapter();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setItemsListAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);
        rvAddItemsList.setLayoutManager(gridLayoutManager);
        addItemsListAdapter = new AddItemsListAdapter(context, itemsList, itemsCategoryList, itemsCategoryIDList);
        rvAddItemsList.setAdapter(addItemsListAdapter);
        addItemsListAdapter.setRvClickListener(this);
    }

    @Override
    public void rv_click(int position, int value, String key) {
        if (key.equals("remove_item")) {
            itemsList.remove(position);
        }
        addItemsListAdapter.notifyDataSetChanged();
    }
}
