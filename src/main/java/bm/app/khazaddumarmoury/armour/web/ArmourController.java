package bm.app.khazaddumarmoury.armour.web;

import bm.app.khazaddumarmoury.armour.application.port.ArmourUseCase;
import bm.app.khazaddumarmoury.armour.domain.Armour;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /**
     * HttpStatus.OK is returned by default.
     * If I add /?name=??? or /?smith=??? to my URL, that's query param in action.
     * An URL like /?name=mirro&smith=bro will also run properly. That's because the logic is based on
     * .contains() so just parts of the Strings will suffice.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Armour> getAll(@RequestParam Optional<String> name,
                               @RequestParam Optional<String> smith,
                               @RequestParam(defaultValue = "10") int limit) { // I can also limit the records displayed!
        if (name.isPresent() && smith.isPresent()) {
            return armourUseCase.findByNameAndSmith(name.get(), smith.get());
        } else if (name.isPresent()) {
            return armourUseCase.findByName(name.get());
        } else if (smith.isPresent()) {
            return armourUseCase.findBySmith(smith.get());
        } else {
            return armourUseCase.findAll().stream().limit(limit).collect(Collectors.toList()); // Utilizing the limit param!
        }
    }

    /**
     * If I want a parameter to be optional, I can use @RequestParam(required = false)... or I can use an Optional.
     * I am moving the below logic to the method getAll() above. Don't want to lose param = something construction
     * though.
     */
//    @GetMapping(params = {"name"}) //A query param - to be added to the URL as /?name = providedName (e.g. Mirrorwrath).
//    @ResponseStatus(HttpStatus.OK)
//    public List<Armour> getAllFiltered(@RequestParam Optional<String> name) {
//        return armourUseCase.findByName(name);
//    }

    /**
     * ResponseEntity allows me to define what headers, body or status code I am returning. I can create it by a
     * constructor or by builders.
     * I can use either ResponseStatus annotation when the status will always be the same or I can use ResponseEntity
     * when I want to define the response codes dynamically.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return armourUseCase
                .findById(id)
                .map(ResponseEntity::ok) //If the armour is present, I wrap it in my ResponseEntity.ok response...
                .orElse(ResponseEntity.notFound().build()); //If it's not present, the ResponseCode assumes the status of notFound.
    }

}
