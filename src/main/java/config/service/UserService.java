package config.service;

import config.dao.UserRepository;
import config.entity.User;
import config.enumeration.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author marsel.maximov
 */


public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Nullable
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name);
        if (user == null) {
            return null;
        }

        Collection<GrantedAuthority> authorities = getAuthorities(user);
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }

    private Collection<GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRoles() != null) {
            for (UserRole userRole : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(userRole.name()));
            }
        }

        return authorities;
    }
}