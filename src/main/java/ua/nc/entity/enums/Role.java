package ua.nc.entity.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Pavel on 18.04.2016.
 */
public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_HR,
    ROLE_DEV,
    ROLE_BA,
    ROLE_STUDENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
