package bm.app.khazaddumarmoury.order.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum OrderStatus {
    NEW, CONFIRMED, BEING_FORGED, IN_DELIVERY, DELIVERED, CANCELLED, RETURNED_FOR_MENDING;

    public static Optional<OrderStatus> parseString(String value) {
        return Arrays.stream(values())
                .filter(it -> StringUtils.equalsIgnoreCase(it.name(), value))
                .findFirst();
    }
}
