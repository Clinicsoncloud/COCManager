package com.coc.cocmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateUserModel {

    @SerializedName("data")
    @Expose
    private CreateUserInfo data;

    @SerializedName("success")
    @Expose
    private Boolean success;

    public CreateUserInfo getData() {
        return data;
    }

    public void setData(CreateUserInfo data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
