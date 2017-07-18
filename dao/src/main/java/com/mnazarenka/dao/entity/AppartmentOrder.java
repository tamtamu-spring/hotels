package com.mnazarenka.dao.entity;

import com.mnazarenka.dao.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @Getter
    @Setter
    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @Getter
    @Setter
    @Version
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd: HH:mm:ss")
    private LocalDateTime version;
    @Getter
    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
}
