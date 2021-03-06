package com.coc.cocmanager.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.adapter.UsersListAdapter;
import com.coc.cocmanager.interfaces.ListClickListener;
import com.coc.cocmanager.model.UserData;
import com.coc.cocmanager.model.DeleteUserModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by ketan 27-3-2020
 */
public class UsersFragment extends Fragment implements ListClickListener {

    //region variables
    private RecyclerView rvUserList;
    private ImageView ivAddNewUser;

    UserData userData;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        getUserList();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getUserList() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String,String> params = new HashMap<>();
                params.put("status","true");

                Map<String, String> headerParams  = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.USER_LIST,
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
                userData = (UserData) Utils.parseResponse(response,UserData.class);
                if(userData.getFound()){
                    //TODO AFTER SUCCESS
                    if(userData.getData() != null) {
                        setUserListAdapter(userData.getData());
                    }else{
                        Toast.makeText(getContext(), "No users created", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * delete user API call method
     * handle response usign handleRespone method
     * @param position
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void deleteUserAPI(int position) {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String, String> headerParams  = new HashMap<>();
                String url = ApiUtils.DELETE_USER + position;
                Log.e("url_log"," = "+url);
                HttpService.accessWebServicess(
                        getContext(),
                        url,
                        Request.Method.DELETE,
                        null,
                        headerParams,
                        (response, error, status) -> handleResponse(response, error, status));
            } else {
                Utils.showToast(getContext(),"No Internet connectivity..!");
            }
        } catch (Exception e) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleResponse(String response, VolleyError error, String status) {
        Log.e("response_log"," = "+response);
        if (status.equals("response")) {
            try {
                DeleteUserModel userModel = (DeleteUserModel) Utils.parseResponse(response, DeleteUserModel.class);
                if(userModel.getSuccess()){
                    //TODO AFTER SUCCESS
                    setUserListAdapter(userData.getData());
                    Toast.makeText(getContext(), "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setUserListAdapter(List<UserData.User_Info> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvUserList.setLayoutManager(linearLayoutManager);
        UsersListAdapter adapter = new UsersListAdapter(getContext(),list);
        rvUserList.setAdapter(adapter);
        adapter.setListClickListener(this);
        adapter.notifyDataSetChanged();
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

    /**
     * list item click listener
     * @param position
     * @param value
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void click(int position, int value) {
        int id = 0;
        id = Integer.parseInt(userData.getData().get(position).getId());
        if(value == 0) {
            deleteUser(id);
        }else if(value == 1){
            editUser(id,position);
        }
    }

    private void editUser(int id,int position) {
        Fragment fragment = new EditUserFragment();
        Bundle args = new Bundle();
        args.putString("id",""+id);
        args.putString("position",""+position);
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void deleteUser(int position) {
        deleteUserAPI(position);
    }
    //endregion
}
