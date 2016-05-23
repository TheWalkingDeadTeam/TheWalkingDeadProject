package ua.nc.dao.enums;

/**
 * Created by Pavel on 23.05.2016.
 */
public enum UserStatus {
    Active,
    Inactive;


    public static boolean contains(String string) {
        for (UserStatus userStatus : UserStatus.values()) {
            if (userStatus.name().equals(string))
                return true;
        }
        return false;
    }
}
