package com.coc.cocmanager.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.content.Context;


import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coc.cocmanager.R;
import com.coc.cocmanager.adapter.InstallationsListAdapter;
import com.coc.cocmanager.adapter.PipelineListAdapter;
import com.coc.cocmanager.interfaces.ListClickListener;

import java.util.ArrayList;

/**
 * created by ketan 23-3-2020
 */
public class PipelineFragment extends Fragment implements ListClickListener {

    //region variables
    private RecyclerView rvPipeline;
    private ImageView ivAddNew;
    private Context context = getContext();

    //endregion

    //region methods

    public PipelineFragment() {
        // Required empty public constructor
    }

    public static PipelineFragment newInstance() {
        PipelineFragment fragment = new PipelineFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_pipeline, container, false);

        setupUI(rootView);
        setupEvents();
        initializeData();
        return rootView;
    }

    private void setupEvents() {
        ivAddNew.setOnClickListener(View->{ addNewForm(); });
    }

    private void addNewForm() {
        Fragment fragment = new AddNewFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }

    /**
     *
     */
    private void initializeData() {
        setListAdapter();
    }

    /**
     *
     */
    private void setListAdapter() {
        ArrayList list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rvPipeline.setLayoutManager(linearLayoutManager);
        PipelineListAdapter adapter = new PipelineListAdapter(context, list);
        rvPipeline.setAdapter(adapter);
        adapter.setListClickListener(this);
    }

    /**
     *
     * @param rootView
     */
    private void setupUI(View rootView) {
        rvPipeline = rootView.findViewById(R.id.rv_pipeline_list);
        ivAddNew = rootView.findViewById(R.id.iv_add_new);
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
        Fragment fragment = new PipelineDetailFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }

    //endregion


}
