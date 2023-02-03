package com.codecool.dadsinventory.security;

public enum ApplicationUserPermission {
    DAD_READ_DETAILS("dad:readDetails"),
    MOM_READ_PRIVACY_PAGE("mom:readPrivacyPage");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
