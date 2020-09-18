package com.sda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PluginShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(PluginShopApplication.class, args);
    }
}
