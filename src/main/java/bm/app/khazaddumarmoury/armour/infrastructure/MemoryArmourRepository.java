package bm.app.khazaddumarmoury.armour.infrastructure;

import bm.app.khazaddumarmoury.armour.domain.Armour;
import bm.app.khazaddumarmoury.armour.domain.ArmourRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
/**
 * @Primary <- Does exactly the same thing as @Qualified defined currently in ArmourService. In the case
 * of two possible implementations of an interface, shows which one should be used.
 */
class MemoryArmourRepository implements ArmourRepository {
    //Memory, because all data is stored in memory and it needs to be reloaded
    //during the application's restart.

    /**
     * Another implementation of ArmourRepository class to see the conflict solving in a situation
     * when there are more than two implementations of a used interface - what happens when a class
     * has an ArmourRepository dependency and there are two implementations of it available.
     */

    private final Map<Long, Armour> hoard = new ConcurrentHashMap<>(); // Safe in multi-threaded environment.
    private final AtomicLong ID_NEXT_VALUE = new AtomicLong(0L); // When I move to a DB, IDs will be assigned by the DB.
                                                                           // AtomicLong makes sure that, at all times, the ID will be unique.
    @Override
    public List<Armour> findAll() {
        return new ArrayList<>(hoard.values());
    }

    @Override
    public Armour save(Armour armour) {
        if (armour.getId() != null) {
            hoard.put(armour.getId(), armour);
        } else {
            long nextId = nextId();
            armour.setId(nextId);
            hoard.put(nextId, armour);
        }
        return armour;
    }

    @Override
    public Optional<Armour> findById(Long id) {
        return Optional.ofNullable(hoard.get(id));
    }

    @Override
    public void removeById(Long id) {
        hoard.remove(id);
    }

    private long nextId() {
        return ID_NEXT_VALUE.getAndIncrement();
    }
}
