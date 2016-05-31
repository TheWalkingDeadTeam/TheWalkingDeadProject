package ua.nc.service.user;

import ua.nc.entity.Role;
import ua.nc.entity.User;
import ua.nc.entity.UserRow;

import java.util.List;
import java.util.Set;

/**
 * Created by Pavel on 18.04.2016.
 */
public interface UserService {
    List<UserRow> getUser(Integer itemPerPage, Integer pageNumber);

    List<UserRow> getUser(Integer itemPerPage, Integer pageNumber, String orderBy, String pattern);

    List<UserRow> getUser(Integer itemPerPage, Integer pageNumber, String pattern);

    List<UserRow> getUser(Integer itemPerPage, Integer pageNumber, String orderBy, Boolean asc);

    void changeStatus(String action, List<Integer> userIds);

    Integer getSize(String pattern);

    Integer getSize();

    User findUserByEmail(String email);

    User findUserById(Integer id);

    User createUser(User user);

    void changePassword(User user, String password);

    void recoverPass(User user);

    boolean checkRole(User user, String roleName);

    void activateUsers(List<Integer> userIds);

    void deactivateUsers(List<Integer> userIds);

    void changeRoles(String email, Set<Role> roles);
}
