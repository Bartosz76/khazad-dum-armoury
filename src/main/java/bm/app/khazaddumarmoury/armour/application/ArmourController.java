package bm.app.khazaddumarmoury.armour.application;

import bm.app.khazaddumarmoury.armour.domain.Armour;
import bm.app.khazaddumarmoury.armour.domain.ArmourService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArmourController {
    /**
     * Controllers go into application layer.
     */

    private final ArmourService armourService;

    public List<Armour> findByName(String name) {
        return armourService.findByName(name);
    }

    public List<Armour> findBySmith(String smith) {
        return armourService.findBySmith(smith);
    }
}
