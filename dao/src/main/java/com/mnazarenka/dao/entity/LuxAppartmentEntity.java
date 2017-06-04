package com.mnazarenka.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("lux")
public class LuxAppartmentEntity {
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
    private Boolean kichen;
}
