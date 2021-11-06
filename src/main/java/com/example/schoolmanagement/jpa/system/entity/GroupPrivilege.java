package com.example.schoolmanagement.jpa.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "GroupPrivilege")
public class GroupPrivilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="GroupPrivilege_id")
    private Long GroupPrivilegeId;
    @Column(name ="active_flg")
    private Boolean activeFlag;
//    @Column(name ="Group_id")
    @ManyToOne @JoinColumn(name = "group_id")
    private Group group;
//    @Column(name ="role_id")
    @ManyToOne @JoinColumn(name = "role_id")
    private Role role;

    private LocalDate effectiveDate;
    private LocalDate expiryDate;

}