package model.photo.element;

import com.google.common.collect.ImmutableList;
import model.attribute.Attribute;
import model.attribute.AttributeMap;
import model.attribute.AttributeSet;
import model.photo.PhotoKind;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;


public interface PhotoElement {
    // All photo elements have a name
    Attribute<String> F_NAME = Attribute.newStringAttribute("name");
    AttributeSet NAME_ATTRIBUTES = AttributeSet.of(PhotoElement.F_NAME);

    PhotoKind getKind();
    <T> T getAttribute(@NotNull final Attribute<T> key);
    PhotoElement getParent();
    ImmutableList<PhotoElement> getChildren();
    PhotoElement getChild(String childId);

    /**
     * Sets the children of this element, cannot be set once it was already set or getChildren was first called
     * @param children the children of this element
     */
    void setChildren(@NotNull Collection<PhotoElement> children);

    /**
     * Returns the AttributeMap subset that uniquely identifies this photo from its siblings
     * @return the id
     */
    AttributeMap getId();

    /**
     * Returns a unique global identifier
     * @return the unique ID
     */
    PhotoElementIdentifier getIdentifier();
}
