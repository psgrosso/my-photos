package model.photo.element;


import model.attribute.Attribute;
import model.attribute.AttributeMap;
import model.photo.PhotoKind;
import org.jetbrains.annotations.NotNull;

public class PhotoAlbum extends BasePhotoElement {

    public static final Attribute<Integer> F_MONTH = Attribute.newIntegerAttribute("month");
    public static final Attribute<Integer> F_DAY = Attribute.newIntegerAttribute("day");

    public PhotoAlbum(@NotNull PhotoElement parent, @NotNull AttributeMap attributeMap) {
        super(PhotoKind.ALBUM, attributeMap, parent);
    }

    public static AttributeMap attributeMapFor(String name, int month, int day) {
        return AttributeMap.builderFor(PhotoKind.ALBUM.getRequiredAttributes())
                            .with(F_NAME, name)
                            .with(F_MONTH, month)
                            .with(F_DAY, day)
                            .build();
    }
}
