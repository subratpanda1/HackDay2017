package com.ekart.goliathus;

import lombok.Data;

/**
 * Created by sandesh.kumar on 23/06/17.
 */
@Data
public class OrderDto {
    Long slotId;
    Long customerId;
    Double orderValue;
    String locality;
}
