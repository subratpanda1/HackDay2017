package com.ekart.goliathus.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.DayOfWeek;

/**
 * Created by sandesh.kumar on 23/06/17.
 */
@Entity
@Table
@Data
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek day;

    private String timeSlot;

}
