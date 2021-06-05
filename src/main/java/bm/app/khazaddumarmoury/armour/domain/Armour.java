package bm.app.khazaddumarmoury.armour.domain;

import lombok.*;

@ToString
@RequiredArgsConstructor
@Getter
public class Armour {

    private final Long id;
    private final String name;
    private final String type;
    private final String smith;
    private final Integer year;

}
