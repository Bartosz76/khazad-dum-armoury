package bm.app.khazaddumarmoury.order.domain;

import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * What's in the domain, can be accessible from the outside. Hence -> public classes.
 */

@Data
public class Order {
    private Long id;
    private List<OrderItem> items;
    private Recipient recipient;
    private OrderStatus status;
    private LocalDateTime createdAt;

    /**
     * Counting the price of the armour piece.
     */

    public BigDecimal totalPrice() {
        return items.stream()
                .map(item -> item.getArmour().getPrice().multiply(new BigDecimal(item.getQuantity())))
                //Up there I need to multiply because without it, it's just a single armour piece.
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //.reduce allows me count the prices of all elements of the stream - to the value of zero,
        //add what comes next.
    }

}
