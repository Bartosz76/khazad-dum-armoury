package bm.app.khazaddumarmoury.armour.infrastructure;

import bm.app.khazaddumarmoury.armour.domain.Armour;
import bm.app.khazaddumarmoury.armour.domain.ArmourRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
class TardhagharArmourRepository implements ArmourRepository {

    /**
     * An implementation of ArmourRepository. It's in the infrastructure as it is... an implementation,
     * it is not a part of the domain per se, but the way the part of the domain is implemented.
     * It's 'Memory', because for now, it's just an in-memory alternative to a database.
     */

    private final Map<Long, Armour> hoard = new ConcurrentHashMap<>(); // Safe in multi-threaded environment.

    public TardhagharArmourRepository() {
        hoard.put(1L, new Armour(1L, "Scarbrand", "Face mask", "Sordi Trygwasson", 2351));
        hoard.put(2L, new Armour(2L, "Bloodmyst", "Chainmail", "Gorn Warain", 1684));
        hoard.put(3L, new Armour(3L, "Yorehope", "Helmet", "Brandin Herryar", 1234));
        hoard.put(4L, new Armour(4L, "Snowcaps", "Shoulder pads", "Rerir Uzurakh", 1775));
    }

    @Override
    public List<Armour> findAll() {
        return new ArrayList<>(hoard.values());
    }
}
