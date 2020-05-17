package com.coc.cocmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockUsingTypeModel {

    @SerializedName("data")
    @Expose
    private StockOutInfo data;

    @SerializedName("success")
    @Expose
    private Boolean success;

    public StockOutInfo getData() {
        return data;
    }

    public void setData(StockOutInfo data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public class StockOutInfo {

        @SerializedName("data")
        @Expose
        private StockOutData data;

        public StockOutData getData() {
            return data;
        }

        public void setData(StockOutData data) {
            this.data = data;
        }
    }

    private class StockOutData{

        @SerializedName("clinic_id")
        @Expose
        private String clinicId;
        @SerializedName("stock_type")
        @Expose
        private String stockType;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("id")
        @Expose
        private String id;

        public String getClinicId() {
            return clinicId;
        }

        public void setClinicId(String clinicId) {
            this.clinicId = clinicId;
        }

        public String getStockType() {
            return stockType;
        }

        public void setStockType(String stockType) {
            this.stockType = stockType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
