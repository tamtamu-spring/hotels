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
@DiscriminatorValue("econom")
public class EconomApartmentEntity {

}
