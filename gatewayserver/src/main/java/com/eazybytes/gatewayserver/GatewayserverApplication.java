package com.eazybytes.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }

    @Bean
    public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("accounts-route", r -> r
                        .path("/eazybank/accounts/**")
                        .filters(f -> f.rewritePath(
                                        "/eazybank/accounts/(?<segment>.*)",
                                        "/${segment}"   // FIXED
                                )
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://ACCOUNTS"))

                .route("loans-route", r -> r
                        .path("/eazybank/loans/**")   // FIXED
                        .filters(f -> f.rewritePath(
                                        "/eazybank/loans/(?<segment>.*)",
                                        "/${segment}"   // FIXED
                                )
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://LOANS"))

                .route("cards-route", r -> r
                        .path("/eazybank/cards/**")   // FIXED
                        .filters(f -> f.rewritePath(
                                "/eazybank/cards/(?<segment>.*)",
                                "/${segment}"   // FIXED
                        )
                       .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://CARDS"))

                .build();
    }

}
