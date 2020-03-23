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
import com.coc.cocmanager.adapter.InstallationsListAdapter;
import com.coc.cocmanager.adapter.TransportListAdapter;
import com.coc.cocmanager.interfaces.ListClickListener;

import java.util.ArrayList;

/**
 * created by ketan 23-3-2020
 */

public class TransportFragment extends Fragment implements ListClickListener {

    //region variables
    private RecyclerView rvTransport;
    private Context context = getContext();
    //endregion

    public TransportFragment() {
        // Required empty public constructor
    }

    public static TransportFragment newInstance() {
        TransportFragment fragment = new TransportFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_transport, container, false);

        setupUI(rootView);
        initializeData();
        return rootView;
    }

    private void initializeData() {
        setAdapter();
    }

    private void setAdapter() {
        ArrayList list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rvTransport.setLayoutManager(linearLayoutManager);
        TransportListAdapter adapter = new TransportListAdapter(context, list);
        rvTransport.setAdapter(adapter);
        adapter.setListClickListener(this);
    }

    private void setupUI(View rootView) {
        rvTransport = rootView.findViewById(R.id.rv_transport);
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
    public void click(int position, int value) {
        Fragment fragment = new TransportDetailFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }
}
