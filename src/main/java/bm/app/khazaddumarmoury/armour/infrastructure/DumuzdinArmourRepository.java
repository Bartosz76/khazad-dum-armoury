package bm.app.khazaddumarmoury.armour.infrastructure;

import bm.app.khazaddumarmoury.armour.domain.Armour;
import bm.app.khazaddumarmoury.armour.domain.ArmourRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
//@Primary <- Does exactly the same thing as @Qualified defined currently in ArmourService. In the case
//of two possible implementations of an interface, shows which one should be used.
public class DumuzdinArmourRepository implements ArmourRepository {
    // Another implementation of ArmourRepository class to see the conflict solving in a situation
    // when there are more than two implementations of a used interface - what happens when a class
    // has an ArmourRepository dependency and there are two implementations of it available.

    private final Map<Long, Armour> hoard = new ConcurrentHashMap<>(); // Safe in multi-threaded environment.

    public DumuzdinArmourRepository() {
        hoard.put(1L, new Armour(1L, "Mirrormere Plate", "Full Plate", "Snorri Haggesson", 2354));
        hoard.put(2L, new Armour(2L, "Darkstar", "Helmet", "Nain Dainsson", 1984));
        hoard.put(3L, new Armour(3L, "Tramplers", "Sabatons", "Leifi Grvaldsson", 1956));
        hoard.put(4L, new Armour(4L, "Mirrorrift", "Breastplate", "Brok Targoghar", 1476));
    }

    @Override
    public List<Armour> findAll() {
        return new ArrayList<>(hoard.values());
    }
}
