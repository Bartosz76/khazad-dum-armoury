package bm.app.khazaddumarmoury;

import bm.app.khazaddumarmoury.armour.application.port.ArmourUseCase;
import bm.app.khazaddumarmoury.armour.domain.Armour;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {

    /**
     * This class exists as the initial implementation of the application. To see the logic in action.
     * It was created in order to keep the Main class clean.
     * CommandLineRunner will allow me to have a method that will run the moment my application
     * is started. When Spring Context, the entire Spring application is up, the code defined in
     * the run method will be activated.
     */

    /**
     * ApplicationStartup can access the service class through the 'port' of the layer
     * which holds my services!
     */
    private final ArmourUseCase armourUseCase;
    private final String name;
    private final String smith;
    private final Long limit;

    /**
     * Defining a constructor instead of @RequiredArgsConstructor, because I need to
     * augment the field 'name'.
     */
    public ApplicationStartup(ArmourUseCase armourUseCase,
                              @Value("${khazad.armoury.query}") String name, // this is defined in application.properties!
                              @Value("${khazad.armoury.second.query}") String smith,
                              @Value("${khazad.armoury.limit:3}") Long limit) {     // the limit of findings I want. It's defined in
        this.armourUseCase = armourUseCase;                 // application.properties too, but after the
        this.name = name;                                   // colon I specified the default value!
        this.smith = smith;
        this.limit = limit;
    }

    /**
     * I could also inject a primitive value like this:
     * @Bean
     * String query() {
     *     return "Mirror";
     * }
     * But that would not be a good solution, as, if there were more than a single
     * Bean String, Spring would not know what to do.
     */

    @Override
    public void run(String... args) throws Exception {
        List<Armour> armourSetsByName = armourUseCase.findByName(name);
        armourSetsByName.stream().limit(limit).forEach(System.out::println);

        List<Armour> armourSetsBySmith = armourUseCase.findBySmith(smith);
        armourSetsBySmith.stream().limit(limit).forEach(System.out::println);
    }

}
