package com.coc.cocmanager.fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;


import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.adapter.PipelineListAdapter;
import com.coc.cocmanager.interfaces.ListClickListener;
import com.coc.cocmanager.model.ClinicListModel;
import com.coc.cocmanager.model.LoginData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
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

    /**
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initializeData() {
        getPipelineList();
    }

    private void addNewForm() {
        Fragment fragment = new AddToPipelineFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getPipelineList() {
        try {
            if (Utils.isOnline(getContext())) {
                Map<String ,String> params = new HashMap<>();
                params.put(Constants.Fields.INSTALLATION_STEP,"Pipeline");

                Map<String, String> headerParams = new HashMap<>();

                HttpService.accessWebServices(
                        getContext(), ApiUtils.CLINIC_LIST,
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
                ClinicListModel clinicData = (ClinicListModel) Utils.parseResponse(response,ClinicListModel.class);
                if(clinicData.getFound()){
                    //TODO AFTER SUCCESS
                    setPipelineListAdapter(clinicData.getData());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (status.equals("error")) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setPipelineListAdapter(List<ClinicListModel.ClinicListInfo> list) {
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
        ivAddNew = rootView.findViewById(R.id.iv_add_new);
        rvPipeline = rootView.findViewById(R.id.rv_pipeline_list);
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
        Bundle args = new Bundle();
        args.putString("position",""+position);
        fragment.setArguments(args);

        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }

    //endregion


}
