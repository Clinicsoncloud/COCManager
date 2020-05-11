package com.coc.cocmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserData {

        @SerializedName("data")
        @Expose
        private List<User_Info> data;

        @SerializedName("found")
        @Expose
        private Boolean found;

        public List<UserData.User_Info> getData() {
            return data;
        }

        public void setData(List<User_Info> data) {
            this.data = data;
        }

        public Boolean getFound() {
            return found;
        }

        public void setFound(Boolean found) {
            this.found = found;
        }

        public class User_Info {

            @SerializedName("email")
            @Expose
            private String email;

            @SerializedName("phone")
            @Expose
            private String phone;

            @SerializedName("password")
            @Expose
            private String password;

            @SerializedName("username")
            @Expose
            private String username;

            @SerializedName("last_name")
            @Expose
            private String last_name;

            @SerializedName("first_name")
            @Expose
            private String first_name;

            @SerializedName("remember_token")
            @Expose
            private String remember_token;

            @SerializedName("email_verified_at")
            @Expose
            private String email_verified_at;

            @SerializedName("role_id")
            @Expose
            private String role_id;

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

            @SerializedName("role")
            @Expose
            private User_Role_Info role;

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

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getRemember_token() {
                return remember_token;
            }

            public void setRemember_token(String remember_token) {
                this.remember_token = remember_token;
            }

            public String getEmail_verified_at() {
                return email_verified_at;
            }

            public void setEmail_verified_at(String email_verified_at) {
                this.email_verified_at = email_verified_at;
            }

            public String getRole_id() {
                return role_id;
            }

            public void setRole_id(String role_id) {
                this.role_id = role_id;
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

            public User_Role_Info getRole() {
                return role;
            }

            public void setRole(User_Role_Info role) {
                this.role = role;
            }
        }
}

