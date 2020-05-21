package com.coc.cocmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemsCategory_Response {

    @SerializedName("data")
    @Expose
    private List<ItemCategoryInfo> data;

    @SerializedName("found")
    @Expose
    private Boolean found;

    public List<ItemCategoryInfo> getData() {
        return data;
    }

    public void setData(List<ItemCategoryInfo> data) {
        this.data = data;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }
}