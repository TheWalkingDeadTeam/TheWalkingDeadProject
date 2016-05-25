package ua.nc.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ua.nc.dao.enums.UserStatus;

import java.util.Collection;

/**
 * Created by Pavel on 28.04.2016.
 */
public class UserDetailsImpl extends User {
    private Integer id;
    private UserStatus status;

    public UserDetailsImpl(Integer id, String username, String password, UserStatus status,  Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.status = status;
    }

    public UserDetailsImpl(Integer id, String username, String password, UserStatus status, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;  
    }

    public UserStatus getStatus() {
        return status;
    }
}
