package com.mnazarenka.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.beans.Transient;
import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "appartments")
public class AppartmentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    /*@Getter
    @Setter
    private int fkKategoryId;
    @Getter
    @Setter
    private int fkHotelId;*/
    @Getter
    @Setter
    @Column
    private String name;
    @Getter
    @Setter
    @Column
    private String description;
}
