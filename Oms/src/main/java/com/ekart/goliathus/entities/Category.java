package com.ekart.goliathus.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by sandesh.kumar on 22/06/17.
 */
@Entity
@Table
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;

}
