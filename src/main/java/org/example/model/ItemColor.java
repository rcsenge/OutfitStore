package org.example.model;

public enum ItemColor {
    RED(1),
    BLUE(2),
    GREEN(3),
    YELLOW(4),
    ORANGE(5),
    PURPLE(6),
    PINK(7),
    BLACK(8),
    WHITE(9),
    GRAY(10),
    BROWN(11),
    TEAL(12),
    NAVY(13),
    MAROON(14),
    BEIGE(15),
    MULTI(16);

    private final int dbValue;


    ItemColor(int dbValue) {
        this.dbValue = dbValue;
    }

    public int getDbValue() {
        return dbValue;
    }

    public static ItemColor fromDbValue(int dbValue) {
        for (ItemColor color : ItemColor.values()) {
            if (color.dbValue == dbValue) {
                return color;
            }
        }
        throw new IllegalArgumentException("No matching ItemColor for value " + dbValue);
    }
}
