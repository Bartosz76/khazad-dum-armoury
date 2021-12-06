package bm.app.khazaddumarmoury.armour.web;

import bm.app.khazaddumarmoury.armour.application.port.ArmourUseCase;
import bm.app.khazaddumarmoury.armour.domain.Armour;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
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
     * Optional is a better version of "required = false" added to @RequestParam.
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
     * though. That param = {"name"} means that this one param is required!
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
        //This is one way to handle exceptions -> ResponseStatusException is an exception provided by Spring and
        //it handles itself! I don't need to write my own handler for it.
        if (id.equals(72L)) {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "No 72, because reasons.");
        }
        return armourUseCase
                .findById(id)
                .map(ResponseEntity::ok) //If the armour is present, I wrap it in my ResponseEntity.ok response...
                .orElse(ResponseEntity.notFound().build()); //If it's not present, the ResponseCode assumes the status of notFound.
                //.notFound() returns a different object than .ok() -> so it needs .build() as well.
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
     * @Valid makes Spring attempt to validate the input (I think it's thanks to jsr-303). I also need to specify
     * what "validate" is supposed to mean in practice -> it's done within the RestCreateArmourCommand itself!
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addArmour(@Valid @RequestBody RestCreateArmourCommand command) {
        Armour armour = armourUseCase.addArmour(command.toCommand());
        URI uri = createdArmourUri(armour); //Here I am building the URI!
        return ResponseEntity.created(uri).build();
    }

    /**
     * NO_CONTENT is a standard status to be returned when removing an object regardless of whether it was present
     * or not.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        armourUseCase.removeById(id);
    }

    private URI createdArmourUri(Armour armour) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + armour.getId().toString()).build().toUri();
    }

    /**
     * I can handle the exceptions caused by the creation of the RestCreateArmourCommand either here or
     * in a separate class. The lack of any of the arguments will yield "MethodArgumentNotValidException",
     * so that's what I will handle.
     * Exception is injected so I have access to its body and can extract data from it.
     * Note -> this works only if defined/called before the declaration of RestArmourCreateCommand.
     * If the ExceptionHandler method remains here, it will only handle the exceptions thrown within the functional
     * scope of this class. If I want it to more general purpose - I need to move it to another class...
     * Which I am just doing (it's in the CustomGlobalExceptionHandler class). I am leaving it here for
     * informational purposes.
     */
//    @ExceptionHandler(MethodArgumentNotValidException.class) // When this exception is thrown -> method will be called.
//    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex) {
//        Map<String, Object> body = new LinkedHashMap<>();
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//        //Below are unnecessary, but useful. Timestamp will tell me when the exception occurred.
//        body.put("timestamp", new Date());
//        body.put("status", status.value());
//        //To get the actual error...
//        List<String> errors = ex
//                .getBindingResult()
//                .getFieldErrors() //Extracting the errors...
//                .stream() //To open a stream to build String messages...
//                .map(x -> x.getField() + " - " + x.getDefaultMessage()) //That consist of the name of the field that had the error and the message what's wrong.
//                .collect(Collectors.toList());
//        //These messages (what's wrong) are currently default ones... Unless I add my custom message
//        //in the brackets next to @NotNull (and other) annotations within my RestArmourCreateCommand.
//        body.put("errors", errors);
//        return new ResponseEntity<>(body, status);
//    }

    /**
     * Another mini DTO. It is going to be used to avoid taking in and putting out pure entities.
     * @Data provides me with getters and setters. Spring requires them in order to be able to map the payload (the body
     * in JSON) into the instance of the class!
     * This has a prefix of 'Rest' because I already have CreateArmourCommand defined in the code and it's used for
     * the purpose of saving a new armour piece in my memory repository.
     * This one though will be used to save a new armour piece but from the web layer.
     *
     * I also need some validation! Without it I am just assuming the user's good will and just passing it forward
     * into the app. This could result, e.g. in a armour piece without the smith's name or a negative number for
     * a price.
     * I can utilize the project 'jsr-303' that will automatically validate the input.
     * @NotBlank, @NotNull, @DecimalMin -> are annotations given to me by this project.
     */
    @Data
    private static class RestCreateArmourCommand {
        @NotBlank(message = "Provide the name of the armour piece.")
        private String name;
        @NotBlank(message = "Provide the type of the armour piece.")
        private String type;
        @NotBlank(message = "Provide the name of the smith.")
        private String smith;
        @NotNull(message = "Provide the year of its creation.")
        private Integer year;
        @NotNull(message = "Provide the price.")
        @DecimalMin("0.00")
        private BigDecimal price;

        /**
         * I could use the previously defined CreateArmourCommand in my .addArmour() method, but RestCreateArmourCommand
         * might need another kind of validation. So I am creating RestCreateArmourCommand as a 'wrapper', a stage to
         * pass through on the way to return a CreateArmourCommand. So basically another layer of abstraction.
         */
        CreateArmourCommand toCommand() {
            return new CreateArmourCommand(name, type, smith, year, price);
        }
    }

}
