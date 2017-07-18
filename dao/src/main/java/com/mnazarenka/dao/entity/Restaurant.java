package com.mnazarenka.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "my-cache")
@Table(name = "restaurants")
public class Restaurant extends BaseEntity {
    @Getter
    @Setter
    @Column
    private String name;
    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "id")
    private Hotel hotel;
    @Getter
    @Setter
    @ManyToMany
    @JoinTable(name = "dishes_restaurants",
            joinColumns = @JoinColumn(name = "fk_restaurants_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_dishes_id")
    )
    private Set<Dish> dishes = new HashSet<>();
}
