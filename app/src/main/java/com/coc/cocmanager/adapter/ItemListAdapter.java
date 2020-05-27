package com.coc.cocmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.coc.cocmanager.R;
import com.coc.cocmanager.interfaces.ListClickListener;
import com.coc.cocmanager.model.Items_Response;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private View itemView;
    private Context context;
    private ListClickListener listClickListener;
    private final List<Items_Response.Items_Data> list;

    public ItemListAdapter(Context context, List<Items_Response.Items_Data> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCount;
        TextView tvItemName;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCount = itemView.findViewById(R.id.tv_count);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
        }

    }

    public void setListClickListener(ListClickListener listClickListener) {
        this.listClickListener = listClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list_item_clinic_name, parent, false);
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

        holder.tvItemName.setText(list.get(position).getName());
        holder.tvCount.setText(list.get(position).getInventory_count());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
