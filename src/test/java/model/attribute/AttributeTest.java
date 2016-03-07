package model.attribute;

import static org.testng.AssertJUnit.*;

import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;


public class AttributeTest {
    private final static String NAME = "name1";
    private final static String NAME1 = "name2";
    private final Attribute longAttribute = Attribute.newLongAttribute(NAME);
    private final Attribute integerAttribute = Attribute.newIntegerAttribute(NAME);
    private final Attribute stringAttribute = Attribute.newStringAttribute(NAME);

    @Test
    public void testStringAttribute() {
        System.out.println("Testing: " + stringAttribute);
        assertEquals(NAME, stringAttribute.getName());
        assertEquals(Attribute.Type.STRING, stringAttribute.getType());

        assertEquals(stringAttribute, Attribute.newStringAttribute(NAME));
        assertFalse(stringAttribute.equals(Attribute.newStringAttribute(NAME1)));
        // Same name with different type
        assertFalse(stringAttribute.equals(Attribute.newIntegerAttribute(NAME)));
        assertFalse(stringAttribute.equals(Attribute.newLongAttribute(NAME)));
    }

    @Test
    public void testIntegerAttribute() {
        assertEquals(NAME, integerAttribute.getName());
        assertEquals(Attribute.Type.INTEGER, integerAttribute.getType());

        assertEquals(integerAttribute, Attribute.newIntegerAttribute(NAME));
        assertFalse(integerAttribute.equals(Attribute.newIntegerAttribute(NAME1)));
        // Same name with different type
        assertFalse(integerAttribute.equals(Attribute.newStringAttribute(NAME)));
        assertFalse(integerAttribute.equals(Attribute.newLongAttribute(NAME)));
    }

    @Test
    public void testLongAttribute() {
        assertEquals(NAME, longAttribute.getName());
        assertEquals(Attribute.Type.LONG, longAttribute.getType());

        assertEquals(longAttribute, Attribute.newLongAttribute(NAME));
        assertFalse(longAttribute.equals(Attribute.newLongAttribute(NAME1)));
        // Same name with different type
        assertFalse(longAttribute.equals(Attribute.newStringAttribute(NAME)));
        assertFalse(longAttribute.equals(Attribute.newIntegerAttribute(NAME)));
    }

    @Test
    public void testIntegerHash() {
        Set<Attribute> set = new HashSet<>();

        assertTrue(set.add(integerAttribute));
        assertTrue(set.contains(integerAttribute));
        assertTrue(set.contains(Attribute.newIntegerAttribute(NAME)));
        assertFalse(set.contains(Attribute.newIntegerAttribute(NAME1)));
        assertFalse(set.contains(Attribute.newStringAttribute(NAME)));
        assertFalse(set.contains(Attribute.newLongAttribute(NAME)));

        assertFalse(set.add(integerAttribute));
    }

    @Test
    public void testMixedHash() {
        Set<Attribute> set = new HashSet<>();
        set.add(integerAttribute);
        set.add(longAttribute);
        set.add(stringAttribute);

        assertEquals(3, set.size());
        assertTrue(set.contains(integerAttribute));
        assertTrue(set.contains(Attribute.newIntegerAttribute(NAME)));
        assertFalse(set.contains(Attribute.newIntegerAttribute(NAME1)));

        assertTrue(set.contains(longAttribute));
        assertTrue(set.contains(Attribute.newLongAttribute(NAME)));
        assertFalse(set.contains(Attribute.newLongAttribute(NAME1)));

        assertTrue(set.contains(stringAttribute));
        assertTrue(set.contains(Attribute.newStringAttribute(NAME)));
        assertFalse(set.contains(Attribute.newStringAttribute(NAME1)));
    }
}
