package com.mnazarenka.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@ToString(callSuper = true, exclude = "appartments")
@Entity
@Table(name = "users")
public class User extends BaseEntity implements Serializable {
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_role_id")
    private Role role;
    @Getter
    @Setter
    @Column
    private String login;
    @Getter
    @Setter
    @Column
    private String password;
    @Getter
    @Setter
    @OneToMany(mappedBy = "user")
    private List<AppartmentOrder> appartments;
}
