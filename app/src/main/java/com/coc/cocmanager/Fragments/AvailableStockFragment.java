package com.coc.cocmanager.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.coc.cocmanager.R;
import com.coc.cocmanager.adapter.StockListAdapter;
import com.coc.cocmanager.model.StockItem;
import com.coc.cocmanager.model.StockSubItem;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;

/**
 * created by ketan 25-3-2020
 */
public class AvailableStockFragment extends Fragment {

    //region variables
    private RecyclerView rvAvailableStock;
    //endregion

    public AvailableStockFragment() {
        // Required empty public constructor
    }

    public static AvailableStockFragment newInstance() {
        AvailableStockFragment fragment = new AvailableStockFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_available_stock, container, false);
        setupUI(rootView);
        setupEvents();
        initializeData();
        return rootView;
    }

    private void initializeData() {
        setStockListAdapter();
    }

    private void setStockListAdapter() {
        rvAvailableStock.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<StockItem> list = new ArrayList<>();
        ArrayList<StockSubItem> Items = new ArrayList<>();

        Items.add(new StockSubItem("demo item 1","demo item 1"));
        Items.add(new StockSubItem("demo item 2","demo item 2"));
        Items.add(new StockSubItem("demo item 3","demo item 3"));
        Items.add(new StockSubItem("demo item 4","demo item 4"));

        StockItem stockItem = new StockItem("Items",Items);
        list.add(stockItem);

        ArrayList<StockSubItem> AddOns = new ArrayList<>();

        AddOns.add(new StockSubItem("demo add on 1","demo add on 1"));
        AddOns.add(new StockSubItem("demo add on 2","demo add on 2"));
        AddOns.add(new StockSubItem("demo add on 3","demo add on 3"));
        AddOns.add(new StockSubItem("demo add on 4","demo add on 4"));

        StockItem StockItem = new StockItem("AddOns",AddOns);
        list.add(StockItem);

        ArrayList<StockSubItem> Consumables = new ArrayList<>();

        Consumables.add(new StockSubItem("consumable 1","consumable 1"));
        Consumables.add(new StockSubItem("consumable 2","consumable 2"));
        Consumables.add(new StockSubItem("consumable 3","consumable 3"));
        Consumables.add(new StockSubItem("consumable 4","consumable 4"));

        StockItem consumables = new StockItem("Consumables",AddOns);
        list.add(consumables);

        StockListAdapter adapter = new StockListAdapter(list);
        rvAvailableStock.setAdapter(adapter);
    }

    private void setupEvents() { }

    private void setupUI(View rootView) {
        rvAvailableStock = rootView.findViewById(R.id.rv_available_stock_list);
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

}
