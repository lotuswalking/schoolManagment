package com.example.schoolmanagement.jpa.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "USER_MEMBERSHIPS")
public class UserMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="usermembership_id")
    private Long userMembershipId;
    @Column(name ="active_flg")
    private Boolean activeFlag;
//    @Column(name ="user_id")
    @ManyToOne @JoinColumn(name = "user_id")
    private User user;
//    @Column(name ="user_id")
    @ManyToOne @JoinColumn(name = "group_id")
    private Group group;

    private LocalDate effectiveDate;
    private LocalDate expiryDate;

}
