package bm.app.khazaddumarmoury.order.application;

import bm.app.khazaddumarmoury.armour.domain.Armour;
import bm.app.khazaddumarmoury.armour.domain.ArmourRepository;
import bm.app.khazaddumarmoury.order.application.port.QueryOrderUseCase;
import bm.app.khazaddumarmoury.order.domain.Order;
import bm.app.khazaddumarmoury.order.domain.OrderItem;
import bm.app.khazaddumarmoury.order.domain.OrderRepository;
import bm.app.khazaddumarmoury.order.domain.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class QueryOrderService implements QueryOrderUseCase {
    private final OrderRepository repository;
    private final ArmourRepository armourRepository;

    @Override
    public List<RichOrder> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toRichOrder)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RichOrder> findById(Long id) {
        return repository.findById(id).map(this::toRichOrder);
    }

    private RichOrder toRichOrder(Order order) {
        List<RichOrderItem> richItems = toRichItems(order.getItems());
        return new RichOrder(
                order.getId(),
                order.getStatus(),
                richItems,
                order.getRecipient(),
                order.getCreatedAt()
        );
    }

    private List<RichOrderItem> toRichItems(List<OrderItem> items) {
        return items.stream()
                .map(item -> {
                    Armour armour = armourRepository
                            .findById(item.getArmourId())
                            .orElseThrow(() -> new IllegalStateException("Unable to find book with ID: " + item.getArmourId()));
                    return new RichOrderItem(armour, item.getQuantity());
                })
                .collect(Collectors.toList());
    }
}
