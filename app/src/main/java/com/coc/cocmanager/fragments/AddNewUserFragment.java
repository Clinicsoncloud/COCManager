package com.coc.cocmanager.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.coc.cocmanager.model.CreateUserModel;
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
    @BindView(R.id.edt_user_password)
    TextInputEditText edtUserPassword;
    @BindView(R.id.edt_user_name)
    TextInputEditText edtUserName;
    @BindView(R.id.btn_create_user)
    Button btnCreateUser;
    @BindView(R.id.edt_email)
    TextInputEditText edtEmail;
    @BindView(R.id.edt_phone)
    TextInputEditText edtPhone;
    @BindView(R.id.spn_gender)
    Spinner spnGender;
    @BindView(R.id.edt_name)
    TextInputEditText edtName;
    @BindView(R.id.spn_user_type)
    Spinner spnUserType;
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
        btnCreateUser.setOnClickListener(v -> {
            createNewUser();
        });
    }

    /**
     * create new user with validation of the each field
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNewUser() {
        if (edtName.getText().length() <= 0) {
            edtName.setError("Please Enter Name");
            edtName.setFocusable(true);
        } else if (edtPhone.getText().length() < 10) {
            edtPhone.setError("Please Enter Phone");
            edtPhone.setFocusable(true);
        } else if (edtEmail.getText().length() <= 0 || !Utils.isValidEmail(edtEmail.getText().toString())) {
            edtEmail.setError("Please Enter Valid Email");
            edtEmail.setFocusable(true);
        } else if (edtUserName.getText().length() <= 0) {
            edtUserName.setError("Please Enter Valid UserName");
            edtUserName.setFocusable(true);
        } else if (edtUserPassword.getText().length() < 8) {
            edtUserPassword.setError("Please Enter Valid Password");
            edtUserPassword.setFocusable(true);
        } else {
            createNewUserAPIcall();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNewUserAPIcall() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.STATUS, "true");
                params.put(Constants.Fields.OPERATION, "CREATE");
                params.put(Constants.Fields.EMAIL, edtEmail.getText().toString());
                params.put(Constants.Fields.PHONE, edtPhone.getText().toString());
                params.put(Constants.Fields.FIRST_NAME, edtName.getText().toString());
                params.put(Constants.Fields.USERNAME, edtUserName.getText().toString());
                params.put(Constants.Fields.ROLE_ID, "" + spnUserType.getSelectedItemId());
                params.put(Constants.Fields.GENDER, spnGender.getSelectedItem().toString());
                params.put(Constants.Fields.PASSWORD, edtUserPassword.getText().toString());

                Log.e("request_body"," = "+params);
                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.CREATE_USER,
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
                CreateUserModel userModelObject = (CreateUserModel) Utils.parseResponse(response, CreateUserModel.class);
                if (userModelObject.getSuccess() && userModelObject.getData() != null) {
                    //TODO AFTER SUCCESS
                    clearFormData();
                    Toast.makeText(getContext(), "New User Created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error in User Creation", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Utils.showToast(getContext(), error.toString());
        }
    }

    private void clearFormData() {
        edtName.setText("");
        edtEmail.setText("");
        edtPhone.setText("");
        edtUserName.setText("");
        spnGender.setSelection(0);
        spnUserType.setSelection(0);
        edtUserPassword.setText("");
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
