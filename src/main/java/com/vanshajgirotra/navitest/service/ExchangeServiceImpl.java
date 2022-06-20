package com.vanshajgirotra.navitest.service;

import com.vanshajgirotra.navitest.model.Order;
import com.vanshajgirotra.navitest.model.Transaction;
import com.vanshajgirotra.navitest.repository.ExchangeRepository;
import com.vanshajgirotra.navitest.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
    private final FileUtil fileReader;
    private final ExchangeRepository repository;
    private final List<Transaction> transactions = new ArrayList<>();
    private List<Order> orderList = new ArrayList<>();

    @Override
    public void process() {
        try {
            orderList = fileReader.getOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }

        orderList.forEach(order -> {
            String stock = order.getStock();
            repository.registerOrder(order);

            Order highestBuyOrder = repository.getTopOrder(Order.OrderType.BUY, stock);
            Order lowestSellOrder = repository.getTopOrder(Order.OrderType.SELL, stock);

            while (lowestSellOrder != null && highestBuyOrder != null && highestBuyOrder.getPrice() >= lowestSellOrder.getPrice()) {

                repository.removeOrder(lowestSellOrder);
                repository.removeOrder(highestBuyOrder);

                int quantitySold = Math.min(highestBuyOrder.getQuantity(), lowestSellOrder.getQuantity());
                if (highestBuyOrder.getQuantity() > quantitySold) {
                    highestBuyOrder.setQuantity(highestBuyOrder.getQuantity() - quantitySold);
                    repository.registerOrder(highestBuyOrder);
                }
                if (lowestSellOrder.getQuantity() > quantitySold) {
                    lowestSellOrder.setQuantity(lowestSellOrder.getQuantity() - quantitySold);
                    repository.registerOrder(lowestSellOrder);
                }

                transactions.add(
                        Transaction.builder()
                                .buyOrderId(highestBuyOrder.getOrderId())
                                .quantity(quantitySold)
                                .sellOrderId(lowestSellOrder.getOrderId())
                                .sellPrice(lowestSellOrder.getPrice())
                                .build()
                );

                highestBuyOrder = repository.getTopOrder(Order.OrderType.BUY, stock);
                lowestSellOrder = repository.getTopOrder(Order.OrderType.SELL, stock);
            }
        });
        log.info(transactions.toString());
    }
}
