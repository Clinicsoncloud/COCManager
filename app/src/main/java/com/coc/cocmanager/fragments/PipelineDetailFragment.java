package com.coc.cocmanager.fragments;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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
import com.coc.cocmanager.interfaces.VolleyResponse;
import com.coc.cocmanager.model.ClinicListModel;
import com.coc.cocmanager.model.ItemListModel;
import com.coc.cocmanager.model.ItemsCategory_Response;
import com.coc.cocmanager.model.Request_Item_Data;
import com.coc.cocmanager.model.StockModel;
import com.coc.cocmanager.model.StockUsingTypeModel;
import com.coc.cocmanager.model.UserData;
import com.coc.cocmanager.services.DateService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by ketan 23-3-2020
 */
public class PipelineDetailFragment extends Fragment implements RvClickListener {

    //region variables
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
    @BindView(R.id.tv_client_name)
    Spinner spnClientName;
    @BindView(R.id.edt_location)
    TextInputEditText edtLocation;
    @BindView(R.id.edt_address)
    TextInputEditText edtAddress;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_add_item)
    Button btnAddItem;
    @BindView(R.id.rv_add_item_list)
    RecyclerView rvAddItemList;

    private int mYear, mMonth, mDay;

    //string and arraylist objects
    private String item_id;
    private String clinic_id;
    private JSONArray itemsArray;
    private String assign_user_id;
    private String item_category_id;
    private String selected_position;
    private ArrayList<String> itemList;
    private ArrayList<String> categoryList;
    private ArrayList<String> clientNamelist;

    //model class objects
    private ItemListModel itemListData;
    private ClinicListModel clinicData;
    private StockUsingTypeModel updateStockData;

    private StockModel stockData;
    private ArrayList itemsCategoryList;
    private ArrayList itemsCategoryIDList;
    private List<Request_Item_Data> itemsList;
    private Request_Item_Data request_item_data;
    private AddItemsListAdapter addItemsListAdapter;
    private ItemsCategory_Response itemsCategory_response;

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
        btnSave.setOnClickListener(v -> {
            moveToTransport();
        });
        tvActofitExpiry.setOnClickListener(v -> {
            openCalender();
        });

        btnAddItem.setOnClickListener(v -> {
            createStockItemArray();
        });
    }

    private boolean valid() {
        if (edtLocation.getText().length() == 0) {
            Toast.makeText(getContext(), "Please Enter Location", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tvClinicName.getText().length() == 0) {
            Toast.makeText(getContext(), "Please Enter Clinic", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtGmailId.getText().length() == 0) {
            edtGmailId.setError("Please Enter Gmail id");
            return false;
        } else if (edtGmailPassword.getText().length() == 0) {
            edtGmailPassword.setError("Please Enter Password");
            return false;
        } else if (edtActofitId.getText().length() == 0) {
            edtActofitId.setError("Please Enter Actofit ID");
            return false;
        } else if (edtActofitPassword.getText().length() == 0) {
            edtActofitPassword.setError("Please Enter Actofit Password");
            return false;
        } else if (spnClientName.getSelectedItemId() == -1) {
            Toast.makeText(getContext(), "Please select the client Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtAddress.getText().length() == 0) {
            edtAddress.setError("Please Enter Address");
            return false;
        }
        return true;
    }

    private void moveToTransport() {
        try {
            itemsArray = new JSONArray();
            for (int i = 0; i < itemsList.size(); i++) {
                JSONObject itemsObj = new JSONObject();
                itemsObj.put(Constants.Fields.ITEM_ID, itemsList.get(i).getId());
                itemsObj.put(Constants.Fields.QUANTITY, itemsList.get(i).getQuantity());
                itemsArray.put(itemsObj);
            }

            JSONObject rquestObject = new JSONObject();
            rquestObject.put(Constants.Fields.DESCRIPTION, "Sent to" + tvClinicName.getText().toString());
            rquestObject.put(Constants.Fields.STOCK_TYPE, Constants.Fields.OUT);
            rquestObject.put(Constants.Fields.CLINIC_ID, clinic_id);
            rquestObject.put(Constants.Fields.INSTALLATION_STEP, Constants.Fields.TYPE_TRANASPORT);
            rquestObject.put(Constants.Fields.CREATED_AT, DateService.getCurrentDateTime(DateService.MM_DD_YYY_HH_MM));
            rquestObject.put(Constants.Fields.STOCK_ITEMS, itemsArray);

            Log.e("params_log_Obj", " = " + rquestObject);

            HttpService.accessWebServicesJSON(
                    getContext(),
                    ApiUtils.STOCK_OUT,
                    Request.Method.POST,
                    rquestObject,
                    new VolleyResponse() {
                        @Override
                        public void onProcessFinish(String response, VolleyError error, String status) {
                            handleMoveToTransportResponse(response, error, status);
                        }

                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleMoveToTransportResponse(String response, VolleyError error, String status) {
        Log.e("response_log", " = " + response);
        if (status.equals("response")) {
            try {
                stockData = (StockModel) Utils.parseResponse(response, StockModel.class);
                if (stockData.getFound()) {
                    //TODO AFTER SUCCESS
                    Toast.makeText(getContext(), "Moved to Transport Successfully", Toast.LENGTH_SHORT).show();
                    gotoTransportFragment();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoTransportFragment() {
        Fragment fragment = new InstallationHomeFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initializeData() {
        itemsList = new ArrayList<>();

        getPosition();
        getCategoryData();
        showPipelineDetails();
        createStockItemArray();
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

    private void getPosition() {
        selected_position = getArguments().getString(Constants.Fields.POSITION);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showPipelineDetails() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.INSTALLATION_STEP, Constants.Fields.TYPE_PIPELINE);

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
                    clinic_id = clinicData.getData().get(Integer.parseInt(selected_position)).getId();
                    setPipelineDetailData(clinicData.getData().get(Integer.parseInt(selected_position)));
                    getClientList();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getClientList() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.STATUS, Constants.Fields.TRUE);

                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.CLIENT_LIST,
                        params, headerParams,
                        (response, error, status) -> handleClientNameResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) {
        }
    }

    private void handleClientNameResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                UserData userData = (UserData) Utils.parseResponse(response, UserData.class);
                if (userData.getFound()) {
                    //TODO AFTER SUCCESS
                    setClientList(userData.getData());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setClientList(List<UserData.User_Info> data) {
        int i;
        clientNamelist = new ArrayList<>();
        clientNamelist.add("Select Client");

        for (i = 0; i < data.size(); i++) {
            clientNamelist.add(data.get(i).getFirst_name() + " " + data.get(i).getLast_name());
        }

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(getContext().getApplicationContext(),
                R.layout.simple_item_selected, clientNamelist);
        dataAdapter.setDropDownViewResource(R.layout.simple_item);
        spnClientName.setAdapter(dataAdapter);

        spnClientName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    assign_user_id = data.get(position - 1).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPipelineDetailData(ClinicListModel.ClinicListInfo clinicListInfo) {
        tvClinicId.setText(clinicListInfo.getId());
        tvClinicName.setText(clinicListInfo.getName());
        edtAddress.setText(clinicListInfo.getAddress());
        edtGmailId.setText(clinicListInfo.getGmailid());
        edtActofitId.setText(clinicListInfo.getActofit_id());
        edtLocation.setText(clinicListInfo.getLocation().getName());
        edtGmailPassword.setText(clinicListInfo.getGmail_password());
        tvActofitExpiry.setText(clinicListInfo.getActofit_end_date());
        edtActofitPassword.setText(clinicListInfo.getActofit_password());
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
        tvActofitExpiry = rootView.findViewById(R.id.tv_actofit_expiry);
    }

}
