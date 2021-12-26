package bm.app.khazaddumarmoury.order.application;

import bm.app.khazaddumarmoury.order.application.port.DeleteOrderUseCase;
import bm.app.khazaddumarmoury.order.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteOrderService implements DeleteOrderUseCase {

    private final OrderRepository repository;

    @Override
    public void removeById(Long id) {
        repository.removeById(id);
    }
}
