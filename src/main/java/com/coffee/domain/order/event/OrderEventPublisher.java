package com.coffee.domain.order.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    public static final String ORDER_CREATED_TOPIC = "order.created";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishOrderCreated(OrderCreatedEvent event) {
        kafkaTemplate.send(ORDER_CREATED_TOPIC, String.valueOf(event.orderId()), event);
        log.info("주문 이벤트 발행 - orderId: {}, menuId: {}", event.orderId(), event.menuId());
    }
}
