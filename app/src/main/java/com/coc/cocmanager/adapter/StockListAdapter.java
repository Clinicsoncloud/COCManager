package com.coc.cocmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coc.cocmanager.R;
import com.coc.cocmanager.model.StockItem;
import com.coc.cocmanager.model.StockSubItem;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class StockListAdapter extends ExpandableRecyclerViewAdapter<StockItemViewHolder,StockSubItemViewHolder> {
    public StockListAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public StockItemViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_stock_items,parent,false);
        return new StockItemViewHolder(v);
    }

    @Override
    public StockSubItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_stock_sub_item,parent,false);
        return new StockSubItemViewHolder(v);
    }

    @Override
    public void onBindChildViewHolder(StockSubItemViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final StockSubItem stockSubItem = (StockSubItem) group.getItems().get(childIndex);
        holder.bind(stockSubItem);
    }

    @Override
    public void onBindGroupViewHolder(StockItemViewHolder holder, int flatPosition, ExpandableGroup group) {
        final StockItem stockItem = (StockItem) group;
        holder.bind(stockItem);
    }
}
