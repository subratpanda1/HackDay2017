package com.ekart.goliathus.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by sandesh.kumar on 22/06/17.
 */
@Entity
@Table
@Data
@NamedQueries({
        @NamedQuery(name = "com.ekart.goliathus.entities.Customer.findByLocality",
                query = "select c from Customer c where c.address.locality = :locality")
})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String phone;
    private String email;
    @Embedded
    private Address address;
}
