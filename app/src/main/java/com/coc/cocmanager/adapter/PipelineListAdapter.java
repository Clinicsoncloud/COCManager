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
 * created by ketan 11-5-2020
 */

public class PipelineListAdapter extends RecyclerView.Adapter<PipelineListAdapter.ViewHolder> {

    private View itemView;
    private Context context;
    private ListClickListener listClickListener;
    private final List<ClinicListModel.ClinicListInfo> list;

    public PipelineListAdapter(Context context, List<ClinicListModel.ClinicListInfo> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvClinicId;
        TextView tvClinicName;
        LinearLayout cardviewPipeline;

        public ViewHolder(View itemView) {
            super(itemView);

            tvClinicId = itemView.findViewById(R.id.tv_clinic_id);
            cardviewPipeline =itemView.findViewById(R.id.cardview_pipeline);
            tvClinicName = itemView.findViewById(R.id.tv_clinic_name_pipeline);
        }
    }

    public void setListClickListener(ListClickListener listClickListener) {
        this.listClickListener = listClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_pipeline_list_item, parent, false);
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
        holder.cardviewPipeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listClickListener.click(position, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
