package kingsbutbad.kingsbutbad.utils;

import org.jetbrains.annotations.NotNull;

public enum Seasons {
    SPRING("<#00FF7F>Spring"),
    WINTER("<#ADD8E6>Winter"),
    SUMMER("<#FFD700>Summer"),
    AUTUMN("<#FF8C00>Autumn");
    private final String colorName;
    Seasons(String colorName) {
        this.colorName = colorName;
    }
    public @NotNull String getColorName() {
        return CreateText.addColors(colorName);
    }
}
