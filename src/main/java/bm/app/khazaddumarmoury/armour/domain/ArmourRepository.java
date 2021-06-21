package bm.app.khazaddumarmoury.armour.domain;

import java.util.List;

public interface ArmourRepository {

    List<Armour> findAll();

    void save(Armour armour);
}
