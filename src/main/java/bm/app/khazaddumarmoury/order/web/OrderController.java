package bm.app.khazaddumarmoury.order.web;

import bm.app.khazaddumarmoury.order.application.port.DeleteOrderUseCase;
import bm.app.khazaddumarmoury.order.application.port.PlaceOrderUseCase;
import bm.app.khazaddumarmoury.order.application.port.PlaceOrderUseCase.PlaceOrderCommand;
import bm.app.khazaddumarmoury.order.application.port.PlaceOrderUseCase.PlaceOrderResponse;
import bm.app.khazaddumarmoury.order.application.port.QueryOrderUseCase;
import bm.app.khazaddumarmoury.order.domain.Order;
import bm.app.khazaddumarmoury.order.domain.OrderItem;
import bm.app.khazaddumarmoury.order.domain.OrderStatus;
import bm.app.khazaddumarmoury.order.domain.Recipient;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static bm.app.khazaddumarmoury.order.application.port.QueryOrderUseCase.UpdateOrderStatusCommand;
import static bm.app.khazaddumarmoury.order.application.port.QueryOrderUseCase.UpdateOrderStatusResponse;

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
    public ResponseEntity<Void> addOrder(@RequestBody RestOrderCommand command) {
        Order order = placeOrderUseCase.placeOrder(command.toPlaceCommand());
        URI uri = createOrderUri(order);
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateOrderStatus(@PathVariable Long id, @RequestBody RestOrderCommand command) {
        UpdateOrderStatusResponse response = queryOrderUseCase.updateOrderStatus(command.toUpdateStatusCommand(id));
        if (!response.isSuccess()) {
            String message = String.join(", ", response.getErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        deleteOrderUseCase.removeById(id);
    }

    @Data
    private static class RestOrderCommand {

        List<OrderItem> items;
        Recipient recipient;
        OrderStatus status;

        PlaceOrderCommand toPlaceCommand() {
            return new PlaceOrderCommand(items, recipient);
        }

        UpdateOrderStatusCommand toUpdateStatusCommand(Long id) {
            return new UpdateOrderStatusCommand(id, status);
        }
    }

    private URI createOrderUri(Order order) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + order.getId().toString())
                .build()
                .toUri();
    }
}
