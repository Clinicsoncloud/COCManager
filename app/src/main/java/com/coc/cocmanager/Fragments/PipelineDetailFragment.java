package com.coc.cocmanager.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.content.Context;
import android.app.DatePickerDialog;
import androidx.fragment.app.Fragment;

import android.view.View;
import java.util.Calendar;
import com.coc.cocmanager.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import android.widget.Toast;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import android.widget.ImageView;
import android.widget.DatePicker;
import android.view.LayoutInflater;

/**
 * created by ketan 23-3-2020
 */
public class PipelineDetailFragment extends Fragment {

    //region variables
    private TextView tvActofitExpiry;
    private int mYear,mMonth,mDay;

    private ImageView ivMinus;
    private ImageView ivPlus;
    private TextView tvQty;

    private int count = 0;

    private ExpandableRelativeLayout expandableConsumables;
    private Button btnConsumables;
    //endregion

    public static PipelineDetailFragment newInstance(String param1, String param2) {
        PipelineDetailFragment fragment = new PipelineDetailFragment();
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
        View rootView = inflater.inflate(R.layout.layout_pipeline_detail, container, false);
        ButterKnife.bind(this,rootView);

        setupUI(rootView);
        setupEvents();

        return rootView;
    }

    private void setupEvents() {
        tvActofitExpiry.setOnClickListener(v -> {
            openCalender();
        });

        btnConsumables.setOnClickListener(v -> {
            expandableConsumables.toggle();
        });

        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count >= 0) {
                    count = count + 1;
                    tvQty.setText(""+count);
                }
            }
        });


        ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Can't minus right now", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openCalender() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        tvActofitExpiry.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void setupUI(View rootView) {
        tvActofitExpiry = rootView.findViewById(R.id.tv_actofit_expiry);
        btnConsumables = rootView.findViewById(R.id.btn_consumables);
        expandableConsumables = rootView.findViewById(R.id.expandable_consumables);
        ivMinus = rootView.findViewById(R.id.iv_minus);
        ivPlus = rootView.findViewById(R.id.iv_plus);
        tvQty = rootView.findViewById(R.id.tv_count);
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
}
