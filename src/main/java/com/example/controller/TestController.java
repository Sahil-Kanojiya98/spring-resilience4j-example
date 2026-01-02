package com.example.controller;

import com.example.service.ResilientService;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class TestController {

    private final ResilientService service;

    public TestController(ResilientService service, TimeLimiterRegistry timeLimiterRegistry) {
        this.service = service;
    }

    @GetMapping("/circuit")
    public String circuitBreaker() {
        return service.circuitBreakerExample();
    }

    @GetMapping("/retry")
    public String retry() {
        return service.retryExample();
    }

    @GetMapping("/rate")
    public String rateLimiter() {
        return service.rateLimiterExample();
    }

    @GetMapping("/bulkhead")
    public String bulkhead() {
        return service.myBulkheadExample();
    }

    @GetMapping("/time")
    public CompletableFuture<String> timeLimiter() throws InterruptedException {
        return service.timeLimiterExample();
    }
}
