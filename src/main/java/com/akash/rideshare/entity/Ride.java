package com.akash.rideshare.entity;

import com.akash.rideshare.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rides")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "passenger_id",nullable = false)
    private User passenger;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private User driver;

    @Embedded
    @AttributeOverrides({
           @AttributeOverride(
                   name = "address",
                   column = @Column(name = "pickup_address")
           ),
            @AttributeOverride(
                    name = "latitude",
                    column = @Column(name = "pickup_latitude")
            ),

           @AttributeOverride(
                   name = "longitude",
                   column = @Column(name = "pickup_longitude")
           )
   })
    private Location pickupLocation;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name="address",
                    column = @Column(name = "drop_address")
            ),
            @AttributeOverride(
                    name="latitude",
                    column = @Column(name = "drop_latitude")
            ),
            @AttributeOverride(
                    name="longitude",
                    column = @Column(name = "drop_longitude")
            )
    })
    private Location dropLocation;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private BigDecimal fare;

    private LocalDateTime bookedAt;

    private LocalDateTime acceptedAt;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    private LocalDateTime cancelledAt;
}
