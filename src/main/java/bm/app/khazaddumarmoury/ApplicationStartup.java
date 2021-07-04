package bm.app.khazaddumarmoury;

import bm.app.khazaddumarmoury.armour.application.port.ArmourUseCase;
import bm.app.khazaddumarmoury.armour.application.port.ArmourUseCase.CreateArmourCommand;
import bm.app.khazaddumarmoury.armour.domain.Armour;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static bm.app.khazaddumarmoury.armour.application.port.ArmourUseCase.*;

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
        initData();
        searchArmour();
        placeOrder();
    }

    private void placeOrder() {
    }

    private void searchArmour() {
        findArmourSetsByName();
        findArmourSetsBySmith();
        findAndUpdate();
        findArmourSetsByName();
        findArmourSetsBySmith();
    }

    private void initData() {
        armourUseCase.addArmour(new CreateArmourCommand("Mirrormere Plate", "Full Plate", "Snorri Haggesson", 2354));
        armourUseCase.addArmour(new CreateArmourCommand("Darkstar", "Helmet", "Nain Dainsson", 1984));
        armourUseCase.addArmour(new CreateArmourCommand("Tramplers", "Sabatons", "Leifi Grvaldsson", 1956));
        armourUseCase.addArmour(new CreateArmourCommand("Mirrorrift", "Breastplate", "Brok Targoghar", 1476));
    }

    private void findArmourSetsBySmith() {
        List<Armour> armourSetsBySmith = armourUseCase.findByName(name);
        armourSetsBySmith.stream().limit(limit).forEach(System.out::println);
    }

    private void findArmourSetsByName() {
        List<Armour> armourSetsByName = armourUseCase.findBySmith(smith);
        armourSetsByName.stream().limit(limit).forEach(System.out::println);
    }

    private void findAndUpdate() {
        System.out.println("Updating armour...");
        armourUseCase.findOneByNameAndSmith("Mirrorrift", "Brok Targoghar")
                .ifPresent(armour -> {
                    UpdateArmourCommand command = UpdateArmourCommand.builder()
                            .id(armour.getId())
                            .name("Mirrorwrath")
                            .build();
                    armourUseCase.updateArmour(command);
                    UpdateArmourResponse response = armourUseCase.updateArmour(command);
                    System.out.println("Updating the armour with result: " + response.isSuccess());
                });
    }


}
