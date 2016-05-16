package ua.nc.service.user;

import ua.nc.entity.Role;
import ua.nc.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    void activateUsers(List<Integer> userIds);

    void deactivateUsers(List<Integer> userIds);

    void changeRoles(String email, Set<Role> roles);
}
