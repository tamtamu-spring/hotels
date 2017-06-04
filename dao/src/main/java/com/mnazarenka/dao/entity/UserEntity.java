package com.mnazarenka.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString(callSuper = true)
@Entity
@Table(name = "users")
public class UserEntity extends BaseDao implements Serializable {
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_role_id")
    private RoleEntity role;
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
    @Column(name = "block_status")
    private String blockStatus;
}
