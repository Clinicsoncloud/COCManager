package com.coc.cocmanager.adapter;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.coc.cocmanager.R;
import com.coc.cocmanager.model.StockItem;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class StockItemViewHolder extends GroupViewHolder {

    private TextView mTextView;
    private ImageView ivArrow;

    public StockItemViewHolder(View itemView) {
        super(itemView);

        mTextView = itemView.findViewById(R.id.text_title);
        ivArrow = itemView.findViewById(R.id.iv_arrow);
    }

    public void bind(StockItem stockItem){

        mTextView.setText(stockItem.getTitle());
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        ivArrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        ivArrow.setAnimation(rotate);
    }
}
