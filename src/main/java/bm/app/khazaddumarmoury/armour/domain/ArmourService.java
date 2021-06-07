package bm.app.khazaddumarmoury.armour.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArmourService {

    private final ArmourRepository armourRepository;

    /**
     * In case there are two implementations of ArmourRepository (there are), the @Qualifier
     * allows me to determine which one of the implementations is going to be injected.
     * What I provide as a String is the bean's name.
     */

    public ArmourService(@Qualifier("dumuzdinArmourRepository") ArmourRepository armourRepository) {
        this.armourRepository = armourRepository;
    }

    public List<Armour> findByName(String name) {
        return armourRepository.findAll()
                .stream()
                .filter(armour -> armour.getName().startsWith(name))
                .collect(Collectors.toList());
    }

}
