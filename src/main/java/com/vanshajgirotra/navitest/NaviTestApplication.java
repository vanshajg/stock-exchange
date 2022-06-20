package com.vanshajgirotra.navitest;

import com.vanshajgirotra.navitest.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class NaviTestApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NaviTestApplication.class, args);
    }
    private final ExchangeService exchangeService;
    @Override
    public void run(String... args) throws Exception {
        exchangeService.process();
    }
}
