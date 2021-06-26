package bm.app.khazaddumarmoury.order.domain;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Value
public class Order {
    Long id;
    List<OrderItem> items;
    Recipient recipient;
    OrderStatus status;
    LocalDateTime createdAt;

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
