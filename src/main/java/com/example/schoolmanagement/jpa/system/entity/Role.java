package com.example.schoolmanagement.jpa.system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ROLES")
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "role_nm")
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }


}
