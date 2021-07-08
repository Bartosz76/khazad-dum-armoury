package bm.app.khazaddumarmoury.armour.web;

import bm.app.khazaddumarmoury.armour.application.port.ArmourUseCase;
import bm.app.khazaddumarmoury.armour.domain.Armour;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/armour")
@RestController // Registered as a Spring's bean!
@AllArgsConstructor
public class ArmourController {

    /**
     * My controller is to communicate with my domain layer through the application layer
     * (because hexagonal architecture).
     * Again, due to the hexagonal architecture -> I am not accessing ArmourService
     * directly... because ArmourUseCase is what I created to communicate with the layer.
     * ArmourService is not a public class... which is good.
     */
    private final ArmourUseCase armourUseCase;

    @GetMapping
    public List<Armour> getAll() {
        return armourUseCase.findAll();
    }
}
