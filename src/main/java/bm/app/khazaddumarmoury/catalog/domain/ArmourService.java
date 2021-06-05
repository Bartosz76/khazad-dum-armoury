package bm.app.khazaddumarmoury.catalog.domain;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArmourService {

    private final ArmourRepository armourRepository;

    public ArmourService(ArmourRepository armourRepository) {
        this.armourRepository = armourRepository;
    }

    public List<Armour> findByName(String name) {
        return armourRepository.findAll()
                .stream()
                .filter(armour -> armour.name.startsWith(name))
                .collect(Collectors.toList());
    }

}
