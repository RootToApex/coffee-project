package com.coffee.domain.order.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderKafkaEventHandler {
    // 주문 생성 이벤트 Kafka로 발행

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderCreatedEvent event) {

        // key를 orderId로 지정하여 같은 주문 이벤트가 동일 하게 가도록 보장
        kafkaTemplate.send(
                OrderEventPublisher.ORDER_CREATED_TOPIC,
                String.valueOf(event.orderId()),
                event
        );
        log.info("Kafka 발행 완료 - orderId: {}, menuId: {}", event.orderId(), event.menuId());
    }
}
