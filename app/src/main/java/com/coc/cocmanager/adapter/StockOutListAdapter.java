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
import com.coc.cocmanager.model.StockInListModel;
import com.coc.cocmanager.services.DateService;

import java.util.List;

public class StockOutListAdapter extends RecyclerView.Adapter<StockOutListAdapter.ViewHolder> {

    private View itemView;
    private Context context;
    private ListClickListener listClickListener;
    private final List<StockInListModel.StockInInfo> list;

    public StockOutListAdapter(Context context, List<StockInListModel.StockInInfo> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;
        TextView tvItemCount;
        TextView tvDescription;
//        CardView cardViewStockListItem;
        LinearLayout cardViewStockListItem;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_date);
            tvItemCount = itemView.findViewById(R.id.tv_item_count);
            tvDescription = itemView.findViewById(R.id.tv_descreption);
            cardViewStockListItem = itemView.findViewById(R.id.ll_item);
        }
    }

    public void setListClickListener(ListClickListener listClickListener) {
        this.listClickListener = listClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_updated_design_list_item, parent, false);
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

        holder.tvDescription.setText(list.get(position).getDescription());
        holder.tvDate.setText(DateService.formatDateFromString(list.get(position).getCreated_at()));
        holder.cardViewStockListItem.setOnClickListener(v -> {
            listClickListener.click(position,0);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
