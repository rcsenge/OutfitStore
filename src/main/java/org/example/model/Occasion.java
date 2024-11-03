package org.example.model;

public enum Occasion {
    CASUAL(1),
    FORMAL(2),
    SEMIFORMAL(3),
    BUSINESS(4),
    COCKTAIL(5),
    EVENING(6),
    PARTY(7),
    WEDDING(8),
    BEACH(9),
    TRAVEL(10),
    SPORTS(11),
    LOUNGE(12),
    WORK(13),
    WEEKEND(14),
    HOLIDAY(15),
    DATE(16),
    FESTIVAL(17),
    OUTDOOR(18),
    ATHLETIC(19);

    private final int dbValue;

    Occasion(int dbValue) {
        this.dbValue = dbValue;
    }

    public int getDbValue() {
        return dbValue;
    }

    public static Occasion fromDbValue(int dbValue) {
        for (Occasion occasion : Occasion.values()) {
            if (occasion.dbValue == dbValue) {
                return occasion;
            }
        }
        throw new IllegalArgumentException("No matching Occasion for value " + dbValue);
    }
}
