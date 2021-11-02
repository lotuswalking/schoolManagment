package com.example.schoolmanagement.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "GROUPS")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "group_nm")
    private String groupName;
    @Column(name = "active_flg")
    private Boolean activeFlag;



}
