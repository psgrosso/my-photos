package model.photo;

import model.attribute.Attribute;
import model.attribute.AttributeSet;
import model.attribute.Value;
import model.attribute.Values;
import model.photo.element.Photo;
import model.photo.element.PhotoAlbum;
import model.photo.element.PhotoCollection;
import model.photo.element.PhotoElement;
import model.photo.element.PhotoYear;
import org.testng.annotations.Test;

import static model.photo.PhotoKind.*;
import static util.PhotoElementUtils.*;
import static org.testng.AssertJUnit.*;

public class PhotoKindTest {
    private static final Attribute ATTR_INVALID = Attribute.newIntegerAttribute("invalid-attr");
    private static final Values VALUES_INVALID =
            Values.builderFor(AttributeSet.of(ATTR_INVALID)).with(new Value(ATTR_INVALID, 1026)).build();

    @Test
    public void testCollection() {
        assertEquals(YEAR, COLLECTION.getChild());
        // createCollection uses PhotoKind.create()
        PhotoElement collection = createCollection();

        assertNotNull(collection);
        assertEquals(PhotoCollection.class, collection.getClass());
        assertEquals(COLLECTION, collection.getKind());
        assertNull(collection.getParent());
        assertEquals(COLLECTION_NAME, collection.getValue(PhotoCollection.F_NAME).getString());
        assertEquals(COLLECTION_NAME, collection.getName());
        assertEquals(collection, new PhotoCollection(COLLECTION_VALUES));
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testCollectionGetParent() {
        // Collections don't have parents
        COLLECTION.getParent();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidValues() {
        COLLECTION.create(createCollection(), VALUES_INVALID);
    }

    @Test
    public void testYear() {
        assertEquals(COLLECTION, YEAR.getParent());
        assertEquals(ALBUM, YEAR.getChild());
        PhotoElement year = createYear();

        assertNotNull(year);
        assertEquals(PhotoYear.class, year.getClass());
        assertEquals(YEAR, year.getKind());
        assertEquals(String.valueOf(YEAR_NAME), year.getValue(PhotoYear.F_NAME).getString());
        assertEquals(YEAR_NAME, ((PhotoYear) year).getYear());
        assertEquals(createCollection(), year.getParent());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testYearNullParent() {
        YEAR.create(null, YEAR_VALUES);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testYearInvalidNumber() {
        YEAR.create(createCollection(), YEAR.valuesBuilderFor("invalid-number").build());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testYearTooSmall() {
        YEAR.create(createCollection(), YEAR.valuesBuilderFor("1899").build());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testYearTooLarge() {
        YEAR.create(createCollection(), YEAR.valuesBuilderFor("2101").build());
    }

    @Test
    public void testSomeValidYears() {
        YEAR.create(createCollection(), YEAR.valuesBuilderFor("1900").build());
        YEAR.create(createCollection(), YEAR.valuesBuilderFor("1934").build());
        YEAR.create(createCollection(), YEAR.valuesBuilderFor("1973").build());
        YEAR.create(createCollection(), YEAR.valuesBuilderFor("2016").build());
        YEAR.create(createCollection(), YEAR.valuesBuilderFor("2099").build());
    }

    @Test
    public void testAlbum() {
        assertEquals(YEAR, ALBUM.getParent());
        assertEquals(PHOTO, ALBUM.getChild());
        PhotoElement album = createAlbum();

        assertNotNull(album);
        assertEquals(PhotoAlbum.class, album.getClass());
        assertEquals(createYear(), album.getParent());
        assertEquals(ALBUM, album.getKind());
        assertEquals(ALBUM_NAME, album.getName());
        assertEquals(ALBUM_MONTH, album.getValue(PhotoAlbum.F_MONTH).getInteger());
        assertEquals(ALBUM_DAY, album.getValue(PhotoAlbum.F_DAY).getInteger());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAlbumNullParent() {
        ALBUM.create(null, ALBUM_VALUES);
    }

    @Test
    public void testPhoto() {
        assertEquals(ALBUM, PHOTO.getParent());
        PhotoElement photo = createPhoto();

        assertNotNull(photo);
        assertEquals(Photo.class, photo.getClass());
        assertEquals(PHOTO, photo.getKind());
        assertEquals(createAlbum(), photo.getParent());
        assertEquals(PHOTO_NAME, photo.getName());
        assertEquals(PHOTO_SIZE, photo.getValue(Photo.F_SIZE).getLong());
        assertEquals(createPhoto(createAlbum(), photo.getName(), photo.getValue(Photo.F_SIZE).getLong()), photo);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testPhotoGetChild() {
        // Collections don't have parents
        PHOTO.getChild();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testPhotoNullParent() {
        PHOTO.create(null, PHOTO_VALUES);
    }
}
