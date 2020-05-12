package com.coc.cocmanager.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.model.ClinicListModel;
import com.coc.cocmanager.model.CreateUserInfo;
import com.coc.cocmanager.model.CreateUserModel;
import com.coc.cocmanager.model.UpdateUserModel;
import com.coc.cocmanager.model.UserData;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by ketan 12-5-2020
 */
public class EditUserFragment extends Fragment {

    //region variables
    @BindView(R.id.edt_name)
    TextInputEditText edtName;
    @BindView(R.id.edt_email)
    TextInputEditText edtEmail;
    @BindView(R.id.edt_phone)
    TextInputEditText edtPhone;
    @BindView(R.id.edt_user_name)
    TextInputEditText edtUserName;
    @BindView(R.id.edt_user_password)
    TextInputEditText edtUserPassword;
    @BindView(R.id.spn_user_type)
    Spinner spnUserType;
    @BindView(R.id.spn_gender)
    Spinner spnGender;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    private String selected_id;
    private String selected_position;

    //endregion

    public EditUserFragment() {
        // Required empty public constructor
    }

    public static EditUserFragment newInstance(String param1, String param2) {
        EditUserFragment fragment = new EditUserFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_edit_user, container, false);
        ButterKnife.bind(this,rootView);

        setupEvents();
        initializeData();
        return rootView;
    }

    private void setupEvents() {
        btnUpdate.setOnClickListener(v -> {updateUserDetails();});
    }

    private void getPosition() {
        selected_id = getArguments().getString("id");
        selected_position = getArguments().getString("position");
    }

    private void initializeData() {
        getPosition();
        getUserDetails(selected_id);
    }

    private void goToUserScreen() {
        Fragment fragment = new UsersFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }

    private void updateUserDetails() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Fields.PASSWORD,"");
                params.put(Constants.Fields.PHONE,edtPhone.getText().toString());
                params.put(Constants.Fields.EMAIL,edtEmail.getText().toString());
                params.put(Constants.Fields.FIRST_NAME,edtName.getText().toString());
                params.put(Constants.Fields.USERNAME,edtUserName.getText().toString());

                Map<String, String> headerParams = new HashMap<>();
                String url = ApiUtils.UPDATE_USER + selected_id;

                HttpService.accessWebServicess(
                        getContext(),
                        url,
                        Request.Method.PUT,
                        params,
                        headerParams,
                        (response, error, status) -> handleAPIResponse(response, error, status));

            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) { }
    }

    private void getUserDetails(String selected_id) {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> params = new HashMap<>();
                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServicesGet(
                        getContext(), ApiUtils.UPDATE_USER + selected_id,
                        params, headerParams,
                        (response, error, status) -> handleUserDetailResponse(response, error, status));
            } else {
                Utils.showToast(getContext(), "No Internet connectivity..!");
            }
        } catch (Exception e) {
        }
    }

    private void setUserDetails(UpdateUserModel.User_Info user_info) {
        edtEmail.setText(user_info.getEmail());
        edtPhone.setText(user_info.getPhone());
        edtName.setText(user_info.getFirst_name());
        edtUserName.setText(user_info.getUsername());
        edtUserPassword.setText(user_info.getPassword());

        if(user_info.getGender().equals("Male")){
            spnGender.setSelection(1);
        }else{
            spnGender.setSelection(0);
        }

        if(user_info.getUser_type().equals("Administrator")){
            spnUserType.setSelection(1);
        }else if(user_info.getUser_type().equals("Customer")){
            spnUserType.setSelection(2);
        }else if(user_info.getUser_type().equals("User")){
            spnUserType.setSelection(3);
        }

    }

    private void handleAPIResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                CreateUserModel clinicData = (CreateUserModel) Utils.parseResponse(response, CreateUserModel.class);
                if (clinicData.getSuccess()) {
                    //TODO AFTER SUCCESS
                    goToUserScreen();
                    Toast.makeText(getContext(), "User Updated Successfully", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleUserDetailResponse(String response, VolleyError error, String status) {
        if (status.equals("response")) {
            try {
                UpdateUserModel userData = (UpdateUserModel) Utils.parseResponse(response, UpdateUserModel.class);
                if (userData.getFound()) {
                    //TODO AFTER SUCCESS
                    setUserDetails(userData.getData());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
