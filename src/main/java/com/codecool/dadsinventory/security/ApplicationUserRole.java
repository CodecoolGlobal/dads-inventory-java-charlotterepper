package com.codecool.dadsinventory.security;

import java.util.Set;

import static com.codecool.dadsinventory.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    DAD(Set.of(DAD_READ_DETAILS)),
    MOM(Set.of(MOM_READ_PRIVACY_PAGE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
