package com.ekart.goliathus.entities;

import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sandesh.kumar on 22/06/17.
 */
@Entity
@Table
@Data
@NamedQueries({
        @NamedQuery(name = "com.ekart.goliathus.entities.PlacedOrder.findByLocality",
                query = "select o from PlacedOrder o where o.locality = :locality and o.status = 'created'")
})
public class PlacedOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = Customer.class)
    private Customer customer;

    @ManyToOne(targetEntity = Slot.class)
    private Slot slot;

    private Double orderValue;

    private Double billAmount;

    private String status;

    private String locality;

    @Generated(GenerationTime.INSERT)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", insertable = false, updatable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt;

    @Generated(GenerationTime.ALWAYS)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", insertable = false, updatable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private Date updatedAt;
}
