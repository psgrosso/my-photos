package model.comparator;

import model.photo.element.PhotoCollection;
import model.photo.element.PhotoElement;
import model.photo.identifier.PhotoIdentifier;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;


public final class PhotoComparator {
    private PhotoCollection sourceCollection;
    private PhotoCollection targetCollection;

    public PhotoComparator(@NotNull PhotoCollection sourceCollection, @NotNull PhotoCollection targetCollection) {
        this.sourceCollection = sourceCollection;
        this.targetCollection = targetCollection;
    }

    /**
     * Compares the provided photo collections starting at the specified root photo element
     * At the very least, the root element must exist in the source photo collection
     * @param rootPhotoElementIdentifier the root photo element in which the comparison will start
     */
    public void compare(@NotNull PhotoIdentifier rootPhotoElementIdentifier) {
        PhotoElement sourceRoot = sourceCollection.findChild(rootPhotoElementIdentifier);
        PhotoElement targetRoot = targetCollection.findChild(rootPhotoElementIdentifier);

        for (PhotoElement sourceElement : sourceRoot) {

        }
    }

    public int compare(PhotoElement sourceElement, PhotoElement targetElement) {
        int comparison = sourceElement.compareTo(targetElement);
        if (comparison == 0) {
            // Now compare all attributes
            new PhotoElementResult(targetElement, sourceElement.equals(targetElement) ?
                    PhotoElementResult.Status.EQUALS : PhotoElementResult.Status.NOT_EQUAL);
            Iterator<PhotoElement> sourceIterator = sourceElement.iterator();
            Iterator<PhotoElement> targetIterator = targetElement.iterator();
            while (sourceIterator.hasNext()) {
                if (targetIterator.hasNext()) {
                    int childComparison = compare(sourceIterator.next(), targetIterator.next());
                }
            }
        }
        else if (comparison < 0) {
            new PhotoElementResult(sourceElement, PhotoElementResult.Status.NEW_SOURCE);
        }
        else {
            new PhotoElementResult(sourceElement, PhotoElementResult.Status.NEW_TARGET);
        }
    }
}
