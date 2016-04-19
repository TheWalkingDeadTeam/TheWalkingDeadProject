package ua.nc.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.nc.db.UserDB;
import ua.nc.entity.User;

/**
 * Created by Pavel on 18.04.2016.
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(String email) {
        User user;
        try {
            UserDB userDB = new UserDB();
            user = userDB.findByEmail(email);
            user.setRoles(userDB.findRoleByEmail(email));
        } catch (Exception e) {
            System.out.println("DB exception"); //toDo log4j
            throw new UsernameNotFoundException("error", e);
        }
        return user;
    }

}
