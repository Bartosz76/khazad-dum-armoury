package bm.app.khazaddumarmoury.order.application.port;

import bm.app.khazaddumarmoury.order.domain.OrderItem;
import bm.app.khazaddumarmoury.order.domain.Recipient;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

public interface PlaceOrderUseCase {

    /**
     * This will be used to create new orders.
     * UseCases placed within ports ("entryways" into each layer) can be multiple for
     * the sake of modularization. As I will have multiple Service classes for performing
     * various tasks, so UseCases these Services will implement are multiple and various.
     */

    PlaceOrderResponse placeOrder(PlaceOrderCommand command);

    /**
     * Again, I use the Command Pattern to have a 'wrapper' for fields I would be passing
     * in if I wasn't using a Command. So this is my mini DTO. The Command encapsulate
     * what is being passed into the order.
     * I could just pass in parameters instead... but this is more elegant.
     */
    @Builder
    @Value
    class PlaceOrderCommand {
        /**
         * If I have collections in my Lombok's Builder, I can get additional methods
         * for handling the collection if I add @Singular to said collection.
         */
        @Singular
        List<OrderItem> items;
        Recipient recipient;
    }

    @Value
    class PlaceOrderResponse {
        boolean success;
        Long orderId;
        List<String> errors;

        /**
         * Below are static constructors.
         * This allows me to manually choose which constructor will be triggered
         * (by typing PlaceOrderResponse.success(...)). These should not be called
         * constructors, I think. These are just regular static methods, constructors
         * shouldn't have names.
         */
        public static PlaceOrderResponse success(Long orderId) {
            return new PlaceOrderResponse(true, orderId, emptyList());
        }

        public static PlaceOrderResponse failure(String... errors) {
            return new PlaceOrderResponse(false, null, Arrays.asList(errors));
        }
    }
}
