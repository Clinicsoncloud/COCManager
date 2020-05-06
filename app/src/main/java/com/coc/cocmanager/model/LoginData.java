package com.coc.cocmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("data")
    @Expose
    private Data_Info data;

    @SerializedName("found")
    @Expose
    private Boolean found;

    public Data_Info getData() {
        return data;
    }

    public void setData(Data_Info data) {
        this.data = data;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }

    public class Data_Info {

        @SerializedName("valid")
        @Expose
        private String valid;

        public String getValid() {
            return valid;
        }

        public void setValid(String valid) {
            this.valid = valid;
        }
    }
}
