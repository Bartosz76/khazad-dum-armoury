package bm.app.khazaddumarmoury.order.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * What's in the domain, can be accessible from the outside. Hence -> public classes.
 */

@Data
@Builder
public class Order {

    private Long id;

    private List<OrderItem> items;

    private Recipient recipient;

    /**
     * Below @Builder.Default will make OrderStatus.NEW a default value for the field if my Builder does not
     * set it to anything itself.
     * id and createdAt are also usually not created by the Builder, because my repository does it -> the record
     * receives the id at the time of being saved into "the database" and the time of its creation is... well, at
     * the time of its creation.
     */
    @Builder.Default
    private OrderStatus status = OrderStatus.NEW;

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

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }
}
