package ua.nc.service.user;

import ua.nc.entity.User;
import ua.nc.entity.UserRow;

import java.util.List;

/**
 * Created by Pavel on 18.04.2016.
 */
public interface UserService {
    public List<UserRow> getUser(Integer itemPerPage, Integer pageNumber);

    public List<UserRow> getUser(Integer itemPerPage, Integer pageNumber, String orderBy,String pattern);

    public List<UserRow> getUser(Integer itemPerPage, Integer pageNumber, String pattern);

    public List<UserRow> getUser(Integer itemPerPage, Integer pageNumber, String orderBy, Boolean asc);

    public void changeStatus(String action, List<Integer> userIds);

    public Integer getSize(String pattern);

    public Integer getSize();

    User getUser(String email);

    User getUser(Integer id);

    User createUser(User user);

    void changePassword(User user, String password);

    User recoverPass(User user);

    boolean checkRole(User user, String roleName);

    void activateUsers(List<Integer> userIds);

    void deactivateUsers(List<Integer> userIds);

}
