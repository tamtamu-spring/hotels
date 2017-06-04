package com.mnazarenka.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Adress implements Serializable{
    @Getter
    @Setter
    @Column
    private String city;

    @Getter
    @Setter
    @Column
    private String street;
}
