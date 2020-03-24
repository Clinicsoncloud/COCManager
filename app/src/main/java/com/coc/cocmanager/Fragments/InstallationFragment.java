package com.coc.cocmanager.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coc.cocmanager.R;
import com.coc.cocmanager.adapter.InstallationsListAdapter;
import com.coc.cocmanager.interfaces.ListClickListener;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;

/**
 * created by ketan 23-3-2020
 */
public class InstallationFragment extends Fragment implements ListClickListener, View.OnClickListener {


    //region variables
    private RecyclerView rvInstallations;
    private Context context = getContext();
    private int mYear, mMonth, mDay;

    final Calendar c = Calendar.getInstance();
    private TextView tvStartDate;
    private TextView tvEndDate;
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
        setupEvents();
        initalizeData();
        return rootView;
    }

    private void setupEvents() {
        tvStartDate.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);
    }

    private void initalizeData() {
        setListAdapter();
    }

    private void openCalender(TextView textView) {
        // Get Current Date
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        textView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void setupUI(View rootView) {
        rvInstallations = rootView.findViewById(R.id.rv_installations);
        tvStartDate = rootView.findViewById(R.id.tv_start_date);
        tvEndDate = rootView.findViewById(R.id.tv_end_date);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_start_date:
                openCalender(tvStartDate);
                break;

            case R.id.tv_end_date:
                openCalender(tvEndDate);
                break;
        }
    }
}
