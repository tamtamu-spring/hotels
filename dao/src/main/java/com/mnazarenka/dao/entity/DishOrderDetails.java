package com.mnazarenka.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@NoArgsConstructor
@Entity
@Table(name = "dish_orders_details")
public class DishOrderDetails extends BaseEntity implements Serializable {
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_dish_orders_id")
    private DishOrder order;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_dishes_id")
    private Dish dish;
    @Getter
    @Setter
    @Column
    private Integer count;
}
