package ai.openbanking.OpenBanking.model;

import lombok.Getter;

@Getter
public enum Category {
    boodschappen("boodschappen"),
    consumptie("consumptie"),
    educatie("educatie"),
    huishouden("huishouden"),
    inkomsten("inkomsten"),
    kleding("kleding"),
    medischeKosten("medische kosten"),
    overigeUitgaven("overige uitgaven"),
    telecom("telecom"),
    vervoer("vervoer"),
    verzekeringen("verzekeringen"),
    vrijeTijd("vrije tijd");

    private String label;

    Category(String label) {
        this.label = label;
    }

    public static Category fromString(String label) {
        for (Category c : Category.values()) {
            if (c.label.equalsIgnoreCase(label)) {
                return c;
            }
        }
        throw new IllegalArgumentException("No constant with text " + label + " found");
    }
}
