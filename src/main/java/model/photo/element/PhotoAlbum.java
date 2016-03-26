package model.photo.element;


import model.attribute.Attribute;
import model.attribute.Value;
import model.attribute.Values;
import model.photo.PhotoKind;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;

public final class PhotoAlbum extends BasePhotoElement {

    public static final Attribute F_YEAR = Attribute.newIntegerAttribute("year");
    public static final Attribute F_MONTH = Attribute.newIntegerAttribute("month");
    public static final Attribute F_DAY = Attribute.newIntegerAttribute("day");

    public PhotoAlbum(@NotNull PhotoElement parent, @NotNull Values values) {
        super(parent, PhotoKind.ALBUM, values);
        PhotoYear photoYear = (PhotoYear) parent;
        int year = values.get(F_YEAR).getInteger();
        if (year != photoYear.getYear()) {
            throw new IllegalArgumentException("Invalid album year: " + year + ", expected: " + photoYear.getYear());
        }
    }

    /**
     * Returns a Values suitable for an album.
     * It checks whether the attributes specified are valid for an album.
     * Returns null if there is an error in the attributes
     * @param name the name of the album
     * @param year the year
     * @param month the month
     * @param day the day
     * @return a Values representing these attributes or null in case of error
     */
    public static Values valuesFor(@NotNull String name, int year, int month, int day) {
        if (!isDateValid(year, month, day)) {
            return null;
        }
        return PhotoKind.ALBUM.valuesBuilderFor(name)
                            .with(new Value(F_YEAR, year))
                            .with(new Value(F_MONTH, month))
                            .with(new Value(F_DAY, day))
                            .build();
    }

    private static boolean isDateValid(int year, int month, int day) {
        if (month < 1 || month > 12) {
            return false;
        }
        final Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        calendar.clear();
        calendar.set(year, month - 1, day);

        // Check that it is a valid date, and is not in the future
        try {
            if (calendar.get(Calendar.DATE) != day || calendar.get(Calendar.MONTH) + 1 != month ||
                    calendar.get(Calendar.YEAR) != year || calendar.getTime().compareTo(new Date()) > 0) {
                return false;
            }
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
