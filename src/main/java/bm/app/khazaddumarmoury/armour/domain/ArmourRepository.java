package bm.app.khazaddumarmoury.armour.domain;

import java.util.List;
import java.util.Optional;

public interface ArmourRepository {

    List<Armour> findAll();

    void save(Armour armour);

    Optional<Armour> findById(Long id);

    void removeById(Long id);
}
