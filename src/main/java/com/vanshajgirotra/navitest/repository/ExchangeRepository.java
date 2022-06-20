package com.vanshajgirotra.navitest.repository;

import com.vanshajgirotra.navitest.model.Order;


public interface ExchangeRepository {
    void registerOrder(Order order);
    Order getTopOrder(Order.OrderType orderType, String stock);
    void removeOrder(Order order);
}
