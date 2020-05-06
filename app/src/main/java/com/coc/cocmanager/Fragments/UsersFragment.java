package com.coc.cocmanager.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.adapter.InstallationsListAdapter;
import com.coc.cocmanager.adapter.UsersListAdapter;
import com.coc.cocmanager.interfaces.ListClickListener;
import com.coc.cocmanager.model.LoginData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * created by ketan 27-3-2020
 */
public class UsersFragment extends Fragment implements ListClickListener {

    //region variables

    private RecyclerView rvUserList;
    private ImageView ivAddNewUser;

    //endregion

    //region methods

    public UsersFragment() {
        // Required empty public constructor
    }

    public static UsersFragment newInstance() {
        UsersFragment fragment = new UsersFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);

        setupUI(rootView);
        setupEvents();
        initializeData();
        return rootView;
    }

    private void setupEvents() {
        ivAddNewUser.setOnClickListener(v -> {
            openAddNewUserScreen();
        });
    }

    private void openAddNewUserScreen() {
        Fragment fragment = new AddNewUserFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initializeData() {
//        getUserList();
        setUserListAdapter();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getUserList() {
        try {
            if (Utils.isOnline(getContext())) {

                Map<String, String> headerParams;
                headerParams = new HashMap<>();

                HttpService.accessWebServicesGet(
                        getContext(), ApiUtils.LOGIN_URL,
                        null, headerParams,
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

    private void setUserListAdapter() {
        ArrayList list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvUserList.setLayoutManager(linearLayoutManager);
        UsersListAdapter adapter = new UsersListAdapter(getContext(), list);
        rvUserList.setAdapter(adapter);
        adapter.setListClickListener(this);
    }

    private void setupUI(View rootView) {
        rvUserList = rootView.findViewById(R.id.rv_users_list);
        ivAddNewUser = rootView.findViewById(R.id.iv_add_new_user);
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

    @Override
    public void click(int position, int value) { }

    //endregion
}
