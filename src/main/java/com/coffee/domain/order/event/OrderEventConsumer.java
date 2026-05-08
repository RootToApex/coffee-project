package com.coffee.domain.order.event;


import com.coffee.domain.order.service.OrderRankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final OrderRankingService orderRankingService;

    @KafkaListener(topics = OrderEventPublisher.ORDER_CREATED_TOPIC)
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("주문 이벤트 수신 - OrderId: {}, menuId: {}", event.orderId(), event.menuId());
        orderRankingService.recordOrder(event.orderId(), event.menuId(), event.createdAt());
    }
}
