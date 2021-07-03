package bm.app.khazaddumarmoury.order.application.port;

import bm.app.khazaddumarmoury.order.domain.Order;

import java.util.List;

public interface QueryOrderUseCase {

    /**
     * Used for searching.
     */

    List<Order> findAll();

}
