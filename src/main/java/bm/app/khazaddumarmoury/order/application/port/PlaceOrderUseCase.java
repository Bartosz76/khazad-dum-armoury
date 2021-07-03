package bm.app.khazaddumarmoury.order.application.port;

public interface PlaceOrderUseCase {

    /**
     * This will be used to create new orders.
     * UseCases placed within ports ("entryways" into each layer) can be multiple for
     * the sake of modularization. As I will have multiple Service classes for performing
     * various tasks, so UseCases these Services will implement are multiple and various.
     */

    void placeOrder(PlaceOrderCommand command);

    /**
     * Again, I use the Command Pattern to have a 'wrapper' for fields I would be passing
     * in if I wasn't using a Command. So this is my mini DTO. The Command encapsulate
     * what is being passed into the order.
     * I could just pass in parameters instead... but this is more elegant.
     */
    class PlaceOrderCommand {

    }

    class PlaceOrderResponse {

    }
}
