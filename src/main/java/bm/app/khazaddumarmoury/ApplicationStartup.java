package bm.app.khazaddumarmoury;

import bm.app.khazaddumarmoury.armour.application.ArmourController;
import bm.app.khazaddumarmoury.armour.domain.Armour;
import lombok.RequiredArgsConstructor;
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

    private final ArmourController armourController;
    private final String name;
    private final Long limit;

    /**
     * Defining a constructor instead of @RequiredArgsConstructor, because I need to
     * augment the field 'name'.
     */
    public ApplicationStartup(ArmourController armourController,
                              @Value("${khazad.armoury.query}") String name, // this is defined in application.properties!
                              @Value("${khazad.armoury.limit:3}") Long limit) { // the limit of findings I want. It's defined in
        this.armourController = armourController;                               // application.properties too, but after the
        this.name = name;                                                       // colon I specified the default value!
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
        List<Armour> armourSets = armourController.findByName(name);
        armourSets.stream().limit(limit).forEach(System.out::println);
    }
}
