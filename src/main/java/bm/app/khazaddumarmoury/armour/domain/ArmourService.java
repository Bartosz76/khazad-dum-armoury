package bm.app.khazaddumarmoury.armour.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArmourService {

    private final ArmourRepository armourRepository;

    public List<Armour> findByName(String name) {
        return armourRepository.findAll()
                .stream()
                .filter(armour -> armour.getName().startsWith(name))
                .collect(Collectors.toList());
    }

}
