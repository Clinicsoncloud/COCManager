package com.coc.cocmanager.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.coc.cocmanager.R;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by ketan 12-5-2020
 */
public class EditUserFragment extends Fragment {
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
        btnUpdate.setOnClickListener(v -> {updateUser();});
    }

    private void updateUser() {

    }

    private void initializeData() {
        getUserDetails();
    }

    private void getUserDetails() {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }
}
