package bm.app.khazaddumarmoury;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KhazadDumArmouryApplication {

	public static void main(String[] args) {
		SpringApplication.run(KhazadDumArmouryApplication.class, args);
	}

	// The below method is yet another variant of solving the conflict of two available
	// implementations of the repository interface that is being called by the service.
	// Instead of making both of those implementations beans, I can just leave them be,
	// and specify a @Bean method here, returning the repository of my choosing out of
	// the two.
//	@Bean
//	ArmourService armourService() {
//		return new TardhagharArmourRepository();
//	}

//	@Bean
//	String query() {
//		return "Mirror";
//	}
//
}
