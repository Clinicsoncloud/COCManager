package com.coc.cocmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockModel {

    @SerializedName("data")
    @Expose
    public DataInfo data;

    @SerializedName("success")
    @Expose
    private Boolean found;

    public DataInfo getData() {
        return data;
    }

    public void setData(DataInfo data) {
        this.data = data;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }

    private class DataInfo {

        @SerializedName("clinic_id")
        @Expose
        public String clinic_id;

        @SerializedName("stock_type")
        @Expose
        public String stock_type;

        @SerializedName("description")
        @Expose
        public String description;

        @SerializedName("created_by")
        @Expose
        public String created_by;

        @SerializedName("created_at")
        @Expose
        public String created_at;

        @SerializedName("updated_at")
        @Expose
        public String updated_at;

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("is_approved")
        @Expose
        public String is_approved;

        public String getClinic_id() {
            return clinic_id;
        }

        public void setClinic_id(String clinic_id) {
            this.clinic_id = clinic_id;
        }

        public String getStock_type() {
            return stock_type;
        }

        public void setStock_type(String stock_type) {
            this.stock_type = stock_type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreated_by() {
            return created_by;
        }

        public void setCreated_by(String created_by) {
            this.created_by = created_by;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIs_approved() {
            return is_approved;
        }

        public void setIs_approved(String is_approved) {
            this.is_approved = is_approved;
        }
    }
}
