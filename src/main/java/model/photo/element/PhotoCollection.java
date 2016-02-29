package model.photo.element;


import model.attribute.AttributeMap;
import model.photo.PhotoKind;
import org.jetbrains.annotations.NotNull;


public final class PhotoCollection extends BasePhotoElement {

    public PhotoCollection(@NotNull AttributeMap attributeMap) {
        super(PhotoKind.COLLECTION, attributeMap, null);
    }
}
