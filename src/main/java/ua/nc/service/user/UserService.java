package ua.nc.service.user;

import ua.nc.entity.User;

/**
 * Created by Pavel on 18.04.2016.
 */
public interface UserService {
    User getUser(String email);

    User getUser(int id);

    User createUser(User user);

    void changePassword(User user, String password);

    User recoverPass(User user);

    boolean checkRole(User user, String roleName);
}
