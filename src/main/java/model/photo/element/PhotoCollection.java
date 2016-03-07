package model.photo.element;

import model.attribute.AttributeValueMap;
import model.photo.PhotoKind;
import model.photo.identifier.LocalPhotoIdentifier;
import model.photo.identifier.PhotoIdentifier;
import org.jetbrains.annotations.NotNull;


public final class PhotoCollection extends BasePhotoElement {

    public PhotoCollection(@NotNull AttributeValueMap values) {
        super(null, PhotoKind.COLLECTION, values);
    }

    /**
     * Photo Collection is the starting point to find any child in the hierarchy
     * @param photoIdentifier the global unique identifier of the child to look for
     * @return the child or null if not found
     */
    public PhotoElement findChild(@NotNull PhotoIdentifier photoIdentifier) {
        PhotoElement current = null;
        for (LocalPhotoIdentifier localPhotoIdentifier : photoIdentifier) {
            if (current == null) {
                if (!getIdentifier().getLocalIdentifier().equals(localPhotoIdentifier)) {
                    return null;
                }
                current = this;
            }
            else {
                current = current.getChild(localPhotoIdentifier);
                if (current == null) {
                    break;
                }
            }
        }
        return current;
    }
}
