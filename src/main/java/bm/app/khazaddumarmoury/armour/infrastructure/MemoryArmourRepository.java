package bm.app.khazaddumarmoury.armour.infrastructure;

import bm.app.khazaddumarmoury.armour.domain.Armour;
import bm.app.khazaddumarmoury.armour.domain.ArmourRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryArmourRepository implements ArmourRepository {

    // An implementation of ArmourRepository. It's in the infrastructure as it is... an implementation,
    // it is not a part of the domain per se, but the way the part of the domain is implemented.
    // It's 'Memory', because for now, it's just an in-memory alternative to a database.

    private final Map<Long, Armour> hoard = new ConcurrentHashMap<>(); // Safe in multi-threaded environment.

    public MemoryArmourRepository() {
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
