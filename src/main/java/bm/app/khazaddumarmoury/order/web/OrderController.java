package bm.app.khazaddumarmoury.order.web;

import bm.app.khazaddumarmoury.order.application.port.PlaceOrderUseCase;
import bm.app.khazaddumarmoury.order.application.port.QueryOrderUseCase;
import bm.app.khazaddumarmoury.order.domain.Order;
import bm.app.khazaddumarmoury.order.domain.Recipient;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/order")
@RestController
@AllArgsConstructor
public class OrderController {

    private final QueryOrderUseCase queryOrderUseCase;

    @GetMapping("/{recipientName}")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAll(@PathVariable Optional<String> recipientName,
                              @RequestParam(defaultValue = "10") int limit) {
        if (recipientName.isPresent()) {
            return queryOrderUseCase.findByRecipientName(recipientName.get());
        }
        return queryOrderUseCase.findAll().stream().limit(limit).collect(Collectors.toList());
    }

}
