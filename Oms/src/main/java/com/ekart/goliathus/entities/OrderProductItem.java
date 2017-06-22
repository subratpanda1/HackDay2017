package com.ekart.goliathus.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by sandesh.kumar on 22/06/17.
 */
@Entity
@Table
@Data
public class OrderProductItem implements Serializable {
    @Id
    @ManyToOne(targetEntity = PlacedOrder.class)
    @NotNull
    PlacedOrder order;

    @Id
    @ManyToOne(targetEntity = Product.class)
    @NotNull
    Product product;

    long quantity;

    long amount;
}
