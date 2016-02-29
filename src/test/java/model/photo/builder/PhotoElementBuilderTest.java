package model.photo.builder;


import model.attribute.AttributeMap;
import model.photo.element.PhotoCollection;
import model.photo.element.PhotoElement;
import model.photo.element.PhotoYear;
import org.testng.annotations.Test;
import util.PhotoElementUtils;

import static model.photo.PhotoKind.COLLECTION;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertSame;
import static util.PhotoElementUtils.COLLECTION_NAME;
import static util.PhotoElementUtils.YEAR_NAME;

public class PhotoElementBuilderTest {

    @Test
    public void testHappyPath() {
        PhotoElementBuilder builder = new PhotoElementBuilder(PhotoElementUtils.ATTRS_COLLECTION);
        PhotoCollection collection = builder.build();

        assertNotNull(collection);
        assertEquals(PhotoCollection.class, collection.getClass());
        assertEquals(COLLECTION, collection.getKind());
        assertEquals(COLLECTION_NAME, collection.getAttribute(PhotoCollection.F_NAME));
        assertEquals(collection, new PhotoCollection(PhotoElementUtils.ATTRS_COLLECTION));

    }

    @Test
    public void testChildren() {
        PhotoElementBuilder collectionBuilder = new PhotoElementBuilder(PhotoElementUtils.ATTRS_COLLECTION);

        for (AttributeMap yearAttribute : PhotoElementUtils.createYearAttributes(10)) {
            PhotoElementBuilder yearBuilder = collectionBuilder.addChild(yearAttribute);

            for (AttributeMap albumAttribute : PhotoElementUtils.createAlbumAttributes(9)) {
                PhotoElementBuilder albumBuilder = yearBuilder.addChild(albumAttribute);

                for (AttributeMap photoAttribute : PhotoElementUtils.createPhotoAttributes(9)) {
                    albumBuilder.addChild(photoAttribute);
                }
            }
        }
        collectionBuilder.build();
    }

    @Test
    public void testYears() {
        PhotoElementBuilder mainCollection = new PhotoElementBuilder(PhotoElementUtils.ATTRS_COLLECTION);
        PhotoElementBuilder year1973Builder = mainCollection.addChild(PhotoElementUtils.ATTRS_YEAR);
        //PhotoElementBuilder year2016Builder = mainCollection.addChild(new AttributeMap(PhotoYear.F_NAME, "2016"));

        PhotoCollection collection = mainCollection.build();
        System.out.println(collection);

        assertNotNull(collection);
        assertEquals(PhotoCollection.class, collection.getClass());
        assertEquals(COLLECTION, collection.getKind());
        assertEquals(COLLECTION_NAME, collection.getAttribute(PhotoCollection.F_NAME));

        // Check parent
        PhotoElement year1973 = collection.getChild(YEAR_NAME);
        assertSame(collection, year1973.getParent());
        assertSame(collection, collection.getChild("2016").getParent());
        //assertEquals(COLLECTION_NAME + "|" + YEAR_NAME, year1973.getGlobalId());
    }

    public void testYearInvalid() {
        PhotoElementBuilder mainCollection = new PhotoElementBuilder(PhotoElementUtils.ATTRS_COLLECTION);
        PhotoElementBuilder year1973 = mainCollection.addChild(PhotoElementUtils.ATTRS_YEAR);
        //PhotoElementBuilder year2016 = mainCollection.addChild(new AttributeMap(PhotoYear.F_NAME, "2016"));

        PhotoCollection collection = mainCollection.build();
    }

    @Test
    public void test() {
        PhotoElementBuilder mainCollection = new PhotoElementBuilder(PhotoElementUtils.ATTRS_COLLECTION);
        PhotoElementBuilder year1973 = mainCollection.addChild(PhotoElementUtils.ATTRS_YEAR);
        year1973.addChild(PhotoElementUtils.ATTRS_ALBUM);

        PhotoCollection collection = mainCollection.build();
        System.out.println(collection);
    }
}
