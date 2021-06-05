package bm.app.khazaddumarmoury;

import java.util.StringJoiner;

public class Armour {

    Long id;
    String name;
    String type;
    String smith;
    Integer year;

    public Armour(Long id, String name, String type, String smith, Integer year) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.smith = smith;
        this.year = year;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Armour.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("type='" + type + "'")
                .add("smith='" + smith + "'")
                .add("year=" + year)
                .toString();
    }
}
