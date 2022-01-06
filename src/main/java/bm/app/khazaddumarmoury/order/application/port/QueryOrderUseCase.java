package bm.app.khazaddumarmoury.order.application.port;

import bm.app.khazaddumarmoury.order.domain.Order;
import bm.app.khazaddumarmoury.order.domain.OrderItem;
import bm.app.khazaddumarmoury.order.domain.OrderStatus;
import bm.app.khazaddumarmoury.order.domain.Recipient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface QueryOrderUseCase {

    /**
     * Used for searching.
     */

    List<Order> findAll();

    List<Order> findByRecipientName(String recipientName);

    Optional<Order> findById(Long id);

    UpdateOrderStatusResponse updateOrderStatus(UpdateOrderStatusCommand command);

    @Value
    @Builder
    @AllArgsConstructor
    class UpdateOrderStatusCommand {
        Long id;
        OrderStatus status;

        public Order updateStatus(Order order) {
            if (status != null) {
                order.setStatus(status);
            }
            return order;
        }
    }

    @Value
    class UpdateOrderStatusResponse {
        public static UpdateOrderStatusResponse SUCCESS = new UpdateOrderStatusResponse(true, Collections.emptyList());
        boolean success;
        List<String> errors;
    }

}
