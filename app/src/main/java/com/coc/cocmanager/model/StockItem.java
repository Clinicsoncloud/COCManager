package com.coc.cocmanager.model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class StockItem extends ExpandableGroup<StockSubItem> {
    public StockItem(String title, List<StockSubItem> items) {
        super(title, items);
    }
}
