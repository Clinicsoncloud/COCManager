package com.coc.cocmanager.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coc.cocmanager.R;
import com.coc.cocmanager.adapter.AddOnListAdapter;
import com.coc.cocmanager.adapter.ConsumableListAdapter;
import com.coc.cocmanager.adapter.InstallationsListAdapter;
import com.coc.cocmanager.adapter.ItemListAdapter;
import com.coc.cocmanager.interfaces.ListClickListener;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by ketan 25-3-2020
 */
public class StockInFragment extends Fragment implements ListClickListener, View.OnClickListener {

    //region variables

    @BindView(R.id.tv_start_date)
    TextView tvStartDate;
    @BindView(R.id.tv_end_date)
    TextView tvEndDate;
    @BindView(R.id.ll_filter)
    LinearLayout llFilter;
    @BindView(R.id.rv_item_list)
    RecyclerView rvItemList;
    @BindView(R.id.ll_items)
    LinearLayout llItems;
    @BindView(R.id.rv_addon_list)
    RecyclerView rvAddonList;
    @BindView(R.id.ll_add_ons)
    LinearLayout llAddOns;
    @BindView(R.id.rv_consumables_list)
    RecyclerView rvConsumablesList;
    @BindView(R.id.ll_consumables_ons)
    LinearLayout llConsumablesOns;
    @BindView(R.id.iv_add_stock)
    ImageView ivAddStock;
    private int mYear;
    private int mMonth;
    private int mDay;

    //endregion

    public StockInFragment() {
        // Required empty public constructor
    }

    public static StockInFragment newInstance() {
        StockInFragment fragment = new StockInFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_stock_in, container, false);
        ButterKnife.bind(this,rootView);

        setupEvents();
        initializeData();
        return rootView;
    }

    private void setupEvents() {
        tvStartDate.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);
        ivAddStock.setOnClickListener(this);
    }

    private void initializeData() {
        setItemListAdapter();
        setAddOnListAdapter();
        setConsumableListAdapter();
    }

    /**
     *
     */
    private void setConsumableListAdapter() {
        ArrayList list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvConsumablesList.setLayoutManager(linearLayoutManager);
        ConsumableListAdapter adapter = new ConsumableListAdapter(getContext(), list);
        rvConsumablesList.setAdapter(adapter);
        adapter.setListClickListener(this);
    }

    /**
     *
     */
    private void setAddOnListAdapter() {
        ArrayList list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvAddonList.setLayoutManager(linearLayoutManager);
        AddOnListAdapter adapter = new AddOnListAdapter(getContext(), list);
        rvAddonList.setAdapter(adapter);
        adapter.setListClickListener(this);
    }

    /**
     *
     */
    private void setItemListAdapter() {
        ArrayList list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvItemList.setLayoutManager(linearLayoutManager);
        ItemListAdapter adapter = new ItemListAdapter(getContext(), list);
        rvItemList.setAdapter(adapter);
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
    public void click(int position, int value) { }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_start_date:
                openCalender(tvStartDate);
                break;

            case R.id.tv_end_date:
                openCalender(tvEndDate);
                break;

            case R.id.iv_add_stock:
                openAddStock();
                break;
        }
    }

    /**
     *
     */
    private void openAddStock() {
        Fragment fragment = new AddNewStockFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }

    /**
     *
     * @param textView
     */
    private void openCalender(TextView textView) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        textView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
