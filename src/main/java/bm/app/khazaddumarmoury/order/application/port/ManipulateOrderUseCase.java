package bm.app.khazaddumarmoury.order.application.port;

import bm.app.khazaddumarmoury.commons.Either;
import bm.app.khazaddumarmoury.order.domain.OrderItem;
import bm.app.khazaddumarmoury.order.domain.OrderStatus;
import bm.app.khazaddumarmoury.order.domain.Recipient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

public interface ManipulateOrderUseCase {

    /**
     * This will be used to create new orders.
     * UseCases placed within ports ("entryways" into each layer) can be multiple for
     * the sake of modularization. As I will have multiple Service classes for performing
     * various tasks, so UseCases these Services will implement are multiple and various.
     */

    PlaceOrderResponse placeOrder(PlaceOrderCommand command);

    void deleteOrderById(Long id);

    void updateOrderStatus(Long id, OrderStatus status);

    /**
     * Again, I use the Command Pattern to have a 'wrapper' for fields I would be passing
     * in if I wasn't using a Command. So this is my mini DTO. The Command encapsulates
     * what is being passed into the order.
     * I could just pass in parameters instead... but this is more elegant.
     */
    @Builder
    @Value
    @AllArgsConstructor
    class PlaceOrderCommand {
        /**
         * If I have collections in my Lombok's Builder, I can get additional methods
         * for handling the collection if I add @Singular to said collection.
         */
        @Singular
        List<OrderItem> items;
        Recipient recipient;
    }

    class PlaceOrderResponse extends Either<String, Long> {
        public PlaceOrderResponse(boolean success, String left, Long right) {
            super(success, left, right);
        }

        /**
         * Below are static constructors.
         * This allows me to manually choose which constructor will be triggered
         * (by typing PlaceOrderResponse.success(...)).
         */
        public static PlaceOrderResponse success(Long orderId) {
            return new PlaceOrderResponse(true, null, orderId);
        }

        public static PlaceOrderResponse failure(String error) {
            return new PlaceOrderResponse(false, error, null);
        }
    }
}
