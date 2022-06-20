package com.vanshajgirotra.navitest.repository;

import com.vanshajgirotra.navitest.model.Order;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

@Repository
public class MemoryExchangeRepository implements ExchangeRepository {
    private final Map<String, PriorityQueue<Order>> buyOrders = new HashMap<>();
    private final Map<String, PriorityQueue<Order>> sellOrders = new HashMap<>();

    @Override
    public void registerOrder(Order order) {
        String stock = order.getStock();
        buyOrders.putIfAbsent(stock, new PriorityQueue<>(Comparator.comparingDouble(Order::getPrice).reversed().thenComparing(Order::getTime)));
        sellOrders.putIfAbsent(stock, new PriorityQueue<>(Comparator.comparingDouble(Order::getPrice)));
        if (order.getOrderType() == Order.OrderType.BUY) {
            buyOrders.get(stock).add(order);
        } else {
            sellOrders.get(order.getStock()).add(order);
        }
    }

    @Override
    @Nullable
    public Order getTopOrder(Order.OrderType orderType, String stock) {
        if (orderType == Order.OrderType.BUY) {
            return buyOrders.get(stock).peek();
        }
        return sellOrders.get(stock).peek();
    }

    @Override
    public void removeOrder(Order order) {
        if (order.getOrderType() == Order.OrderType.BUY) {
            buyOrders.get(order.getStock()).remove(order);
            return;
        }
        sellOrders.get(order.getStock()).remove(order);
    }
}
