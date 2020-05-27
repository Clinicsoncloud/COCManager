package com.coc.cocmanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.adapter.AddItemsListAdapter;
import com.coc.cocmanager.interfaces.RvClickListener;
import com.coc.cocmanager.model.ItemsCategory_Response;
import com.coc.cocmanager.model.Request_Item_Data;
import com.coc.cocmanager.model.StockModel;
import com.coc.cocmanager.services.DateService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by ketan 25-3-2020
 */
public class AddNewStockFragment extends Fragment implements RvClickListener {

    //region variables
    Button btnSave;
    TextView tvAddItem;
    Spinner spnCategory;
    RecyclerView rvAddItemList;
    TextInputEditText edtComment;

    private JSONArray itemsArray;
    private StockModel stockData;
    private ArrayList itemsCategoryList;
    private ArrayList itemsCategoryIDList;

    private Context context = getContext();
    private List<Request_Item_Data> itemsList;
    private Request_Item_Data request_item_data;
    private AddItemsListAdapter addItemsListAdapter;
    private ItemsCategory_Response itemsCategory_response;
    //endregion

    public AddNewStockFragment() {
        // Required empty public constructor
    }

    public static AddNewStockFragment newInstance() {
        AddNewStockFragment fragment = new AddNewStockFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_add_new_stock, container, false);

        setupUI(rootView);
        setupEvents();
        initializeData();
        return rootView;
    }

    private void setupUI(View rootView) {
        btnSave = rootView.findViewById(R.id.btn_save);
        tvAddItem = rootView.findViewById(R.id.tv_add_item);
        edtComment = rootView.findViewById(R.id.edt_comment);
        spnCategory = rootView.findViewById(R.id.spn_category);
        rvAddItemList = rootView.findViewById(R.id.rv_add_item_list);
    }

    private void setupEvents() {
        tvAddItem.setOnClickListener(view -> createStockItemArray());
        btnSave.setOnClickListener(view -> addNewStock());
    }

    private void addNewStock() {
        try {
            itemsArray = new JSONArray();
            for (int i = 0; i < itemsList.size(); i++) {
                JSONObject itemsObj = new JSONObject();
                itemsObj.put(Constants.Fields.ITEM_ID, itemsList.get(i).getId());
                itemsObj.put(Constants.Fields.QUANTITY, itemsList.get(i).getQuantity());
                itemsArray.put(itemsObj);
            }

            JSONObject requestObject = new JSONObject();
            requestObject.put(Constants.Fields.STOCK_ITEMS, itemsArray);
            requestObject.put(Constants.Fields.STOCK_TYPE, Constants.Fields.IN);
            requestObject.put(Constants.Fields.CREATED_AT, DateService.getCurrentDateTime(DateService.MM_DD_YYY_HH_MM));
            requestObject.put(Constants.Fields.DESCRIPTION, spnCategory.getSelectedItem().toString() + " - " + edtComment.getText().toString());

            HttpService.accessWebServicesJSON(
                    getContext(),
                    ApiUtils.STOCK_OUT,
                    Request.Method.POST,
                    requestObject,
                    (response, error, status) -> handleAddStockResponse(response, error, status));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleAddStockResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                stockData = (StockModel) Utils.parseResponse(response, StockModel.class);
                if (stockData.getFound()) {
                    //TODO AFTER SUCCESS
                    goToStockListingScreen();
                    Toast.makeText(getContext(), "Stock Added Successfully", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeData() {
        itemsList = new ArrayList<>();

        getCategoryData();
        createStockItemArray();
    }

    private void goToStockListingScreen() {
        Fragment fragment = new InventoryFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }

    private void createStockItemArray() {
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
            requestBodyParams.put(Constants.Fields.STATUS, Constants.Fields.TRUE);

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
                    itemsCategoryList = new ArrayList();
                    itemsCategoryIDList = new ArrayList();

                    itemsCategoryList.add("-Select-");
                    itemsCategoryIDList.add("0");

                    for (int i = 0; i < itemsCategory_response.getData().size(); i++) {
                        itemsCategoryList.add(itemsCategory_response.getData().get(i).getName());
                        itemsCategoryIDList.add(itemsCategory_response.getData().get(i).getId());
                    }
                    setItemsListAdapter();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setItemsListAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        rvAddItemList.setLayoutManager(gridLayoutManager);
        addItemsListAdapter = new AddItemsListAdapter(getContext(), itemsList, itemsCategoryList, itemsCategoryIDList);
        rvAddItemList.setAdapter(addItemsListAdapter);
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
