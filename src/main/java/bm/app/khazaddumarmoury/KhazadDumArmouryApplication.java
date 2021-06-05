package bm.app.khazaddumarmoury;

import bm.app.khazaddumarmoury.catalog.domain.Armour;
import bm.app.khazaddumarmoury.catalog.domain.ArmourService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class KhazadDumArmouryApplication implements CommandLineRunner {
	// CommandLineRunner will allow me to have a method that will run the moment my application
	// is started. When Spring Context, the entire Spring application is up, the code defined in
	// the run method will be activated.

	public static void main(String[] args) {
		SpringApplication.run(KhazadDumArmouryApplication.class, args);
	}

	private final ArmourService armourService;

	public KhazadDumArmouryApplication(ArmourService armourService) {
		this.armourService = armourService;
	}

	@Override
	public void run(String... args) throws Exception {
		List<Armour> armourSets = armourService.findByName("Mirrormere Plate");
		armourSets.forEach(System.out::println);
	}
}
