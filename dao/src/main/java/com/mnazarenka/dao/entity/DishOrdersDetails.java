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
@Table(name = "dish_orders_details")
public class DishOrdersDetails extends Base implements Serializable {
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_dish_orders_id")
    private DishOrders order;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_dishes_id")
    private Dishes dish;
    @Getter
    @Setter
    @Column
    private Integer count;
}
