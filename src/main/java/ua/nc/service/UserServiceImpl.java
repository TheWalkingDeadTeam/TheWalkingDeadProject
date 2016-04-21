package ua.nc.service;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.nc.db.UserDB;
import ua.nc.entity.User;
import ua.nc.entity.enums.Role;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pavel on 18.04.2016.
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(String email) {
        User user = null;
        try {
            UserDB userDB = new UserDB();
            user = userDB.findByEmail(email);
            user.setRoles(userDB.findRoleByEmail(email));
        } catch (Exception e) {
            System.out.println("DB exception"); //toDo log4j
        }
        return user;
    }

    @Override
    public User createUser(User user) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        user.setPassword(encoder.encodePassword(user.getPassword(), null));
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_STUDENT);
        user.setRoles(roles);
        try {
            UserDB userDB = new UserDB();
            userDB.createUser(user);
            userDB.createRoleToUser(userDB.findByEmail(user.getEmail()), Role.ROLE_STUDENT); //toDo Kill this
            return user;
        } catch (Exception e) {
            System.out.println("DB exception"); //toDo log4j
            return null;
        }
    }
}
