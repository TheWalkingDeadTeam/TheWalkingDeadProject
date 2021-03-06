package ua.nc.service.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.nc.entity.Role;
import ua.nc.entity.User;
import ua.nc.service.UserDetailsImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pavel on 18.04.2016.
 */

public class UserDetailsServiceImpl implements UserDetailsService {
    private UserService userService = new UserServiceImpl();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }
        Set<GrantedAuthority> roles = new HashSet();
        for (Role role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        UserDetailsImpl userDetails = new UserDetailsImpl(user.getId(), user.getEmail(), user.getPassword(), user.getStatus(), roles);
        return userDetails;
    }


}
