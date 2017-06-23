package com.ekart.goliathus.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by sandesh.kumar on 23/06/17.
 */
@Table
@Entity
@Data
@NamedQueries({
        @NamedQuery(name = "com.ekart.goliathus.entities.Discount.findByOrderValue",
                query = "select d from Discount d where :orderValue >= d.startRange and :orderValue <= d.endRange")
})
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    Integer discount;

    Integer startRange;

    Integer endRange;
}
