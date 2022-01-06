package bm.app.khazaddumarmoury.order.application;

import bm.app.khazaddumarmoury.order.application.port.QueryOrderUseCase;
import bm.app.khazaddumarmoury.order.domain.Order;
import bm.app.khazaddumarmoury.order.domain.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QueryOrderService implements QueryOrderUseCase {

    private final OrderRepository repository;

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Order> findByRecipientName(String recipientName) {
        return repository.findByRecipientName(recipientName);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public UpdateOrderStatusResponse updateOrderStatus(UpdateOrderStatusCommand command) {
        return repository.findById(command.getId())
                .map(order -> {
                    Order updatedOrder = command.updateStatus(order);
                    repository.save(updatedOrder);
                    return UpdateOrderStatusResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateOrderStatusResponse(false, Collections.singletonList("Order of the id: " + command.getId() + " not found.")));
    }
}
