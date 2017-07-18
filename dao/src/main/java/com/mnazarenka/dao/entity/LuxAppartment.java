package com.mnazarenka.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@NoArgsConstructor
@Entity
@DiscriminatorValue("lux")
public class LuxAppartment extends Appartment implements Serializable{
    @Getter
    @Setter
    @Column
    private Boolean wc;
    @Getter
    @Setter
    @Column
    private Boolean tv;
    @Getter
    @Setter
    @Column
    private Boolean bar;
    @Getter
    @Setter
    @Column
    private Boolean kitchen;
}
