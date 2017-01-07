package com.xecoder.core.entity;

import com.xecoder.config.BaseEntity;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Table(name = "security_role")
public class Role  extends BaseEntity implements Serializable {
    private Long id;

    private String description;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Transient
    private List<RolePermission> rolePermissions;

    public List<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    public String getName() {
        return name;
    }

}