package bm.app.khazaddumarmoury.armour.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Armour {

    /**
     * @Id - The primary key. Refers to the first variable below the annotation.
     * @GeneratedValue - A mechanism for the automated generation of Ids. Without it,
     * the app won't compile and will throw an error:
     * "ids for this class must be manually assigned before calling save()"
     * "strategy = GenerationType.AUTO" will pick the best generation mechanism
     * based on the type of the database. It is not required as it is a default value.
     */
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
