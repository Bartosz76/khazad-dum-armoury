package bm.app.khazaddumarmoury.armour.domain;

import lombok.*;

import java.math.BigDecimal;

@ToString
@RequiredArgsConstructor
@Getter
@Setter
public class Armour {

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
