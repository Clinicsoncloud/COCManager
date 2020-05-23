package com.coc.cocmanager.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
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

        TextView tvClinicId;
        TextView tvClinicName;
        TextView tvClientName;
        TextView tvClinicLocation;
        LinearLayout cardViewInstallation;

        public ViewHolder(View itemView) {
            super(itemView);

            tvClinicId = itemView.findViewById(R.id.tv_clinic_id);
            tvClinicName = itemView.findViewById(R.id.tv_clinic_name);
            tvClientName = itemView.findViewById(R.id.tv_client_name);
            tvClinicLocation = itemView.findViewById(R.id.tv_clinic_location);
            cardViewInstallation = itemView.findViewById(R.id.ll_card_item);
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

        holder.tvClinicId.setText("Id :"+list.get(position).getId());
        holder.tvClinicName.setText(list.get(position).getName());
        holder.tvClientName.setText("Client :"+list.get(position).getUser().getFirstname());
        holder.tvClinicLocation.setText("Location :"+list.get(position).getLocation().getName());

        holder.cardViewInstallation.setOnClickListener(v -> {listClickListener.click(position,0);});
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
