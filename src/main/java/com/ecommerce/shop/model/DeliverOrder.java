package com.ecommerce.shop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "deliver_details")
public class DeliverOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tracking_id")
    private String trackingId;

    @Column(name = "order_id")
    private Integer orderId;


    @Column(name = "user_id")
    private Integer userId;

}
