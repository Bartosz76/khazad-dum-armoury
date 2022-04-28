package bm.app.khazaddumarmoury.armour.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@ToString
@RequiredArgsConstructor
@Getter
@Setter
/**
 * An entity for the database.
 */
@Entity
public class Armour {

    /**
     * @Id - The primary key. Refers to the first variable below the annotation.
     * @GeneratedValue - A mechanism for the automated generation of Ids. Without it,
     * the app won't compile and will throw an error:
     * "ids for this class must be manually assigned before calling save()"
     * "strategy = GenerationType.AUTO" will pick the best generation mechanism
     * based on the type of the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    private String smith;
    private Integer year;
    private BigDecimal price;
    //An instance of an Armour will not hold the content of the file, but just the "pointer".
    private String paintingId;

    /**
     * Id is not included, because it is set by the repository.
     */
    public Armour(String name, String type, String smith, Integer year, BigDecimal price) {
        this.name = name;
        this.type = type;
        this.smith = smith;
        this.year = year;
        this.price = price;

    }

}
