package com.example.schoolmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "username")
    private String username;
    private boolean activeFlag;
    @Column(name = "effectiveData")
    private LocalDate effectiveData;
    @Column(name = "expiryDate")
    private LocalDate expiryDate;

    private boolean isAuthorized;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<UserMembership> userMemberships = new ArrayList<>();


    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (isAuthorized()) {
            try {

                List<Group> groups =  userMemberships.stream().filter(m -> m.getActiveFlag()==true)
                        .map(UserMembership::getGroup).collect(Collectors.toList());
                authorities = userMemberships.stream().filter(m -> m.getActiveFlag()==true)
                        .map(UserMembership::getGroup)
                        .map(Group::getGroupPrivilege)
                        .flatMap(Collection::stream).filter(gp -> gp.getActiveFlag()==true)
                        .map(GroupPrivilege::getRole)
//                    .flatMap(Collection::stream)
                        .map(this::mapRoleToAuthority)
                        .collect(Collectors.toList());
            } catch (Exception e) {
                throw e;
            }


        }
        return authorities;
    }

    private GrantedAuthority mapRoleToAuthority(Role role) {

        return new SimpleGrantedAuthority(role.getRoleName());
    }

    @Override
    public String getPassword() {
        return "xsw2!QAZzaq1@WSX";
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
