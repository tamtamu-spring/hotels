package com.mnazarenka.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "roles")
public class Role extends BaseEntity implements Serializable {
    @Getter
    @Setter
    @Column(name = "role")
    private String name;
}
