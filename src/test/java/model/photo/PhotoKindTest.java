package model.photo;

import model.attribute.Attribute;
import model.attribute.AttributeMap;
import model.attribute.AttributeSet;
import model.photo.element.Photo;
import model.photo.element.PhotoAlbum;
import model.photo.element.PhotoCollection;
import model.photo.element.PhotoElement;
import model.photo.element.PhotoYear;
import org.testng.annotations.Test;
import util.PhotoElementUtils;

import static model.photo.PhotoKind.ALBUM;
import static model.photo.PhotoKind.COLLECTION;
import static model.photo.PhotoKind.PHOTO;
import static model.photo.PhotoKind.YEAR;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static util.PhotoElementUtils.ALBUM_DAY;
import static util.PhotoElementUtils.ALBUM_MONTH;
import static util.PhotoElementUtils.ALBUM_NAME;
import static util.PhotoElementUtils.ATTRS_ALBUM;
import static util.PhotoElementUtils.ATTRS_COLLECTION;
import static util.PhotoElementUtils.ATTRS_YEAR;
import static util.PhotoElementUtils.COLLECTION_NAME;
import static util.PhotoElementUtils.PHOTO_NAME;
import static util.PhotoElementUtils.PHOTO_SIZE;
import static util.PhotoElementUtils.YEAR_VALUE;
import static util.PhotoElementUtils.createAlbum;
import static util.PhotoElementUtils.createCollection;
import static util.PhotoElementUtils.createPhoto;

public class PhotoKindTest {

    public static final Attribute<Integer> ATTR_INVALID = Attribute.newIntegerAttribute("invalid");
    private static final AttributeMap ATTRS_INVALID =
            AttributeMap.builderFor(AttributeSet.of(ATTR_INVALID)).with(ATTR_INVALID, 1026).build();

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testCollectionGetParent() {
        COLLECTION.getParent();
    }

    @Test
    public void testCollection() {
        assertEquals(YEAR, COLLECTION.getChild());
        PhotoElement collection = createCollection();

        assertNotNull(collection);
        assertEquals(PhotoCollection.class, collection.getClass());
        assertEquals(COLLECTION, collection.getKind());
        assertEquals(COLLECTION_NAME, collection.getAttribute(PhotoCollection.F_NAME));
        assertEquals(COLLECTION_NAME, collection.getId());
        assertEquals(collection, new PhotoCollection(PhotoElementUtils.ATTRS_COLLECTION));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNotNullParent() {
        COLLECTION.create(createCollection(), ATTRS_COLLECTION);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCollectionInvalidAttributes() {
        COLLECTION.create(null, ATTRS_INVALID);
    }

    @Test
    public void testYear() {
        assertEquals(COLLECTION, YEAR.getParent());
        assertEquals(ALBUM, YEAR.getChild());
        PhotoElement collection = createCollection();
        PhotoElement year = YEAR.create(collection, ATTRS_YEAR);

        assertNotNull(year);
        assertEquals(PhotoYear.class, year.getClass());
        assertEquals(YEAR, year.getKind());
        assertEquals(String.valueOf(YEAR_VALUE), year.getAttribute(PhotoYear.F_NAME));
        assertEquals(YEAR_VALUE, (int) ((PhotoYear) year).getYear());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testYearNullParent() {
        YEAR.create(null, ATTRS_YEAR);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testYearInvalidNumber() {
        YEAR.create(createAlbum(), PhotoYear.attributeMapFor("invalid-number"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testYearTooSmall() {
        YEAR.create(createAlbum(), PhotoYear.attributeMapFor("1899"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testYearTooLarge() {
        YEAR.create(createAlbum(), PhotoYear.attributeMapFor("2101"));
    }

    @Test
    public void testValidYears() {
        YEAR.create(createAlbum(), PhotoYear.attributeMapFor("1900"));
        YEAR.create(createAlbum(), PhotoYear.attributeMapFor("1973"));
        YEAR.create(createAlbum(), PhotoYear.attributeMapFor("1978"));
        YEAR.create(createAlbum(), PhotoYear.attributeMapFor("2016"));
        YEAR.create(createAlbum(), PhotoYear.attributeMapFor("2099"));
    }

    @Test
    public void testAlbum() {
        assertEquals(YEAR, ALBUM.getParent());
        assertEquals(PHOTO, ALBUM.getChild());
        PhotoElement album = createAlbum();

        assertNotNull(album);
        assertEquals(PhotoAlbum.class, album.getClass());
        assertEquals(ALBUM, album.getKind());
        assertEquals(ALBUM_NAME, album.getAttribute(PhotoAlbum.F_NAME));
        assertEquals(ALBUM_MONTH, (int) album.getAttribute(PhotoAlbum.F_MONTH));
        assertEquals(ALBUM_DAY, (int) album.getAttribute(PhotoAlbum.F_DAY));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAlbumNullParent() {
        ALBUM.create(null, ATTRS_ALBUM);
    }

    @Test
    public void testPhoto() {
        assertEquals(ALBUM, PHOTO.getParent());
        PhotoElement photo = createPhoto();

        assertNotNull(photo);
        assertEquals(Photo.class, photo.getClass());
        assertEquals(PHOTO, photo.getKind());
        assertEquals(PHOTO_NAME, photo.getAttribute(Photo.F_NAME));
        assertEquals(PHOTO_SIZE, (long) photo.getAttribute(Photo.F_SIZE));
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testPhotoChild() {
        PHOTO.getChild();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testPhotoNullParent() {
        PHOTO.create(null, ATTRS_YEAR);
    }
}
