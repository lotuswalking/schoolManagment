package com.example.schoolmanagment.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ROLES")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "rolsSequenceGenerator")
    @SequenceGenerator(name="rolsSequenceGenerator", sequenceName = "ROLSP_ID_SEQ", allocationSize = 1)
    @Column(name = "role_id")
    private Integer roleId;
    @Column(name = "role_nm")
    private String roleName;

}
