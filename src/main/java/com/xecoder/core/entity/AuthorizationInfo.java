package com.xecoder.core.entity;

import java.util.Collection;

/**
 * Created by vincent on 1/8/17.
 */
public class AuthorizationInfo {
    Collection<String> hasPermissions = null;
    Collection<String> hasRoles = null;

    public Collection<String> getHasPermissions() {
        return hasPermissions;
    }

    public void setHasPermissions(Collection<String> hasPermissions) {
        this.hasPermissions = hasPermissions;
    }

    public Collection<String> getHasRoles() {
        return hasRoles;
    }

    public void setHasRoles(Collection<String> hasRoles) {
        this.hasRoles = hasRoles;
    }
}
