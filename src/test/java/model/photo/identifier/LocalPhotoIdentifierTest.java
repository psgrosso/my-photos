package model.photo.identifier;

import model.photo.element.Photo;
import model.photo.element.PhotoElement;
import org.testng.annotations.Test;

import static model.photo.PhotoKind.*;
import static org.testng.AssertJUnit.*;
import static util.PhotoElementUtils.*;

public class LocalPhotoIdentifierTest {

    @Test
    public void testCollection() {
        PhotoElement collection = createCollection();
        LocalPhotoIdentifier identifier = collection.getIdentifier().getLocalIdentifier();
        System.out.println("Testing: " + identifier);

        assertNotNull(identifier);
        assertEquals(identifier.getKind(), COLLECTION);
        assertEquals(identifier, createCollection().getIdentifier().getLocalIdentifier());
        assertEquals(0, identifier.compareTo(identifier));

        // Comparing photo elements is the same as comparing their local identifier
        PhotoElement newCollection = createCollection("zzzzz");
        assertTrue(collection.compareTo(newCollection) < 0);
        assertTrue(newCollection.compareTo(collection) > 0);
        LocalPhotoIdentifier newIdentifier = newCollection.getIdentifier().getLocalIdentifier();
        assertTrue(collection.getIdentifier().getLocalIdentifier().compareTo(newIdentifier) < 0);
        assertEquals(newIdentifier, new LocalPhotoIdentifier(COLLECTION, COLLECTION.valuesBuilderFor("zzzzz").build()));
    }

    @Test
    public void testPhoto() {
        PhotoElement photo = createPhoto();
        LocalPhotoIdentifier identifier = photo.getIdentifier().getLocalIdentifier();
        assertNotNull(identifier);
        assertEquals(identifier.getKind(), PHOTO);
        assertEquals(identifier, new Photo(createAlbum(), PHOTO_VALUES).getIdentifier().getLocalIdentifier());
        assertEquals(0, identifier.compareTo(identifier));

        PhotoElement parent = createAlbum();
        PhotoElement seqPhoto1 = createSeqPhoto(parent);
        PhotoElement seqPhoto2 = createSeqPhoto(parent);
        assertTrue(seqPhoto1.compareTo(seqPhoto2) < 0);
        assertTrue(seqPhoto2.compareTo(seqPhoto1) > 0);
        assertTrue(seqPhoto1.getIdentifier().getLocalIdentifier().compareTo(
                seqPhoto2.getIdentifier().getLocalIdentifier()) < 0);

        // Manually create an identifier to compare against, size shouldn't be part of the identifier
        identifier = new LocalPhotoIdentifier(PHOTO, newNameValue(seqPhoto1.getName()));
        assertEquals(seqPhoto1.getIdentifier().getLocalIdentifier(), identifier);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidComparison() {
        createYear(1973).getIdentifier().getLocalIdentifier().compareTo(
                createAlbum().getIdentifier().getLocalIdentifier());
    }
}
