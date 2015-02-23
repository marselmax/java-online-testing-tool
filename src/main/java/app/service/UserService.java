package app.service;

import app.dao.UserRepository;
import app.entity.Role;
import app.entity.User;
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
        final User user = userRepository.findByName(name);
        if (user == null) {
            return null;
        }

        final Collection<GrantedAuthority> authorities = getAuthorities(user);
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }

    private Collection<GrantedAuthority> getAuthorities(User user) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRoles() != null) {
            for (final Role role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role.getValue().name()));
            }
        }

        return authorities;
    }
}