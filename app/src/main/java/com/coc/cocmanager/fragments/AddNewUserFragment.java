package com.coc.cocmanager.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.model.LoginData;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by ketan 27-3-2020
 */
public class AddNewUserFragment extends Fragment {

    //region variables
    @BindView(R.id.edt_user_name)
    TextInputEditText edtUserName;
    @BindView(R.id.edt_user_password)
    TextInputEditText edtUserPassword;
    @BindView(R.id.spn_user_type)
    Spinner spnUserType;
    @BindView(R.id.btn_create_user)
    Button btnCreateUser;
    //endregion

    public AddNewUserFragment() {
        // Required empty public constructor
    }

    public static AddNewUserFragment newInstance() {
        AddNewUserFragment fragment = new AddNewUserFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_add_new_user, container, false);
        ButterKnife.bind(this, rootView);

        setupEvents();
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupEvents() {
        btnCreateUser.setOnClickListener(v -> {createNewUser();});
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNewUser() {
        createNewUserAPIcall();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNewUserAPIcall() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String ,String> params = new HashMap<>();
                params.put(Constants.Fields.EMAIL,"");
                params.put(Constants.Fields.PHONE,"");
                params.put(Constants.Fields.GENDER,"");
                params.put(Constants.Fields.STATUS,"");
                params.put(Constants.Fields.ROLE_ID,"");
                params.put(Constants.Fields.LAST_NAME,"");
                params.put(Constants.Fields.FIRST_NAME,"");

                Map<String, String> headerParams = new HashMap<>();

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
