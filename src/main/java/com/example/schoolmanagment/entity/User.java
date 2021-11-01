package com.example.schoolmanagment.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@Table(name="Users")
@Data

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "userSequenceGenerator")
    @SequenceGenerator(name="userSequenceGenerator", sequenceName = "User_ID_SEQ", allocationSize = 1)
    @Column(name="user_id")
    private  Long userId;
    @Column(name="username")
    private String username;
    private String activeFlag;
    @Column(name="effectiveData")
    private LocalDate effectiveData;
    @Column(name="expiryDate")
    private LocalDate expiryDate;
    private boolean isAuthorized;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserMembership> userMemberships;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if(isAuthorized()) {
           authorities =userMemberships.stream().filter(m -> m.getActiveFlag()=='1')
                   .map(UserMembership::getGroup)
                   .map(Group::getRoles)
                   .flatMap(Collection::stream)
                   .map(this::mapRoleToAuthority)
                   .collect(Collectors.toList());
//           authorities.add(new SimpleGrantedAuthority())
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
