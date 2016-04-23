package model.photo.identifier;

import model.photo.element.PhotoElement;
import org.testng.annotations.Test;

import java.util.Iterator;

import static model.photo.PhotoKind.*;
import static org.testng.AssertJUnit.*;
import static util.PhotoElementUtilsTest.*;

public class PhotoIdentifierTest {

    @Test
    public void testCollectionIdentifier() {
        PhotoElement collection = createCollection();
        PhotoIdentifier identifier = collection.getIdentifier();
        System.out.println("Testing: " + identifier);

        assertNotNull(identifier);
        assertEquals(identifier.getKind(), COLLECTION);
        assertEquals(identifier, createCollection().getIdentifier());

        // Iterator test
        Iterator<LocalPhotoIdentifier> iterator = identifier.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(collection.getIdentifier().getLocalIdentifier(), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testYearIdentifier() {
        PhotoElement year = createYear();
        PhotoIdentifier identifier = year.getIdentifier();

        assertNotNull(identifier);
        assertEquals(identifier.getKind(), YEAR);
        assertEquals(identifier, createYear().getIdentifier());

        // Iterator test
        Iterator<LocalPhotoIdentifier> iterator = identifier.iterator();
        assertTrue(iterator.hasNext());
        // First one is the collection
        assertEquals(createCollection().getIdentifier().getLocalIdentifier(), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(year.getIdentifier().getLocalIdentifier(), iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testAlbumIdentifier() {
        PhotoElement album = createAlbum();
        PhotoIdentifier identifier = album.getIdentifier();

        assertNotNull(identifier);
        assertEquals(identifier.getKind(), ALBUM);
        assertEquals(identifier, createAlbum().getIdentifier());

        // Iterator test
        Iterator<LocalPhotoIdentifier> iterator = identifier.iterator();
        assertTrue(iterator.hasNext());
        // First one is the collection
        assertEquals(createCollection().getIdentifier().getLocalIdentifier(), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(createYear().getIdentifier().getLocalIdentifier(), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(album.getIdentifier().getLocalIdentifier(), iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testPhotoIdentifier() {
        PhotoElement photo = createPhoto();
        PhotoIdentifier identifier = photo.getIdentifier();

        assertNotNull(identifier);
        assertEquals(identifier.getKind(), PHOTO);
        assertEquals(identifier, createPhoto().getIdentifier());

        // Iterator test
        Iterator<LocalPhotoIdentifier> iterator = identifier.iterator();
        assertTrue(iterator.hasNext());
        // First one is the collection
        assertEquals(createCollection().getIdentifier().getLocalIdentifier(), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(createYear().getIdentifier().getLocalIdentifier(), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(createAlbum().getIdentifier().getLocalIdentifier(), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(photo.getIdentifier().getLocalIdentifier(), iterator.next());

        assertFalse(iterator.hasNext());
    }
}
