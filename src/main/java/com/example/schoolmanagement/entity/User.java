package com.example.schoolmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private  Long userId;
    @Column(name="username")
    private String username;
    private boolean activeFlag;
    @Column(name="effectiveData")
    private LocalDate effectiveData;
    @Column(name="expiryDate")
    private LocalDate expiryDate;

    private boolean isAuthorized;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<UserMembership> userMemberships = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name="USER_GROUP",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="group_id")
    )
    private List<Group> group;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "role_id")
    private List<Role> roles = new ArrayList<>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if(isAuthorized()) {
            authorities = roles.stream()
                    .map(this::mapRoleToAuthority)
                    .collect(Collectors.toList());
        }
        return authorities;
    }
    private GrantedAuthority mapRoleToAuthority(Role role) {
        return new SimpleGrantedAuthority(role.getRoleName());
    }

    @Override
    public String getPassword() {
        return "123456";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
