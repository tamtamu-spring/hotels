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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "details", callSuper = true)
@Entity
@Table(name = "dish_orders")
public class DishOrder extends BaseEntity implements Serializable {
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_users_id")
    private User user;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_appartments_id")
    private Appartment appartment;
    @Getter
    @Setter
    @Column(name = "order_time")
    private LocalDateTime orderTime;
    @Getter
    @Setter
    @OneToMany(mappedBy = "order")
    private Set<DishOrderDetails> details;
}
