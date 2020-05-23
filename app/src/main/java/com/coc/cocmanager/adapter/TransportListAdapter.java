package com.coc.cocmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.coc.cocmanager.R;
import com.coc.cocmanager.interfaces.ListClickListener;
import com.coc.cocmanager.model.ClinicListModel;

import java.util.List;

import butterknife.BindView;

/**
 *created by ketan 12-5-2020
 */
public class TransportListAdapter extends RecyclerView.Adapter<TransportListAdapter.ViewHolder> {

    private Context context;
    private View itemView;
    private ListClickListener listClickListener;
    private final List<ClinicListModel.ClinicListInfo> list;

    public TransportListAdapter(Context context, List<ClinicListModel.ClinicListInfo> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvClinicId;
        TextView tvClientName;
        TextView tvClinicName;
        LinearLayout cardviewTransport;

        public ViewHolder(View itemView) {
            super(itemView);

            tvClinicId = itemView.findViewById(R.id.tv_clinic_id);
            tvClientName = itemView.findViewById(R.id.tv_client_name);
            tvClinicName = itemView.findViewById(R.id.tv_clinic_name);
            cardviewTransport = itemView.findViewById(R.id.cardview_transport);
        }
    }

    public void setListClickListener(ListClickListener listClickListener) {
        this.listClickListener = listClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_transport_list_item, parent, false);
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

        holder.tvClinicName.setText(list.get(position).getName());
        holder.tvClinicId.setText("Id :"+list.get(position).getId());
        holder.tvClientName.setText("Client :"+list.get(position).getUser().getFirstname());

        holder.cardviewTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listClickListener.click(position,0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
