package com.ilililissue.www.domain.manager;

public enum ManagerRole {
    LV1,
    LV2,
    LV3,
    MASTER,
    ;

    public boolean isOverAuthorized(ManagerRole role) {
        return this.ordinal() >= role.ordinal();
    }

}
