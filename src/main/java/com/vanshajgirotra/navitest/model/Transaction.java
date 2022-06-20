package com.vanshajgirotra.navitest.model;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class Transaction {
    private String buyOrderId;
    private Double sellPrice;
    private Integer quantity;
    private String sellOrderId;
}
