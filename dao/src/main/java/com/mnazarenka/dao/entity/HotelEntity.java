package com.mnazarenka.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "entities", callSuper = true)
@Entity
@Table(name = "hotels")
public class HotelEntity extends BaseDao implements Serializable {
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
}
