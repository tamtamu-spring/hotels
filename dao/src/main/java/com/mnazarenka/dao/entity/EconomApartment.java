package com.mnazarenka.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("econom")
public class EconomApartment extends Appartment implements Serializable {
}
