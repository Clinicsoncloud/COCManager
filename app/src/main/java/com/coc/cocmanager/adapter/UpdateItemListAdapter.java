package com.coc.cocmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.coc.cocmanager.R;
import com.coc.cocmanager.interfaces.ListClickListener;
import com.coc.cocmanager.model.StockInListModel;

import java.util.List;

public class UpdateItemListAdapter extends RecyclerView.Adapter<UpdateItemListAdapter.ViewHolder> {

    private View itemView;
    private Context context;
    private ListClickListener listClickListener;
    private final List<StockInListModel.StockItems> list;

    public UpdateItemListAdapter(Context context, List<StockInListModel.StockItems> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemName;
        EditText tvQuantity;
        TextView tvCategoryName;

        public ViewHolder(View itemView) {
            super(itemView);

            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
        }
    }

    public void setListClickListener(ListClickListener listClickListener) {
        this.listClickListener = listClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_stock_in_details_list_item, parent, false);
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

        holder.tvQuantity.setText(list.get(position).getQuantity());
        holder.tvItemName.setText(list.get(position).getItem().getName());
        holder.tvCategoryName.setText(list.get(position).getItem().getItemCategory().getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
