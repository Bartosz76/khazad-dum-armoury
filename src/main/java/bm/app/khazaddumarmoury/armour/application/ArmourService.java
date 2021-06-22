package bm.app.khazaddumarmoury.armour.application;

import bm.app.khazaddumarmoury.armour.application.port.ArmourUseCase;
import bm.app.khazaddumarmoury.armour.domain.Armour;
import bm.app.khazaddumarmoury.armour.domain.ArmourRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class ArmourService implements ArmourUseCase {

    private final ArmourRepository armourRepository;

    /**
     * In case there are two implementations of ArmourRepository (there are), the @Qualifier
     * allows me to determine which one of the implementations is going to be injected.
     * What I provide as a String is the bean's name.
     */

//    Only one implementation of the repository remains so below is not necessary.
//    Thanks to @AllArgsConstructor, Spring will automatically inject the only implementation
//    of the ArmourRepository it has.

//    public ArmourService(@Qualifier("memoryArmourRepository") ArmourRepository armourRepository) {
//        this.armourRepository = armourRepository;
//    }

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
        return armourRepository.findAll();
    }

    @Override
    public Optional<Armour> findOneByNameAndSmith(String name, String smith) {
        return armourRepository.findAll()
                               .stream()
                               .filter(armour -> armour.getName().startsWith(name))
                               .filter(armour -> armour.getSmith().startsWith(smith))
                               .findFirst();
    }

    /**
     * Technically, I can just take Armour as a parameter and automatically load it into the "DB",
     * but would prevent the logic, like validation, from being used here. I would need to do it
     * elsewhere. I'd prefer to validate the parameters before creating an object.
     * I can also just take in all the parameters separately... Or I can take one argument - a Commend.
     */
    @Override
    public void addArmour(CreateArmourCommand command) {
        Armour armour = new Armour(command.getName(), command.getType(), command.getSmith(), command.getYear());
        armourRepository.save(armour);
    }

    @Override
    public void removeById(Long id) {

    }

    @Override
    public void updateArmour() {

    }
}
