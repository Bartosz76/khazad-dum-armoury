package bm.app.khazaddumarmoury.order.application.port;

import bm.app.khazaddumarmoury.order.domain.Order;

import java.util.List;
import java.util.Optional;

public interface QueryOrderUseCase {

    /**
     * Used for searching.
     */

    List<Order> findAll();

    List<Order> findByRecipientName(String recipientName);

    Optional<Order> findById(Long id);

}
