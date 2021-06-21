package bm.app.khazaddumarmoury.armour.domain;

import lombok.*;

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

    /**
     * Id is not included, because it is set by the repository.
     */
    public Armour(String name, String type, String smith, Integer year) {
        this.name = name;
        this.type = type;
        this.smith = smith;
        this.year = year;
    }
}
