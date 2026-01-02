package com.example.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class ResilientService {

    private static final Logger log = LoggerFactory.getLogger(ResilientService.class);

    @CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "fallback")
    public String circuitBreakerExample() {
        if (Math.random() < 0.6) {
            throw new RuntimeException("Circuit breaker triggered!");
        }
        return "Circuit Breaker Success!";
    }

    @Retry(name = "myRetry", fallbackMethod = "fallback")
    public String retryExample() {
        if (Math.random() < 0.7) {
            throw new RuntimeException("Retry triggered!");
        }
        return "Retry Success!";
    }

    @RateLimiter(name = "myRateLimiter", fallbackMethod = "fallback")
    public String rateLimiterExample() {
        return "Rate Limiter Success!";
    }

    @Bulkhead(name = "myBulkhead", fallbackMethod = "fallback")
    public String myBulkheadExample() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ignored) {
        }
        return "Bulkhead Success!";
    }

    @TimeLimiter(name = "myTimeLimiter", fallbackMethod = "futureFallback")
    public CompletableFuture<String> timeLimiterExample() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException ignored) {
            }
            return "Time Limiter Success!";
        });
    }

    public String fallback(Throwable t) {
        return "Fallback response: " + t.getMessage();
    }

    public CompletableFuture<String> futureFallback(Throwable t) {
        return CompletableFuture.completedFuture("Fallback response: " + t.getMessage());
    }
}

