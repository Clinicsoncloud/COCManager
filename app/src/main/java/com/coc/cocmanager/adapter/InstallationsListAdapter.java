package com.coc.cocmanager.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;


import com.coc.cocmanager.R;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.coc.cocmanager.interfaces.ListClickListener;
import com.coc.cocmanager.model.ClinicListModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class InstallationsListAdapter extends RecyclerView.Adapter<InstallationsListAdapter.ViewHolder> {

    private View itemView;
    private Context context;
    private ListClickListener listClickListener;
    private final List<ClinicListModel.ClinicListInfo> list;

    public InstallationsListAdapter(Context context, List<ClinicListModel.ClinicListInfo> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewInstallation;
        TextView tvClinicName;
        TextView tvClinicLocation;
        TextView tvClinicId;
        TextView tvClientName;

        public ViewHolder(View itemView) {
            super(itemView);
        }

    }

    public void setListClickListener(ListClickListener listClickListener) {
        this.listClickListener = listClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_installations_list_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

/*

        holder.tv_particulars.setText(list.get(position).getServiceName());
        holder.ivAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listClickListener.click(position,1);
            }
        });
*/

    }


    @Override
    public int getItemCount() {

        return list.size();
    }

}
