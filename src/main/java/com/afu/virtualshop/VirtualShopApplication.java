package com.afu.virtualshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.afu.virtualshop.repositories")
@EntityScan(basePackages = "com.afu.virtualshop.models")

public class VirtualShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirtualShopApplication.class, args);
    }


}
