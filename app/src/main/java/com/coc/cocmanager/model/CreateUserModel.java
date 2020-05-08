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

    private class CreateUserInfo {
        @SerializedName("email")
        @Expose
        private String email;

        @SerializedName("phone")
        @Expose
        private String phone;

        @SerializedName("gender")
        @Expose
        private String gender;

        @SerializedName("status")
        @Expose
        private Boolean status;

        @SerializedName("password")
        @Expose
        private String password;

        @SerializedName("username")
        @Expose
        private String username;

        @SerializedName("last_name")
        @Expose
        private String lastname;

        @SerializedName("first_name")
        @Expose
        private String firstname;

        @SerializedName("remember_token")
        @Expose
        private String remembertoken;

        @SerializedName("email_verified_at")
        @Expose
        private String emailverifiedat;

        @SerializedName("role_id")
        @Expose
        private String roleid;

        @SerializedName("user_type")
        @Expose
        private String usertype;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getRemembertoken() {
            return remembertoken;
        }

        public void setRemembertoken(String remembertoken) {
            this.remembertoken = remembertoken;
        }

        public String getEmailverifiedat() {
            return emailverifiedat;
        }

        public void setEmailverifiedat(String emailverifiedat) {
            this.emailverifiedat = emailverifiedat;
        }

        public String getRoleid() {
            return roleid;
        }

        public void setRoleid(String roleid) {
            this.roleid = roleid;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
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
    }
}
