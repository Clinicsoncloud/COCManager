package com.coc.cocmanager.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.content.Context;

import android.view.View;
import com.coc.cocmanager.R;
import com.coc.cocmanager.adapter.ItemListAdapter;
import com.coc.cocmanager.interfaces.ListClickListener;
import com.google.android.material.textfield.TextInputEditText;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

/**
 * created by ketan 27-3-2020
 */
public class CheckAvailabilityFragment extends Fragment implements ListClickListener {

    //region variables

    private TextInputEditText edtKioskNumbers;
    private Button btnSearch;
    private RecyclerView rvAvailableList;

    //endregion

    public CheckAvailabilityFragment() {
        // Required empty public constructor
    }

    public static CheckAvailabilityFragment newInstance(String param1, String param2) {
        CheckAvailabilityFragment fragment = new CheckAvailabilityFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_check_availability, container, false);

        setupUI(rootView);
        initializeData();
        return rootView;
    }

    private void initializeData() {
//        setAvailableListAdapter();
    }

    private void setAvailableListAdapter() {
        ArrayList list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvAvailableList.setLayoutManager(linearLayoutManager);
        ItemListAdapter adapter = new ItemListAdapter(getContext(), list);
        rvAvailableList.setAdapter(adapter);
        adapter.setListClickListener(this);
    }

    private void setupUI(View rootView) {
        edtKioskNumbers = rootView.findViewById(R.id.edt_kiosk_no);
        btnSearch = rootView.findViewById(R.id.btn_search);
        rvAvailableList = rootView.findViewById(R.id.rv_availabel_list);
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
}
