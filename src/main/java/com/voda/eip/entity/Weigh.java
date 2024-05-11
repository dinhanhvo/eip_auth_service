package com.voda.eip.entity;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * SELECT TOP (10)
 * [id]
 * ,[Serial_Weigher]
 * ,[Code_Seller]
 * ,[Name_Seller]
 * ,[Code_Tank_Seller]
 * ,[Tank_Tare_Weight] real
 * ,[Tank_Gross_Weight] real
 * ,[Tank_Net_Weight] real
 * ,[Mqtt_Status]
 * ,[created]
 * FROM [CNHS_CheckWeigher].[dbo].[milk_collect]
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "weigh",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "Serial_Weigher"
                })
        }
)
public class Weigh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "Serial_Weigher")
    private String serialWeigher;

    @Column(name = "model")
    private String model;

    @Column(name = "power")
    private String power;

    @Column(name = "dateProduce")
    private String dateProduce;

}
