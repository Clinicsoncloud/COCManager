package com.coc.cocmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckAvailabilityModel {

    @SerializedName("data")
    @Expose
    public List<CheckAvailabilityInfo> data;

    @SerializedName("found")
    @Expose
    private Boolean found;

    public List<CheckAvailabilityInfo> getData() {
        return data;
    }

    public void setData(List<CheckAvailabilityInfo> data) {
        this.data = data;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }

    public class CheckAvailabilityInfo {

        @SerializedName("item_id")
        @Expose
        private String item_id;

        @SerializedName("item_count")
        @Expose
        private String item_count;

        @SerializedName("created_by")
        @Expose
        private String created_by;

        @SerializedName("updated_by")
        @Expose
        private String updated_by;

        @SerializedName("deleted_by")
        @Expose
        private String deleted_by;

        @SerializedName("created_at")
        @Expose
        private String created_at;

        @SerializedName("updated_at")
        @Expose
        private String updated_at;

        @SerializedName("deleted_at")
        @Expose
        private String deleted_at;

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("item")
        @Expose
        private ItemInfoModel item;

        @SerializedName("count_required")
        @Expose
        private String count_required;

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_count() {
            return item_count;
        }

        public void setItem_count(String item_count) {
            this.item_count = item_count;
        }

        public String getCreated_by() {
            return created_by;
        }

        public void setCreated_by(String created_by) {
            this.created_by = created_by;
        }

        public String getUpdated_by() {
            return updated_by;
        }

        public void setUpdated_by(String updated_by) {
            this.updated_by = updated_by;
        }

        public String getDeleted_by() {
            return deleted_by;
        }

        public void setDeleted_by(String deleted_by) {
            this.deleted_by = deleted_by;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getDeleted_at() {
            return deleted_at;
        }

        public void setDeleted_at(String deleted_at) {
            this.deleted_at = deleted_at;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public ItemInfoModel getItem() {
            return item;
        }

        public void setItem(ItemInfoModel item) {
            this.item = item;
        }

        public String getCount_required() {
            return count_required;
        }

        public void setCount_required(String count_required) {
            this.count_required = count_required;
        }
    }
}
