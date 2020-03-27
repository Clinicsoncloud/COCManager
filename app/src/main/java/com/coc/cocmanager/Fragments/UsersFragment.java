package com.coc.cocmanager.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coc.cocmanager.R;
import com.coc.cocmanager.adapter.InstallationsListAdapter;
import com.coc.cocmanager.adapter.UsersListAdapter;
import com.coc.cocmanager.interfaces.ListClickListener;

import java.util.ArrayList;

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

    private void initializeData() {
        setUserListAdapter();
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
