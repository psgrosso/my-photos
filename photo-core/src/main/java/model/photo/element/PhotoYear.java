package model.photo.element;

import model.attribute.Values;
import model.photo.PhotoKind;
import org.jetbrains.annotations.NotNull;


public final class PhotoYear extends BasePhotoElement {
    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2100;

    private final int year;

    public PhotoYear(@NotNull PhotoElement parent, @NotNull Values values) {
        super(parent, PhotoKind.YEAR, values);
        year = parseYear(values.get(F_NAME).getString());
    }

    public int getYear() {
        return year;
    }

    public static int parseYear(@NotNull String yearValue) {
        try {
            int year = Integer.parseInt(yearValue);
            if (year < MIN_YEAR || year > MAX_YEAR) {
                throw new IllegalArgumentException("Year is out of range: " + year);
            }
            return year;
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid year: " + yearValue);
        }
    }
}
