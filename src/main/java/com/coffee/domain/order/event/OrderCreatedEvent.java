package com.coffee.domain.order.event;

import java.time.LocalDateTime;

public record OrderCreatedEvent(
        Long orderId,
        Long userId,
        Long menuId,
        Long price,
        LocalDateTime createdAt
) {
}
