package com.coc.cocmanager.fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.app.DatePickerDialog;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.View;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.model.LoginData;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import android.widget.Toast;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import android.widget.ImageView;
import android.widget.DatePicker;
import android.view.LayoutInflater;

/**
 * created by ketan 23-3-2020
 */
public class PipelineDetailFragment extends Fragment {

    //region variables
    private int mYear,mMonth,mDay;
    private TextView tvActofitExpiry;

    private int count = 0;
    private TextView tvQty;
    private ImageView ivPlus;
    private ImageView ivMinus;

    private Button btnConsumables;
    private ExpandableRelativeLayout expandableConsumables;
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
        ButterKnife.bind(this,rootView);

        setupUI(rootView);
        setupEvents();
        initializeData();
        return rootView;
    }


    private void setupEvents() {
        tvActofitExpiry.setOnClickListener(v -> {
            openCalender();
        });

        btnConsumables.setOnClickListener(v -> {
            expandableConsumables.toggle();
        });

        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count >= 0) {
                    count = count + 1;
                    tvQty.setText(""+count);
                }
            }
        });


        ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Can't minus right now", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initializeData() {
        showPipelineDetails();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showPipelineDetails() {
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
        } catch (Exception e) { }
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

    private void setupUI(View rootView) {
        tvActofitExpiry = rootView.findViewById(R.id.tv_actofit_expiry);
        btnConsumables = rootView.findViewById(R.id.btn_consumables);
        expandableConsumables = rootView.findViewById(R.id.expandable_consumables);
        ivMinus = rootView.findViewById(R.id.iv_minus);
        ivPlus = rootView.findViewById(R.id.iv_plus);
        tvQty = rootView.findViewById(R.id.tv_count);
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
}
