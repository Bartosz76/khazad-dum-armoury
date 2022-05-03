package bm.app.khazaddumarmoury.order.application;

import bm.app.khazaddumarmoury.order.application.port.ManipulateOrderUseCase;
import bm.app.khazaddumarmoury.order.domain.Order;
import bm.app.khazaddumarmoury.order.domain.OrderRepository;
import bm.app.khazaddumarmoury.order.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ManipulateOrderService implements ManipulateOrderUseCase {
    private final OrderRepository repository;

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {
        Order order = Order
                .builder()
                .recipient(command.getRecipient())
                .items(command.getItems())
                .build();
        Order save = repository.save(order);
        return PlaceOrderResponse.success(save.getId());
        /**
         * Creating the order like I did above, the Order's field status would be a null... but it won't be due
         * to the setting of a default value for that field in the Order class.
         */
    }

    @Override
    public void deleteOrderById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateOrderStatus(Long id, OrderStatus status) {
        repository.findById(id)
                .ifPresent(order -> {
                    order.setStatus(status);
                    repository.save(order);
                });
    }
}
