package com.coc.cocmanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.coc.cocmanager.model.ClinicListModel;
import com.coc.cocmanager.model.ItemsCategory_Response;
import com.coc.cocmanager.model.LocationModel;
import com.coc.cocmanager.model.Request_Item_Data;
import com.coc.cocmanager.model.StockModel;
import com.coc.cocmanager.services.DateService;
import com.google.android.material.textfield.TextInputEditText;

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

public class StockOutFragment extends Fragment implements RvClickListener {

    //region variables
    @BindView(R.id.spn_stock_out_category)
    Spinner spnStockOutCategory;
    @BindView(R.id.edt_comment)
    TextInputEditText edtComment;
    @BindView(R.id.tv_add_item)
    TextView tvAddItem;
    @BindView(R.id.rv_add_item_list)
    RecyclerView rvAddItemList;
    @BindView(R.id.btn_stock_out)
    Button btnStockOut;
    @BindView(R.id.spn_location)
    Spinner spnLocation;
    @BindView(R.id.spn_clinic_name)
    Spinner spnClinicName;

    private JSONArray itemsArray;
    private StockModel stockData;
    private ArrayList itemsCategoryList;
    private ArrayList itemsCategoryIDList;

    private String clinic_id;
    private String location_id;
    private String selectedItem;
    private LocationModel locationModel;
    private ArrayList<String> clinicList;
    private Context context = getContext();
    private ClinicListModel clinicListData;
    private ArrayList<String> locationList;
    private List<Request_Item_Data> itemsList;
    private Request_Item_Data request_item_data;
    private AddItemsListAdapter addItemsListAdapter;
    private ItemsCategory_Response itemsCategory_response;
    //endregion

    public StockOutFragment() {
        // Required empty public constructor
    }

    public static StockOutFragment newInstance() {
        StockOutFragment fragment = new StockOutFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_stock_out, container, false);
        ButterKnife.bind(this, rootView);

        setupEvents();
        iniitalizeData();
        return rootView;
    }

    private void iniitalizeData() {
        itemsList = new ArrayList<>();

        getCategoryData();
        getLocationList();
        createStockItemArray();
    }

    private void getLocationList() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServicesGet(
                        getContext(), ApiUtils.LOCATION_LIST,
                        params, headerParams,
                        (response, error, status) -> handleLocationResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) {
        }
    }

    private void handleLocationResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                locationModel = (LocationModel) Utils.parseResponse(response, LocationModel.class);
                if (locationModel.getFound() && locationModel.getData() != null) {
                    //TODO AFTER SUCCESS
                    setLocationListToSpinner(locationModel.getData());
                    Toast.makeText(getContext(), "Successfully loaded list", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setLocationListToSpinner(List<LocationModel.LocationInfo> data) {
        int i;
        locationList = new ArrayList<>();
        locationList.add("Select Location");

        for (i = 0; i < data.size(); i++) {
            locationList.add(data.get(i).getName());
        }

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(getContext().getApplicationContext(),
                R.layout.simple_item_selected, locationList);
        dataAdapter.setDropDownViewResource(R.layout.simple_item);
        spnLocation.setAdapter(dataAdapter);

        spnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    selectedItem = spnLocation.getSelectedItem().toString();
                    location_id = data.get(position - 1).getId();
                    getClinicList(location_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getClinicList(String locationid) {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.LOCATION_ID, locationid);
                params.put(Constants.Fields.STATUS, Constants.Fields.TRUE);

                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.CLINIC_LIST,
                        params, headerParams,
                        (response, error, status) -> handleClinicAPIResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) {
        }
    }

    private void handleClinicAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                clinicListData = (ClinicListModel) Utils.parseResponse(response, ClinicListModel.class);
                if (clinicListData.getFound() && clinicListData.getData() != null) {
                    //TODO AFTER SUCCESS
                    setClinicListToSpinner(clinicListData.getData());
                    Toast.makeText(getContext(), "Successfully loaded list", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setClinicListToSpinner(List<ClinicListModel.ClinicListInfo> data) {
        clinicList = new ArrayList<>();
        clinicList.add("Select Clinic");

        for (int i = 0; i < data.size(); i++) {
            clinicList.add(data.get(i).getName());
        }

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(getContext().getApplicationContext(),
                R.layout.simple_item_selected, clinicList);
        dataAdapter.setDropDownViewResource(R.layout.simple_item);
        spnClinicName.setAdapter(dataAdapter);

        spnClinicName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    selectedItem = spnClinicName.getSelectedItem().toString();
                    clinic_id = data.get(position - 1).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupEvents() {
        btnStockOut.setOnClickListener(v -> {
            if (valid())
                removeStock();
        });
        tvAddItem.setOnClickListener(view -> createStockItemArray());
    }

    private boolean valid() {
        if(edtComment.getText().length() == 0){
            edtComment.setError("Please Add Comment");
            return false;
        }else if(spnStockOutCategory.getSelectedItemId() == -1){
            Toast.makeText(getContext(), "Please select Category", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void removeStock() {
        try {
            itemsArray = new JSONArray();
            for (int i = 0; i < itemsList.size(); i++) {
                JSONObject itemsObj = new JSONObject();
                itemsObj.put(Constants.Fields.ITEM_ID, itemsList.get(i).getId());
                itemsObj.put(Constants.Fields.QUANTITY, itemsList.get(i).getQuantity());
                itemsArray.put(itemsObj);
            }

            JSONObject requestObject = new JSONObject();
            requestObject.put(Constants.Fields.CLINIC_ID, clinic_id);
            requestObject.put(Constants.Fields.STOCK_ITEMS, itemsArray);
            requestObject.put(Constants.Fields.STOCK_TYPE, Constants.Fields.OUT);
            requestObject.put(Constants.Fields.DESCRIPTION, spnStockOutCategory.getSelectedItem().toString() + " - " + edtComment.getText().toString());

            HttpService.accessWebServicesJSON(
                    getContext(),
                    ApiUtils.STOCK_OUT,
                    Request.Method.POST,
                    requestObject,
                    (response, error, status) -> handleRemoveStockResponse(response, error, status));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleRemoveStockResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                stockData = (StockModel) Utils.parseResponse(response, StockModel.class);
                if (stockData.getFound()) {
                    //TODO AFTER SUCCESS
                    goToStockListingScreen();
                    Toast.makeText(getContext(), "Stock Removed Successfully", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
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
