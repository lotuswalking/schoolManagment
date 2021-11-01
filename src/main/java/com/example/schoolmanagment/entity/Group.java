package com.example.schoolmanagment.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "GROUP")
@EqualsAndHashCode
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "groupSequenceGenerator")
    @SequenceGenerator(name="groupSequenceGenerator", sequenceName = "GROUP_ID_SEQ", allocationSize = 1)
    @Column(name = "group_id")
//    @lookupIdField
    private  Long groupId;
    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "group_id", referencedColumnName = "role_id")
    private List<Role> roles;
    @Column(name = "active_flg")
    private char activeFlag;
    private Integer restrictedIndicator;

}
