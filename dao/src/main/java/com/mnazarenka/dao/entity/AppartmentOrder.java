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
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString(callSuper = true)
@Entity
@Table(name = "orders")
public class AppartmentOrder extends BaseEntity implements Serializable {
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
    @Column(name = "start_date")
    private LocalDate startDate;
    @Getter
    @Setter
    @Column(name = "end_date")
    private LocalDate endDate;
    @Getter
    @Setter
    @Version
    @Column
    private LocalDateTime version;
}
