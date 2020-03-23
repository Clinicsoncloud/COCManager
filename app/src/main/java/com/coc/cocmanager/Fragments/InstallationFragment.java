package com.coc.cocmanager.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coc.cocmanager.R;
import com.coc.cocmanager.adapter.InstallationsListAdapter;
import com.coc.cocmanager.interfaces.ListClickListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * created by ketan 23-3-2020
 */
public class InstallationFragment extends Fragment implements ListClickListener {


    //region variables
    private RecyclerView rvInstallations;
    private Context context = getContext();
    //endregion

    public InstallationFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InstallationFragment newInstance() {
        InstallationFragment fragment = new InstallationFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_installation, container, false);
        setupUI(rootView);
        initalizeData();
        return rootView;
    }

    private void initalizeData() {
        setListAdapter();
    }

    private void setupUI(View rootView) {
        rvInstallations = rootView.findViewById(R.id.rv_installations);
    }

    private void setListAdapter() {
        ArrayList list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rvInstallations.setLayoutManager(linearLayoutManager);
        InstallationsListAdapter adapter = new InstallationsListAdapter(context, list);
        rvInstallations.setAdapter(adapter);
        adapter.setListClickListener(this);
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

    @Override
    public void click(int position, int value) {
        Fragment fragment = new InstallationDetailFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }
}
