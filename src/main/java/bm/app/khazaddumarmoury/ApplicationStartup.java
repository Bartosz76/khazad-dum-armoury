package bm.app.khazaddumarmoury;

import bm.app.khazaddumarmoury.armour.application.ArmourController;
import bm.app.khazaddumarmoury.armour.domain.Armour;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {
    // This class exists as the initial implementation of the application. To see the logic in action.
    // It was created in order to keep the Main class clean.

    // CommandLineRunner will allow me to have a method that will run the moment my application
    // is started. When Spring Context, the entire Spring application is up, the code defined in
    // the run method will be activated.

    private final ArmourController armourController;

    public ApplicationStartup(ArmourController armourController) {
        this.armourController = armourController;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Armour> armourSets = armourController.findByName("Mirror");
        armourSets.forEach(System.out::println);
    }
}
