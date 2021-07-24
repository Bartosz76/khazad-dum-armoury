package bm.app.khazaddumarmoury.armour.application.port;

import bm.app.khazaddumarmoury.armour.domain.Armour;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface ArmourUseCase {

    /**
     * The interface to be implemented by concrete implementations of Services. This
     * interface resides in a 'port' catalog according to hexagonal architecture's
     * rules. These 'ports' are basically entrances to packages.
     * Thus, the various layers of the application are separated and entryways to each
     * can be accessed via interfaces held in these 'ports'.
     */

    List<Armour> findByName(String name);

    Optional<Armour> findOneByName(String name);

    List<Armour> findBySmith(String smith);

    List<Armour> findAll();

    Optional<Armour> findById(Long id);

    Optional<Armour> findOneByNameAndSmith(String name, String smith);

    void addArmour(CreateArmourCommand command);

    void removeById(Long id);

    UpdateArmourResponse updateArmour(UpdateArmourCommand command);

    /**
     * This is basically a wrapper for other fields. By using it I can avoid having to pass all the fields separately
     * as arguments while also I don't want Id field to be taken as an argument now, do I? CreateArmourCommand is,
     * essentially, a mini DTO.
     * These command classes should be held in the interface (instead of the port catalog),
     * because their lifespan is very short.
     * The inner class automatically is static in an interface.
     */

    @Value //Makes the class have all the fields private and final as well as gives a constructor.
           //And getters, setters, toString, etc...
    class CreateArmourCommand {
        String name;
        String type;
        String smith;
        Integer year;
        BigDecimal price;

        /**
         * As long as the creation of a domain object is pretty simple, devoid of any validation or complicated logic,
         * I can create a method here, in the Command, to create such an object, so there's less 'noise' in the
         * service itself. It's just a part of the Command pattern.
         */
        public Armour toArmour() {
            return new Armour(name, type, smith, year, price);
        }
    }

    /**
     * Command for updating the armour requires an Id... in order to know what record to update.
     */
    @Value
    /**
     * Adding the Builder to prevent copying and pasting both ways properties of the object being updated (line 89 in
     * the ApplicationStartUp). The idea is to not have to 'resupply' the values of the fields that are not being
     * updated - creating the new UpdateArmourCommand, adding a new name for instance... and doing 'armour.getSmith(),
     * armour.getYear() and so on to just complete this UpdateArmourCommand object.
     */
    @Builder
    class UpdateArmourCommand {
        Long id;
        String name;
        String type;
        String smith;
        Integer year;
        BigDecimal price;

        /**
         * This method will prevent nulls when updating a record. I could just as well implement it in a Service
         * class when the update record method is defined, but this allows me to minimise the complexity. Below method
         * will allow the Builder to actually update only the fields that are being updated and it will cover those
         * not mentioned in the builder.
         */
         public Armour updateFields(Armour armour) {
            if (name != null) {
                armour.setName(name);
            }
            if (type != null) {
                armour.setType(type);
            }
            if (smith != null) {
                armour.setSmith(smith);
            }
            if (year != null) {
                armour.setYear(year);
            }
            return armour;
            //If they ARE nulls, that means I don't want to change these fields and I want them to remain
            //what they were before.
        }

    }

    /**
     * Another type of a short-lived class to inform me how the updating went.
     */
    @Value
    class UpdateArmourResponse {
        //Below to be reused in cases of success.
        public static UpdateArmourResponse SUCCESS  = new UpdateArmourResponse(true, Collections.emptyList());
        boolean success;
        List<String> errors;

    }
}

