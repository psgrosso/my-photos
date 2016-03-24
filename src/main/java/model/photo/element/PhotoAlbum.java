package model.photo.element;


import model.attribute.Attribute;
import model.attribute.Value;
import model.attribute.Values;
import model.photo.PhotoKind;
import org.jetbrains.annotations.NotNull;

public final class PhotoAlbum extends BasePhotoElement {

    public static final Attribute F_MONTH = Attribute.newIntegerAttribute("month");
    public static final Attribute F_DAY = Attribute.newIntegerAttribute("day");

    public PhotoAlbum(@NotNull PhotoElement parent, @NotNull Values values) {
        super(parent, PhotoKind.ALBUM, values);
    }

    public static Values valuesFor(@NotNull String name, int month, int day) {
        return PhotoKind.ALBUM.valuesBuilderFor(name)
                            .with(new Value(F_MONTH, month))
                            .with(new Value(F_DAY, day))
                            .build();
    }
}
