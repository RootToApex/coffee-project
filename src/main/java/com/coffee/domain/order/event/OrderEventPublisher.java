package com.coffee.domain.order.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    public static final String ORDER_CREATED_TOPIC = "order.created";

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishOrderCreated(OrderCreatedEvent event) {
        applicationEventPublisher.publishEvent(event);
        log.info("주문 이벤트 발행(스프링 이벤트) - orderId: {}", event.orderId());
    }
}
