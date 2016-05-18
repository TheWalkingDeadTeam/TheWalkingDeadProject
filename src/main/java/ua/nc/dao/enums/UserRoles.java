package ua.nc.dao.enums;

/**
 * Created by Pavel on 28.04.2016.
 */
public enum UserRoles {
    ROLE_ADMIN,
    ROLE_HR,
    ROLE_DEV,
    ROLE_BA,
    ROLE_STUDENT, UserRoles;

    public static boolean contains(String string) {
        for (UserRoles userRole : UserRoles.values()) {
            if (userRole.name().equals(string))
                return true;
        }
        return false;
    }
}
