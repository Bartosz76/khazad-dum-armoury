package bm.app.khazaddumarmoury.armour.application.port;

import bm.app.khazaddumarmoury.armour.domain.Armour;

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

    public List<Armour> findByName(String name);

    public List<Armour> findBySmith(String smith);

    public List<Armour> findAll();

    public Optional<Armour> findOneByNameAndSmith(String name, String smith);

    public void addArmour();

    public void removeById(Long id);

    public void updateArmour();
}
