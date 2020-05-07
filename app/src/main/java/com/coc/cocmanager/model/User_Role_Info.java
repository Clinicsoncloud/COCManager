package com.coc.cocmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class User_Role_Info {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("guard_name")
    @Expose
    private String guard_name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("rolePermissions")
    @Expose
    private List<RolePermissionsInfo> rolePermissions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuard_name() {
        return guard_name;
    }

    public void setGuard_name(String guard_name) {
        this.guard_name = guard_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public List<RolePermissionsInfo> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<RolePermissionsInfo> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    private class RolePermissionsInfo {

        @SerializedName("role_id")
        @Expose
        private String roleid;

        @SerializedName("permission_id")
        @Expose
        private String permissionid;

        @SerializedName("permission_value")
        @Expose
        private String permissionValue;

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("permission")
        @Expose
        private PermissionData permission;

        public String getRoleid() {
            return roleid;
        }

        public void setRoleid(String roleid) {
            this.roleid = roleid;
        }

        public String getPermissionid() {
            return permissionid;
        }

        public void setPermissionid(String permissionid) {
            this.permissionid = permissionid;
        }

        public String getPermissionValue() {
            return permissionValue;
        }

        public void setPermissionValue(String permissionValue) {
            this.permissionValue = permissionValue;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public PermissionData getPermission() {
            return permission;
        }

        public void setPermission(PermissionData permission) {
            this.permission = permission;
        }
    }


    private class PermissionData {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("guard_name")
        @Expose
        private String guard_name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("created_at")
        @Expose
        private String created_at;
        @SerializedName("updated_at")
        @Expose
        private String updated_at;
        @SerializedName("id")
        @Expose
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGuard_name() {
            return guard_name;
        }

        public void setGuard_name(String guard_name) {
            this.guard_name = guard_name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
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
    }
}
