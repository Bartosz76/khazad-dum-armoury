package bm.app.khazaddumarmoury.order.application.port;

import bm.app.khazaddumarmoury.armour.domain.Armour;
import bm.app.khazaddumarmoury.order.domain.Order;
import bm.app.khazaddumarmoury.order.domain.OrderItem;
import bm.app.khazaddumarmoury.order.domain.OrderStatus;
import bm.app.khazaddumarmoury.order.domain.Recipient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface QueryOrderUseCase {
    List<RichOrder> findAll();

    Optional<RichOrder> findById(Long id);

    @Value
    class RichOrder {
        Long id;
        OrderStatus status;
        List<RichOrderItem> items;
        Recipient recipient;
        LocalDateTime createdAt;

        public BigDecimal totalPrice() {
            return items.stream()
                    .map(item -> item.getArmour().getPrice().multiply(new BigDecimal(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    @Value
    class RichOrderItem {
        Armour armour;
        int quantity;
    }
}
