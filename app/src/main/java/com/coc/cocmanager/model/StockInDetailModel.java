package com.coc.cocmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockInDetailModel {

    @SerializedName("data")
    @Expose
    public StockInDetailModel.StockInInfo data;

    @SerializedName("found")
    @Expose
    private Boolean found;

    public StockInDetailModel.StockInInfo getData() {
        return data;
    }

    public void setData(StockInDetailModel.StockInInfo data) {
        this.data = data;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }

    public class StockInInfo {

        @SerializedName("clinic_id")
        @Expose
        private String clinic_id;

        @SerializedName("stock_type")
        @Expose
        private String stock_type;

        @SerializedName("description")
        @Expose
        private String description;

        @SerializedName("created_by")
        @Expose
        private String created_by;

        @SerializedName("created_at")
        @Expose
        private String created_at;

        @SerializedName("updated_at")
        @Expose
        private String updated_at;

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("stockItems")
        @Expose
        public List<StockInListModel.StockItems> stockItems;

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

        public List<StockInListModel.StockItems> getStockItems() {
            return stockItems;
        }

        public void setStockItems(List<StockInListModel.StockItems> stockItems) {
            this.stockItems = stockItems;
        }
    }

    public class StockItems {
        @SerializedName("item_id")
        @Expose
        private String item_id;

        @SerializedName("stock_id")
        @Expose
        private String stock_id;

        @SerializedName("quantity")
        @Expose
        private String quantity;

        @SerializedName("created_by")
        @Expose
        private String created_by;

        @SerializedName("created_at")
        @Expose
        private String created_at;

        @SerializedName("updated_at")
        @Expose
        private String updated_at;

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("item")
        @Expose
        private ItemInfoModel item;

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getStock_id() {
            return stock_id;
        }

        public void setStock_id(String stock_id) {
            this.stock_id = stock_id;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
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

        public ItemInfoModel getItem() {
            return item;
        }

        public void setItem(ItemInfoModel item) {
            this.item = item;
        }
    }
}
