package com.example.schoolmanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "GROUPS")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "group_nm")
    private String groupName;
    @Column(name = "active_flg")
    private Boolean activeFlag;
    @OneToMany(mappedBy="group")
    private List<UserMembership> userMembers = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy="group")
    private List<GroupPrivilege> groupPrivilege = new ArrayList<>();



}
