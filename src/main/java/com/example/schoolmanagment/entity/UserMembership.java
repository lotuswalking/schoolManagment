package com.example.schoolmanagment.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "USER_MEMBERSHIPS")
public class UserMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "userMembershipSequenceGenerator")
    @SequenceGenerator(name="userMembershipSequenceGenerator", sequenceName = "USER_GROUP_SEQ", allocationSize = 0)
    private Long userMembershipId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private  User user;
    private char activeFlag;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="group_id", referencedColumnName = "group_id")
    private Group group;
}
