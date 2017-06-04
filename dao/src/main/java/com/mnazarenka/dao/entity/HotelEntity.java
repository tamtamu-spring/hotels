package com.mnazarenka.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "entities", callSuper = true)
@Entity
@Table(name = "hotels")
public class HotelEntity extends BaseEntity implements Serializable {
    @Getter
    @Setter
    @Column
    private String name;
    @Getter
    @Setter
    @OneToMany(mappedBy = "hotel")
    private List<AppartmentEntity> entities;
    @Getter
    @Setter
    @Embedded
    private Adress adress;
    @Getter
    @Setter
    @ManyToMany
    @JoinTable(name = "dishes_hotels",
            joinColumns = @JoinColumn(name = "fk_dishes_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_hotels_id")
    )
    private Set<DishesEntity> dishes;
}
