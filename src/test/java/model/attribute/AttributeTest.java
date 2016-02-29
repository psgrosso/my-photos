package model.attribute;

import static org.testng.AssertJUnit.*;

import org.testng.annotations.Test;

public class AttributeTest {
    private final static String NAME = "name1";
    private final static String NAME1 = "name2";

    @Test
    public void testStringAttribute() {
        Attribute<String> attribute = Attribute.newStringAttribute(NAME);
        assertEquals(NAME, attribute.getName());
        attribute.cast("a simple value");

        assertEquals(attribute, Attribute.newStringAttribute(NAME));
        assertFalse(attribute.equals(Attribute.newStringAttribute(NAME1)));

        try {
            attribute.cast(3);
        }
        catch (ClassCastException e) {
            // Expected
        }
    }

    @Test
    public void testIntegerAttribute() {
        Attribute<Integer> attribute = Attribute.newIntegerAttribute(NAME);
        assertEquals(NAME, attribute.getName());
        attribute.cast(3);

        assertEquals(attribute, Attribute.newIntegerAttribute(NAME));
        assertFalse(attribute.equals(Attribute.newIntegerAttribute(NAME1)));

        try {
            attribute.cast("error expected");
        }
        catch (ClassCastException e) {
            // Expected
        }
    }

    @Test
    public void testLongAttribute() {
        Attribute<Long> attribute = Attribute.newLongAttribute(NAME);
        assertEquals(NAME, attribute.getName());
        attribute.cast(3L);

        assertEquals(attribute, Attribute.newLongAttribute(NAME));
        assertFalse(attribute.equals(Attribute.newLongAttribute(NAME1)));

        try {
            attribute.cast("error expected");
        }
        catch (ClassCastException e) {
            // Expected
        }
    }
}
