package bm.app.khazaddumarmoury.armour.application;

import bm.app.khazaddumarmoury.armour.application.port.ArmourUseCase;
import bm.app.khazaddumarmoury.armour.domain.Armour;
import bm.app.khazaddumarmoury.armour.domain.ArmourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class ArmourService implements ArmourUseCase {

    private final ArmourRepository armourRepository;

    /**
     * In case there are two implementations of ArmourRepository (there are), the @Qualifier
     * allows me to determine which one of the implementations is going to be injected.
     * What I provide as a String is the bean's name.
     */

    public ArmourService(@Qualifier("dumuzdinArmourRepository") ArmourRepository armourRepository) {
        this.armourRepository = armourRepository;
    }

    @Override
    public List<Armour> findByName(String name) {
        return armourRepository.findAll()
                .stream()
                .filter(armour -> armour.getName().startsWith(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Armour> findBySmith(String smith) {
        return armourRepository.findAll()
                .stream()
                .filter(armour -> armour.getSmith().startsWith(smith))
                .collect(Collectors.toList());
    }

    /**
     * Adding a few CRUD methods. It's good to communicate via interfaces between different
     * application's layers because I am separating the concrete implementation from the
     * "blueprint" -> makes my application more flexible and easier to expand (OCP).
     * Hence, the ArmourUseCase interface of which this Service is a concrete implementation.
     */

    @Override
    public List<Armour> findAll() {
        return null;
    }

    @Override
    public Optional<Armour> findOneByNameAndSmith(String name, String smith) {
        return Optional.empty();
    }

    @Override
    public void addArmour() {

    }

    @Override
    public void removeById(Long id) {

    }

    @Override
    public void updateArmour() {

    }
}
