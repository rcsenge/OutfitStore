package org.example.model;

public enum Size {
    XXS(1),
    XS(2),
    S(3),
    M(4),
    L(5),
    XL(6);

    private final int dbValue;


    Size(int dbValue) {
        this.dbValue = dbValue;
    }

    public int getDbValue() {
        return dbValue;
    }

    public static Size fromDbValue(int dbValue) {
        for (Size size : Size.values()) {
            if (size.dbValue == dbValue) {
                return size;
            }
        }
        throw new IllegalArgumentException("No matching Size for value " + dbValue);
    }
}
