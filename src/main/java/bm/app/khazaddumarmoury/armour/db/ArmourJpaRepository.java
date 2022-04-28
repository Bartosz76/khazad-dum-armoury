package bm.app.khazaddumarmoury.armour.db;

import bm.app.khazaddumarmoury.armour.domain.Armour;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JpaRepository is a generic interface that takes two parameters. The first one points at the entity the repository
 * is for and the second is the type of the main key.
 */
public interface ArmourJpaRepository extends JpaRepository<Armour, Long> {

}
