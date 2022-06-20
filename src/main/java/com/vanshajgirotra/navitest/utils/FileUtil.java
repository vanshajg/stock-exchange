package com.vanshajgirotra.navitest.utils;

import com.vanshajgirotra.navitest.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class FileUtil {
    @Value("${input_file}")
    private String inputFile;

    public void getInputFile() {
        log.info(inputFile);
    }

    public List<Order> getOrders() throws IOException {
        List<Order> orderList = new ArrayList<>();
        Path path = Paths.get(inputFile);
        BufferedReader reader = Files.newBufferedReader(path);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] lineInput = line.split(" ");
            orderList.add(
                    Order.builder()
                            .orderId(lineInput[0])
                            .time(LocalTime.parse(lineInput[1]))
                            .stock(lineInput[2])
                            .orderType(lineInput[3].equals("buy") ? Order.OrderType.BUY : Order.OrderType.SELL)
                            .price(Double.valueOf(lineInput[4]))
                            .quantity(Integer.valueOf(lineInput[5]))
                            .build()
            );
        }
        return orderList;
    }
}
