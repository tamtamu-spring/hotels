package com.mnazarenka.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
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
    @Column(name = "order_time")
    private LocalDateTime orderTime;
    @Getter
    @Setter
    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<DishOrderDetails> details;
}
