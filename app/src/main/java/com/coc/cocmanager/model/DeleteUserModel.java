package com.coc.cocmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeleteUserModel {

    @SerializedName("data")
    @Expose
    private DeleteInfo data;

    @SerializedName("success")
    @Expose
    private Boolean success;

    public DeleteInfo getData() {
        return data;
    }

    public void setData(DeleteInfo data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public class DeleteInfo{

        @SerializedName("generatedMaps")
        @Expose
        private List<GeneratedMaps> generatedMaps;

        @SerializedName("raw")
        @Expose
        private List<Raw> raw;

         @SerializedName("affected")
        @Expose
        private Integer affected;
    }

    public class GeneratedMaps {

    }
    public class Raw{

    }

}
