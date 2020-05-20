package com.coc.cocmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstallationSummaryModel {

    @SerializedName("data")
    @Expose
    private SummaryInfo data;

    @SerializedName("found")
    @Expose
    private Boolean found;

    public SummaryInfo getData() {
        return data;
    }

    public void setData(SummaryInfo data) {
        this.data = data;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }

    public class SummaryInfo {

        @SerializedName("clinic_in_installed_count")
        @Expose
        private String installedCount;

        @SerializedName("clinic_in_pipleline_count")
        @Expose
        private String pipelineCount;

        @SerializedName("clinic_in_transport_count")
        @Expose
        private String transportCount;

        public String getInstalledCount() {
            return installedCount;
        }

        public void setInstalledCount(String installedCount) {
            this.installedCount = installedCount;
        }

        public String getPipelineCount() {
            return pipelineCount;
        }

        public void setPipelineCount(String pipelineCount) {
            this.pipelineCount = pipelineCount;
        }

        public String getTransportCount() {
            return transportCount;
        }

        public void setTransportCount(String transportCount) {
            this.transportCount = transportCount;
        }
    }
}
