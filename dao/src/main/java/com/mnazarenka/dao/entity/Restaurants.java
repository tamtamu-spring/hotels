package com.mnazarenka.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * Created by Николай on 10.06.2017.
 */
public class Restaurants extends BaseEntity {
    @Getter
    @Setter
    @Column
    private String name;
    @Getter
    @Setter
    @ManyToMany
    @JoinTable(name = "dishes_restaurants",
            joinColumns = @JoinColumn(name = "fk_restaurants_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_dishes_id")
    )
    private Set<Dishes> dishes;
}
