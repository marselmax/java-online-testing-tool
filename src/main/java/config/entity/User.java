package config.entity;

import config.enumeration.UserRole;

import javax.persistence.*;
import java.util.Collection;
/**
 * @author marsel.maximov
 */

@Entity
public class User {

    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    @CollectionTable
    private Collection<UserRole> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Collection<UserRole> roles) {
        this.roles = roles;
    }
}
