package com.vanshajgirotra.navitest.model;

import lombok.*;

import java.time.LocalTime;

@ToString
@Getter
@Builder
@Setter
public class Order {
    public enum OrderType {
        BUY,
        SELL
    }

    private String orderId;
    private LocalTime time;
    private String stock;
    private OrderType orderType;
    private Integer quantity;
    private Double price;
}
