package datta.core.paper.games.lastday.enums;

import lombok.Getter;

import java.util.List;

public class LastDayColor {

    @Getter
    public String name;
    @Getter
    public String[] colors;

    @Getter
    public String firstColor;

    @Getter
    public String secondColor;


    public LastDayColor(String name, String... colors) {
        this.name = name;
        this.colors = colors;

        this.firstColor = colors[0];
        this.secondColor = colors[1];
    }

    public static LastDayColor fetchColor(String name, List<LastDayColor> list) {
        for (LastDayColor color : list) {
            if (color.getName().equalsIgnoreCase(name)) {
                return color;
            }
        }

        return null;
    }
}
