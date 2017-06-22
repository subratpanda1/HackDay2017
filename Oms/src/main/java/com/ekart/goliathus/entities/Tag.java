package com.ekart.goliathus.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by sandesh.kumar on 23/06/17.
 */
@Entity
@Table
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(targetEntity = Product.class)
    Product product;
    String name;
}
