package model.comparator;

import model.photo.element.PhotoCollection;
import model.photo.element.PhotoElement;
import model.photo.identifier.PhotoIdentifier;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static model.comparator.PhotoComparatorResult.Status;


public final class PhotoComparator {
    private PhotoCollection sourceCollection;
    private PhotoCollection targetCollection;

    public PhotoComparator(@NotNull PhotoCollection sourceCollection, @NotNull PhotoCollection targetCollection) {
        this.sourceCollection = sourceCollection;
        this.targetCollection = targetCollection;
    }

    /**
     * Compares the provided photo collections starting at the specified root photo element
     * At the very least, the root element must exist for source OR target collection, ideally will exist on both
     * @param rootPhotoElementIdentifier the root photo element in which the comparison will start
     */
    public List<PhotoComparatorResult> compare(@NotNull PhotoIdentifier rootPhotoElementIdentifier) {
        PhotoElement sourceRoot = sourceCollection.findChild(rootPhotoElementIdentifier);
        PhotoElement targetRoot = targetCollection.findChild(rootPhotoElementIdentifier);
        List<PhotoComparatorResult> result = new LinkedList<>();
        compare(sourceRoot, targetRoot, result);
        return result;
    }

    private int compare(PhotoElement sourceElement, PhotoElement targetElement,
                        @NotNull List<PhotoComparatorResult> result) {
        int comparison;
        if (sourceElement == null) {
            if (targetElement == null) {
                throw new IllegalArgumentException("Both photo elements are null");
            }
            // Force target selection
            comparison = 1;
        }
        else if (targetElement == null) {
            // Force source selection
            comparison = -1;
        }
        else {
            comparison = sourceElement.compareTo(targetElement);
        }
        if (comparison == 0) {
            // Both have the same local identifier, now compare all attributes
            if (!sourceElement.equals(targetElement)) {
                result.add(new PhotoComparatorResult(sourceElement.getIdentifier(),
                        PhotoComparatorResult.Status.DIFFERENT));
            }
            // Children iteration
            Iterator<PhotoElement> sourceIterator = sourceElement.iterator();
            Iterator<PhotoElement> targetIterator = targetElement.iterator();
            int childComparison = 0;
            PhotoElement sourceChild = null;
            PhotoElement targetChild = null;
            while (true) {
                if (childComparison <= 0) {
                    // Source child should be moved forward
                    sourceChild = sourceIterator.hasNext() ? sourceIterator.next() : null;
                }
                if (childComparison >= 0) {
                    // Target child should be moved forward
                    targetChild = targetIterator.hasNext() ? targetIterator.next() : null;
                }
                if (sourceChild == null && targetChild == null) {
                    break;
                }
                childComparison = compare(sourceChild, targetChild, result);
            }
        }
        else {
            PhotoElement element = (comparison < 0) ? sourceElement : targetElement;
            Status status = (comparison < 0) ? Status.NEW_SOURCE : Status.NEW_TARGET;
            result.add(new PhotoComparatorResult(element.getIdentifier(), status));
            for (PhotoElement child : element) {
                compare((comparison < 0) ? child : null, (comparison > 0) ? null : child, result);
            }
        }
        return comparison;
    }
}
