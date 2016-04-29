package ua.nc.service.user;

import ua.nc.entity.User;

import java.util.List;

/**
 * Created by Pavel on 18.04.2016.
 */
public interface UserService {
    User getUser(String email);

    User createUser(User user);

    User getUserById(int id);

    List<User> findUsersByName(String name);

    void delete(int id);

}
