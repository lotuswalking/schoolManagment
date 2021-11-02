package com.example.schoolmanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "USER_MEMBERSHIPS")
public class UserMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userMembershipId;
    private Boolean activeFlag;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;

}
