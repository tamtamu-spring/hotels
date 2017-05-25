package com.mnazarenka.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AppartmentEntity implements Serializable {

    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private int fkKategoryId;
    @Getter
    @Setter
    private int fkHotelId;
    @Getter
    @Setter
    private String name;
}
