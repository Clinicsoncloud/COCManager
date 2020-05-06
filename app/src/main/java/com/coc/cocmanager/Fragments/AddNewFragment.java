package com.coc.cocmanager.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.model.LoginData;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * created by ketan 23-3-2020
 */
public class AddNewFragment extends Fragment {

    //region variables
    private int mDay;
    private int mYear;
    private int mMonth;

    private TextView tvActofitExpiry;
    //endregion

    //region methods
    public AddNewFragment() {
        // Required empty public constructor
    }

    public static AddNewFragment newInstance() {
        AddNewFragment fragment = new AddNewFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_add_new, container, false);
        setupUI(rootView);
        setupEvents();
        initializeData();
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initializeData() {
        addNewKioskTOPipeline();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addNewKioskTOPipeline() {
        try {
            if (Utils.isOnline(getContext())) {

                Map<String ,String> params = new HashMap<>();

                params.put(Constants.Fields.USERNAME,"");
                params.put(Constants.Fields.PASSWORD,"");
                params.put(Constants.Fields.USERTYPE,"");

                Map<String, String> headerParams;
                headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.LOGIN_URL,
                        params, headerParams,
                        (response, error, status) -> handleAPIResponse(response, error, status));
            } else {
                Utils.showToast(getContext(),"No Internet connectivity..!");
            }
        } catch (Exception e) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                LoginData patientData = (LoginData) Utils.parseResponse(response,LoginData.class);
                if(patientData.getFound()){
                    //TODO AFTER SUCCESS
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private void setupEvents() {
        tvActofitExpiry.setOnClickListener(v -> {
            openCalender();
        });
    }

    private void openCalender() {
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

    private void setupUI(View rootView) {
        tvActofitExpiry = rootView.findViewById(R.id.tv_actofit_expiry);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) { }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //endregion
}
