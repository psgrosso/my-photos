package model.photo.element;

import model.attribute.AttributeMap;
import model.photo.PhotoKind;
import org.jetbrains.annotations.NotNull;


public final class PhotoYear extends BasePhotoElement {
    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2100;

    private final int year;

    public PhotoYear(@NotNull PhotoElement parent, @NotNull AttributeMap attributeMap) {
        super(PhotoKind.YEAR, attributeMap, parent);
        String stringYear = attributeMap.get(F_NAME);
        try {
            int integerYear = Integer.parseInt(stringYear);
            if (integerYear < MIN_YEAR || integerYear > MAX_YEAR) {
                throw new IllegalArgumentException("Year is out of range: " + integerYear);
            }
            year = integerYear;
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid year: " + stringYear);
        }
    }

    public int getYear() {
        return year;
    }
}
