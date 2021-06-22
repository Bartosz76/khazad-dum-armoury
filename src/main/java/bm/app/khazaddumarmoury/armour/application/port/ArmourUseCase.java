package bm.app.khazaddumarmoury.armour.application.port;

import bm.app.khazaddumarmoury.armour.domain.Armour;
import lombok.Value;

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

    List<Armour> findBySmith(String smith);

    List<Armour> findAll();

    Optional<Armour> findOneByNameAndSmith(String name, String smith);

    void addArmour(CreateArmourCommand command);

    void removeById(Long id);

    void updateArmour();

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
    }
}

