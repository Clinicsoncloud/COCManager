package com.coc.cocmanager.adapter;

import android.view.View;
import android.widget.TextView;

import com.coc.cocmanager.R;
import com.coc.cocmanager.model.StockSubItem;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class StockSubItemViewHolder extends ChildViewHolder {

    private TextView mTextView;
    private TextView mItemCount;

    public StockSubItemViewHolder(View itemView) {
        super(itemView);

        mTextView = itemView.findViewById(R.id.text_item_name);
        mItemCount = itemView.findViewById(R.id.text_item_name);
    }

    public void bind(StockSubItem stockSubItem){
        mTextView.setText(stockSubItem.subItemName);
        mItemCount.setText(stockSubItem.subItemCount);
    }
}
