package bm.app.khazaddumarmoury.order.domain;

import bm.app.khazaddumarmoury.armour.domain.Armour;
import lombok.Value;

/**
 * To make a list out of -> keep both the kind of armour requested as well its quantity.
 */

@Value
public class OrderItem {
    Armour armour;
    int quantity;
}
