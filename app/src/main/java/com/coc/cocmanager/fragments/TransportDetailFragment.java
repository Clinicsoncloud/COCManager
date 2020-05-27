package com.coc.cocmanager.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.adapter.AddItemsListAdapter;
import com.coc.cocmanager.interfaces.RvClickListener;
import com.coc.cocmanager.model.ClinicListModel;
import com.coc.cocmanager.model.ItemListModel;
import com.coc.cocmanager.model.ItemsCategory_Response;
import com.coc.cocmanager.model.Request_Item_Data;
import com.coc.cocmanager.model.UserData;
import com.coc.cocmanager.services.DateService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

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

public class TransportDetailFragment extends Fragment implements RvClickListener {

    @BindView(R.id.tv_installation_type)
    MaterialTextView tvInstallationType;
    @BindView(R.id.tv_clinic_name)
    MaterialTextView tvClinicName;
    @BindView(R.id.tv_clinic_id)
    MaterialTextView tvClinicId;
    @BindView(R.id.tv_gmail_id)
    MaterialTextView tvGmailId;
    @BindView(R.id.tv_gmail_password)
    MaterialTextView tvGmailPassword;
    @BindView(R.id.tv_actofit_id)
    MaterialTextView tvActofitId;
    @BindView(R.id.tv_actofit_password)
    MaterialTextView tvActofitPassword;
    @BindView(R.id.tv_actofit_expiry)
    MaterialTextView tvActofitExpiry;
    @BindView(R.id.spn_client_name_transposrt)
    Spinner spnClientName;
    @BindView(R.id.edt_location)
    TextInputEditText edtLocation;
    @BindView(R.id.edt_address)
    TextInputEditText edtAddress;
    @BindView(R.id.btn_move_to_pipeline)
    Button btnMoveToPipeline;
    @BindView(R.id.btn_add_item)
    Button btnAddItem;
    @BindView(R.id.rv_add_item_list)
    RecyclerView rvAddItemList;

    private String selectedClientName = "";

    // region variables

    private int count = 0;
    private String assign_user_id;
    private int mYear, mMonth, mDay;
    private String selected_position;
    private ArrayList<String> clientNamelist;
    private String selectedClient = "";
    private ArrayList<String> itemList;
    private ArrayList itemsCategoryList;
    private ArrayList itemsCategoryIDList;
    private List<Request_Item_Data> itemsList;
    private Request_Item_Data request_item_data;
    private AddItemsListAdapter addItemsListAdapter;
    private ItemsCategory_Response itemsCategory_response;
    //endregion

    public TransportDetailFragment() {
        // Required empty public constructor
    }

    public static TransportDetailFragment newInstance(String param1, String param2) {
        TransportDetailFragment fragment = new TransportDetailFragment();
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
        View rootView = inflater.inflate(R.layout.layout_transport_detail, container, false);
        ButterKnife.bind(this, rootView);

        setupEvents();
        initializeData();
        return rootView;
    }

    private void initializeData() {
        itemsList = new ArrayList<>();

        getSelectedPosition();
        getCategoryData();
        getTransportDetails();
        createStockItemArray();
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

    private void createStockItemArray() {
        request_item_data = new Request_Item_Data();
        request_item_data.setId("");
        request_item_data.setQuantity("");
        itemsList.add(request_item_data);

        if (addItemsListAdapter != null) {
            addItemsListAdapter.notifyDataSetChanged();
        }
    }

    private void getClientNameList() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.STATUS, Constants.Fields.TRUE);
                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.CLIENT_LIST,
                        params, headerParams,
                        (response, error, status) -> handleClientAPIResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) {
        }
    }

    private void handleClientAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                UserData userData = (UserData) Utils.parseResponse(response, UserData.class);
                if (userData.getFound()) {
                    //TODO AFTER SUCCESS
                    setClientNameList(userData.getData());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setClientNameList(List<UserData.User_Info> data) {
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

        if (selectedClientName != null) {
            int spinnerPosition = dataAdapter.getPosition(selectedClientName);
            spnClientName.setSelection(spinnerPosition);
        }

        spnClientName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    assign_user_id = data.get(position - 1).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void setupEvents() {

        tvActofitExpiry.setOnClickListener(View -> {
            openCalender();
        });

        btnAddItem.setOnClickListener(v -> {

        });

        btnMoveToPipeline.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Pending functionality", Toast.LENGTH_SHORT).show();
        });
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
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        tvActofitExpiry.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void getTransportDetails() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.INSTALLATION_STEP, Constants.Fields.TYPE_TRANASPORT);
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

    private void getSelectedPosition() {
        selected_position = getArguments().getString(Constants.Fields.POSITION);
    }

    private void setTransportDetails(ClinicListModel.ClinicListInfo clinicListInfo) {
        tvClinicId.setText(clinicListInfo.getId());
        tvClinicName.setText(clinicListInfo.getName());
        tvGmailId.setText(clinicListInfo.getGmailid());
        edtAddress.setText(clinicListInfo.getAddress());
        tvActofitId.setText(clinicListInfo.getActofit_id());
        edtLocation.setText(clinicListInfo.getLocation().getName());
        tvGmailPassword.setText(clinicListInfo.getGmail_password());
        tvActofitPassword.setText(clinicListInfo.getActofit_password());
        tvActofitExpiry.setText(DateService.formatDateFromString(clinicListInfo.getActofit_end_date()));
        selectedClientName = clinicListInfo.getUser().getFirstname() + " " +clinicListInfo.getUser().getLastname();
        Log.e("selectedClientName_log", " = "+selectedClientName);
    }

    private void handleAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                ClinicListModel clinicData = (ClinicListModel) Utils.parseResponse(response, ClinicListModel.class);
                if (clinicData.getFound()) {
                    //TODO AFTER SUCCESS
                    setTransportDetails(clinicData.getData().get(Integer.parseInt(selected_position)));
                    getClientNameList();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void rv_click(int position, int value, String key) {
        if (key.equals("remove_item")) {
            itemsList.remove(position);
        }
        addItemsListAdapter.notifyDataSetChanged();
    }
}
