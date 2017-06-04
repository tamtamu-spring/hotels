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
import java.time.LocalDate;

@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString(callSuper = true)
@Entity
@Table(name = "orders")
public class OrdersEntity extends BaseEntity implements Serializable {
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_users_id")
    private UserEntity user;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_appartments_id")
    private AppartmentEntity appartment;
    @Getter
    @Setter
    @Column(name = "start_date")
    private LocalDate startDate;
    @Getter
    @Setter
    @Column(name = "end_date")
    private LocalDate endDate;

}
