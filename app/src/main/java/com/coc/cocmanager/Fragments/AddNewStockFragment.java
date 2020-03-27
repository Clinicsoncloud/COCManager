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

import com.coc.cocmanager.R;
import com.coc.cocmanager.adapter.StockListAdapter;
import com.coc.cocmanager.model.StockItem;
import com.coc.cocmanager.model.StockSubItem;

import java.util.ArrayList;

/**
 * created by ketan 25-3-2020
 */
public class AddNewStockFragment extends Fragment {

    //region variables

    //endregion

    public AddNewStockFragment() {
        // Required empty public constructor
    }

    public static AddNewStockFragment newInstance() {
        AddNewStockFragment fragment = new AddNewStockFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_add_new_stock, container, false);

        setupUI(rootView);
        initializeData();
        return rootView;
    }

    private void initializeData() { }

    private void setupUI(View rootView) { }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) { }

    @Override
    public void onAttach(Context context) { super.onAttach(context); }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
