package bm.app.khazaddumarmoury;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ArmourService {

    private final Map<Long, Armour> hoard = new ConcurrentHashMap<>(); // Will work safely in a multi-thread environment.

    public ArmourService() {
        hoard.put(1L, new Armour(1L, "Mirrormere Plate", "Full Plate", "Snorri Haggesson", 2354));
        hoard.put(2L, new Armour(2L, "Darkstar", "Helmet", "Nain Dainsson", 1984));
        hoard.put(3L, new Armour(3L, "Tramplers", "Sabatons", "Leifi Grvaldsson", 1956));
    }

    List<Armour> findByName(String name) {
        return hoard.values()
                .stream()
                .filter(armour -> armour.name.startsWith(name))
                .collect(Collectors.toList());
    }

}
