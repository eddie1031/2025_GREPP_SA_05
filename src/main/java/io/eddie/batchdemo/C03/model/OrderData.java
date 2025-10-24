package io.eddie.batchdemo.C03.model;

import java.time.LocalDateTime;

public record OrderData(
        String orderCode,
        String productName,
        Long quantity,
        Long price,
        LocalDateTime orderDate
) {
}
