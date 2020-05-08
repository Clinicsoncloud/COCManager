package com.coc.cocmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationModel {

    @SerializedName("data")
    @Expose
    private List<LocationInfo> data;

    @SerializedName("found")
    @Expose
    private Boolean found;

    public List<LocationInfo> getData() {
        return data;
    }

    public void setData(List<LocationInfo> data) {
        this.data = data;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }

    public class LocationInfo {
        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("status")
        @Expose
        private Boolean status;

        @SerializedName("updated_by")
        @Expose
        private String updatedby;

        @SerializedName("created_by")
        @Expose
        private String createdby;

        @SerializedName("deleted_by")
        @Expose
        private String deletedby;

        @SerializedName("created_at")
        @Expose
        private String createdat;

        @SerializedName("updated_at")
        @Expose
        private String updatedat;

        @SerializedName("deleted_at")
        @Expose
        private String deletedat;

        @SerializedName("id")
        @Expose
        private String id;

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

        public String getUpdatedby() {
            return updatedby;
        }

        public void setUpdatedby(String updatedby) {
            this.updatedby = updatedby;
        }

        public String getCreatedby() {
            return createdby;
        }

        public void setCreatedby(String createdby) {
            this.createdby = createdby;
        }

        public String getDeletedby() {
            return deletedby;
        }

        public void setDeletedby(String deletedby) {
            this.deletedby = deletedby;
        }

        public String getCreatedat() {
            return createdat;
        }

        public void setCreatedat(String createdat) {
            this.createdat = createdat;
        }

        public String getUpdatedat() {
            return updatedat;
        }

        public void setUpdatedat(String updatedat) {
            this.updatedat = updatedat;
        }

        public String getDeletedat() {
            return deletedat;
        }

        public void setDeletedat(String deletedat) {
            this.deletedat = deletedat;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
