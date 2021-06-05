package bm.app.khazaddumarmoury.catalog.application;

import bm.app.khazaddumarmoury.catalog.domain.Armour;
import bm.app.khazaddumarmoury.catalog.domain.ArmourService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ArmourController {
    // Controllers go into application layer.

    private ArmourService armourService;

    public ArmourController(ArmourService armourService) {
        this.armourService = armourService;
    }

    public List<Armour> findByName(String name) {
        return armourService.findByName(name);
    }
}
