package com.mediasoft.restaurant.entity;

public enum CuisineType {
    EUROPEAN("Европейская"),
    ITALIAN("Итальянская"),
    CHINESE("Китайская"),
    JAPANESE("Японская"),
    INDIAN("Индийская"),
    MEXICAN("Мексиканская"),
    RUSSIAN("Русская"),
    FRENCH("Французская"),
    THAI("Тайская"),
    MEDITERRANEAN("Средиземноморская");

    private final String displayName;

    CuisineType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 