package model.photo.element;

import model.attribute.Attribute;
import model.attribute.AttributeSet;
import model.attribute.Value;
import model.attribute.Values;
import model.photo.PhotoKind;
import model.photo.identifier.LocalPhotoIdentifier;
import model.photo.identifier.PhotoIdentifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;


/**
 * Interface that represents a photo element
 * PhotoElement is comparable to another photo element using their local identifier
 * It also behaves as an iterator for its children
 */
public interface PhotoElement extends Comparable<PhotoElement>, Iterable<PhotoElement> {
    // All photo elements have a name
    Attribute F_NAME = Attribute.newStringAttribute("name");
    AttributeSet NAME_ATTRIBUTES = AttributeSet.of(PhotoElement.F_NAME);

    PhotoKind getKind();
    Value getValue(@NotNull final Attribute key);
    PhotoElement getParent();
    PhotoElement getChild(@NotNull LocalPhotoIdentifier childId);

    /**
     * Sets the children of this element, cannot be set once it was already set or getChildren was first called
     * @param children the children of this element
     */
    void setChildren(@NotNull Collection<PhotoElement> children);

    /**
     * Returns a unique global identifier
     * @return the unique ID
     */
    PhotoIdentifier getIdentifier();
}
