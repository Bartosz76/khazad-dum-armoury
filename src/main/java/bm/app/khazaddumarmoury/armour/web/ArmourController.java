package bm.app.khazaddumarmoury.armour.web;

import bm.app.khazaddumarmoury.armour.application.port.ArmourUseCase;
import bm.app.khazaddumarmoury.armour.domain.Armour;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static bm.app.khazaddumarmoury.armour.application.port.ArmourUseCase.*;

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

    /**
     * Apart from just adding a new armour piece, I could also return the path to that new record while at it. I changed
     * the return type of .addArmour() methods in the service and repository (and their interfaces, obviously) to
     * Armour instead of void, because thanks to that - I will be able to get that new record's id!
     * It's a good practice in REST, to return the header with the location of a resource (so its id) -> so it is
     * known where the new record can be found.
     * In order to return the path to my new endpoint (the location of the file, the path with its id at the end!), I
     * need to use ServletUriComponentBuilder.
     * ResponseEntity has <Void> because it doesn't return any body, it only returns the header. I think that <?>
     * would also work but <Void> might be more telling.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addArmour(@RequestBody RestCreateArmourCommand command) {
        Armour armour = armourUseCase.addArmour(command.toCommand());
        URI uri = createdArmourUri(armour); //Here I am building the URI!
        return ResponseEntity.created(uri).build();
    }

    private URI createdArmourUri(Armour armour) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + armour.getId().toString()).build().toUri();
    }

    /**
     * Another mini DTO. It is going to be used to avoid taking in and putting out pure entities.
     * @Data provides me with getters and setters. Spring requires them in order to be able to map the payload (the body
     * in JSON) into the instance of the class!
     * This has a prefix of 'Rest' because I already have CreateArmourCommand defined in the code and it's used for
     * the purpose of saving a new armour piece in my memory repository.
     * This one though will be used to save a new armour piece but from the web layer.
     */
    @Data
    private static class RestCreateArmourCommand {
        private String name;
        private String type;
        private String smith;
        private Integer year;
        private BigDecimal price;

        /**
         * I could use the previously defined CreateArmourCommand in my .addArmour() method, but RestCreateArmourCommand
         * might need another kind of validation. So I am creating RestCreateArmourCommand as a 'wrapper', a stage to
         * pass through on the way to return a CreateArmourCommand.
         */
        CreateArmourCommand toCommand() {
            return new CreateArmourCommand(name, type, smith, year, price);
        }
    }

}
