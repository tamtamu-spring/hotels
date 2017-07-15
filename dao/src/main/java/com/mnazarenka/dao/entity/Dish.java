package com.mnazarenka.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "dishes")
public class Dish extends BaseEntity implements Serializable {
    @Getter
    @Setter
    @Column
    private String name;
    @Getter
    @Setter
    @Column
    private String image;
}
