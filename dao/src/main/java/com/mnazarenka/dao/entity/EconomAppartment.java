package com.mnazarenka.dao.entity;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("econom")
public class EconomAppartment extends Appartment implements Serializable {
}
