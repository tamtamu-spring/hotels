package com.mnazarenka.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class Adress implements Serializable {
    @Getter
    @Setter
    @Column
    private String city;

    @Getter
    @Setter
    @Column
    private String street;

    @Transient
    public String getFullAdress() {
        return String.format("%s %s", city, street);
    }
}
