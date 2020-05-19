package com.coc.cocmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemInfoModel {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("inventory_count")
    @Expose
    private String inventory_count;

    @SerializedName("item_category_id")
    @Expose
    private String item_category_id;

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

    @SerializedName("itemCategory")
    @Expose
    private ItemInfoModel.ItemCategory itemCategory;

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getInventory_count() {
        return inventory_count;
    }

    public void setInventory_count(String inventory_count) {
        this.inventory_count = inventory_count;
    }

    public String getItem_category_id() {
        return item_category_id;
    }

    public void setItem_category_id(String item_category_id) {
        this.item_category_id = item_category_id;
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

    public class ItemCategory {
        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("status")
        @Expose
        private Boolean status;

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

        @SerializedName("clinic")
        @Expose
        private ClinicModel clinic;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
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

        public ClinicModel getClinic() {
            return clinic;
        }

        public void setClinic(ClinicModel clinic) {
            this.clinic = clinic;
        }
    }
}
