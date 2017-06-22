package com.ekart.goliathus.entities;

import lombok.Data;

import javax.persistence.Embeddable;

/**
 * Created by sandesh.kumar on 23/06/17.
 */

@Embeddable
@Data
public class Address {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String pinCode;
}
