package com.example.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class ResilientService {

    @CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "fallback")
    public String circuitBreakerExample() {
        if (Math.random() < 0.6) {
            throw new RuntimeException("Circuit breaker triggered!");
        }
        return "Circuit Breaker Success!";
    }

    @Retry(name = "myRetry")
    public String retryExample() {
        if (Math.random() < 0.7) {
            throw new RuntimeException("Retry triggered!");
        }
        return "Retry Success!";
    }

    @RateLimiter(name = "myRateLimiter")
    public String rateLimiterExample() {
        return "Rate Limiter Success!";
    }

    @Bulkhead(name = "myBulkhead")
    public String bulkheadExample() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Bulkhead Success!";
    }

    @TimeLimiter(name = "myTimeLimiter")
    public CompletableFuture<String> timeLimiterExample() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return CompletableFuture.completedFuture("Time Limiter Success!");
    }

    public String fallback(Throwable t) {
        return "Fallback response: " + t.getMessage();
    }
}

