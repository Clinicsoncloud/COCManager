package com.coc.cocmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClinicModel {

    @SerializedName("location_id")
    @Expose
    private String locationid;

    @SerializedName("assigned_user_id")
    @Expose
    private String assigneduserid;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("gmail_id")
    @Expose
    private String gmailid;

    @SerializedName("actofit_id")
    @Expose
    private String actofit_id;

    @SerializedName("image_file")
    @Expose
    private String image_file;

    @SerializedName("app_version")
    @Expose
    private String app_version;

    @SerializedName("installed_by")
    @Expose
    private String installed_by;

    @SerializedName("gmail_password")
    @Expose
    private String gmail_password;

    @SerializedName("actofit_password")
    @Expose
    private String actofit_password;

    @SerializedName("installation_step")
    @Expose
    private String installation_step;

    @SerializedName("machine_operator_name")
    @Expose
    private String machine_operator_name;

    @SerializedName("machine_operator_mobile_number")
    @Expose
    private String machine_operator_mobile_number;

    @SerializedName("last_sync_date")
    @Expose
    private String last_sync_date;

    @SerializedName("actofit_end_date")
    @Expose
    private String actofit_end_date;

    @SerializedName("installation_date")
    @Expose
    private String installation_date;

    @SerializedName("license_expiry_date")
    @Expose
    private String license_expiry_date;

    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("is_trial_mode")
    @Expose
    private Boolean is_trial_mode;

    @SerializedName("created_by")
    @Expose
    private String createdby;
    @SerializedName("updated_by")
    @Expose
    private String updatedby;

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
    @SerializedName("total_tests_done")
    @Expose
    private String total_tests_done;

    @SerializedName("allowed_trial_tests")
    @Expose
    private String allowed_trial_tests;


    public String getLocationid() {
        return locationid;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }

    public String getAssigneduserid() {
        return assigneduserid;
    }

    public void setAssigneduserid(String assigneduserid) {
        this.assigneduserid = assigneduserid;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGmailid() {
        return gmailid;
    }

    public void setGmailid(String gmailid) {
        this.gmailid = gmailid;
    }

    public String getActofit_id() {
        return actofit_id;
    }

    public void setActofit_id(String actofit_id) {
        this.actofit_id = actofit_id;
    }

    public String getImage_file() {
        return image_file;
    }

    public void setImage_file(String image_file) {
        this.image_file = image_file;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getInstalled_by() {
        return installed_by;
    }

    public void setInstalled_by(String installed_by) {
        this.installed_by = installed_by;
    }

    public String getGmail_password() {
        return gmail_password;
    }

    public void setGmail_password(String gmail_password) {
        this.gmail_password = gmail_password;
    }

    public String getActofit_password() {
        return actofit_password;
    }

    public void setActofit_password(String actofit_password) {
        this.actofit_password = actofit_password;
    }

    public String getInstallation_step() {
        return installation_step;
    }

    public void setInstallation_step(String installation_step) {
        this.installation_step = installation_step;
    }

    public String getMachine_operator_name() {
        return machine_operator_name;
    }

    public void setMachine_operator_name(String machine_operator_name) {
        this.machine_operator_name = machine_operator_name;
    }

    public String getMachine_operator_mobile_number() {
        return machine_operator_mobile_number;
    }

    public void setMachine_operator_mobile_number(String machine_operator_mobile_number) {
        this.machine_operator_mobile_number = machine_operator_mobile_number;
    }

    public String getLast_sync_date() {
        return last_sync_date;
    }

    public void setLast_sync_date(String last_sync_date) {
        this.last_sync_date = last_sync_date;
    }

    public String getActofit_end_date() {
        return actofit_end_date;
    }

    public void setActofit_end_date(String actofit_end_date) {
        this.actofit_end_date = actofit_end_date;
    }

    public String getInstallation_date() {
        return installation_date;
    }

    public void setInstallation_date(String installation_date) {
        this.installation_date = installation_date;
    }

    public String getLicense_expiry_date() {
        return license_expiry_date;
    }

    public void setLicense_expiry_date(String license_expiry_date) {
        this.license_expiry_date = license_expiry_date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getIs_trial_mode() {
        return is_trial_mode;
    }

    public void setIs_trial_mode(Boolean is_trial_mode) {
        this.is_trial_mode = is_trial_mode;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
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

    public String getTotal_tests_done() {
        return total_tests_done;
    }

    public void setTotal_tests_done(String total_tests_done) {
        this.total_tests_done = total_tests_done;
    }

    public String getAllowed_trial_tests() {
        return allowed_trial_tests;
    }

    public void setAllowed_trial_tests(String allowed_trial_tests) {
        this.allowed_trial_tests = allowed_trial_tests;
    }
}
