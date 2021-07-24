package bm.app.khazaddumarmoury.armour.web;

import bm.app.khazaddumarmoury.armour.application.port.ArmourUseCase;
import bm.app.khazaddumarmoury.armour.domain.Armour;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * HttpStatus.OK jest zwracany przez metody domyślnie.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Armour> getAll() {
        return armourUseCase.findAll();
    }

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
