package ua.nc.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.nc.entity.User;

/**
 * Created by Pavel on 18.04.2016.
 */

public class UserDetailsServiceImpl implements UserDetailsService {
    private UserService userService = new UserServiceImpl();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUser(email);
        UserDetails userDetails = new org.springframework.security.core.userdetails
                .User(user.getEmail(), user.getPassword(), user.getRoles());
        return userDetails;
    }
}
