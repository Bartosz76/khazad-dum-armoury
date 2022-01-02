package bm.app.khazaddumarmoury.order.web;

import bm.app.khazaddumarmoury.order.application.port.DeleteOrderUseCase;
import bm.app.khazaddumarmoury.order.application.port.PlaceOrderUseCase;
import bm.app.khazaddumarmoury.order.application.port.PlaceOrderUseCase.PlaceOrderCommand;
import bm.app.khazaddumarmoury.order.application.port.PlaceOrderUseCase.PlaceOrderResponse;
import bm.app.khazaddumarmoury.order.application.port.QueryOrderUseCase;
import bm.app.khazaddumarmoury.order.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/order")
@RestController
@AllArgsConstructor
public class OrderController {

    private final QueryOrderUseCase queryOrderUseCase;
    private final PlaceOrderUseCase placeOrderUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAll(@RequestParam Optional<String> recipientName,
                              @RequestParam(defaultValue = "10") int limit) {
        if (recipientName.isPresent()) {
            return queryOrderUseCase.findByRecipientName(recipientName.get());
        }
        return queryOrderUseCase.findAll().stream().limit(limit).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        if (id.equals(97L)) {
            throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "97 is illegal today!");
        }
        return queryOrderUseCase
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceOrderResponse addOrder(@RequestBody PlaceOrderCommand command) {
        return placeOrderUseCase.placeOrder(command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        deleteOrderUseCase.removeById(id);
    }
}
