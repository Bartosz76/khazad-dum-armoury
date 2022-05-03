package bm.app.khazaddumarmoury.order.domain;

import lombok.Value;

/**
 * To make a list out of -> keep both the kind of armour requested as well its quantity.
 * It is a part of Order, so it also needs to be an entity.
 * It also, as an entity, requires an ID.
 */

@Value
public class OrderItem {
    Long armourId;
    int quantity;
}
