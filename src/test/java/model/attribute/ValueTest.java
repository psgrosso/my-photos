package model.attribute;


import static org.testng.AssertJUnit.*;

import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

public class ValueTest {
    public static final String STRING_NAME = "string-attribute";
    public static final String INTEGER_NAME = "integer-attribute";
    public static final String LONG_NAME = "long-attribute";
    private Attribute stringAttribute = Attribute.newStringAttribute(STRING_NAME);
    private Attribute integerAttribute = Attribute.newIntegerAttribute(INTEGER_NAME);
    private Attribute longAttribute = Attribute.newLongAttribute(LONG_NAME);

    private Value stringValue = new Value(stringAttribute, "value");
    private Value integerValue = new Value(integerAttribute, 1973);
    private Value longValue = new Value(longAttribute, 19731202L);

    @Test
    public void testString() {
        assertEquals("value", stringValue.getString());
        assertEquals(stringAttribute, stringValue.getAttribute());
        assertEquals(stringValue, new Value(stringAttribute, "value"));
        // Same attribute (type and name), different value
        assertFalse(stringValue.equals(new Value(stringAttribute, "value1")));
        // Same attribute type with different name, same value
        assertFalse(stringValue.equals(new Value(
                        Attribute.newStringAttribute("different-name"), "value")));
        // Different attribute type with same name
        assertFalse(stringValue.equals(new Value(Attribute.newIntegerAttribute(STRING_NAME), 3)));
        // Different attribute type with different name
        assertFalse(stringValue.equals(integerValue));

        assertEquals(0, stringValue.compareTo(new Value(stringAttribute, "value")));
        assertEquals(0, stringValue.compareTo(new Value(stringAttribute, "VALUE")));
        assertTrue(stringValue.compareTo(new Value(stringAttribute, "zzzz")) < 0);
        assertTrue(stringValue.compareTo(new Value(stringAttribute, "not-value")) > 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidStringConstructor() {
        new Value(integerAttribute, "value");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidStringGet() {
        stringValue.getInteger();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidStringCompare() {
        stringValue.compareTo(longValue);
    }

    @Test
    public void testInteger() {
        assertEquals(1973, (int) integerValue.getInteger());
        assertEquals(integerAttribute, integerValue.getAttribute());
        assertEquals(integerValue, new Value(integerAttribute, 1973));
        // Same attribute (type and name), different value
        assertFalse(integerValue.equals(new Value(integerAttribute, 197312)));
        // Same attribute type with different name, same value
        assertFalse(integerValue.equals(new Value(
                Attribute.newIntegerAttribute("different-name"), 1973)));
        // Different attribute type with same name
        assertFalse(integerValue.equals(new Value(Attribute.newStringAttribute(INTEGER_NAME), "3")));
        // Different attribute type with different name
        assertFalse(integerValue.equals(stringValue));

        assertEquals(0, integerValue.compareTo(new Value(integerAttribute, 1973)));
        assertTrue(integerValue.compareTo(new Value(integerAttribute, 197312)) < 0);
        assertTrue(integerValue.compareTo(new Value(integerAttribute, 1202)) > 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidIntegerConstructor() {
        new Value(integerAttribute, 19731202L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidIntegerGet() {
        integerValue.getString();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidIntegerCompare() {
        integerValue.compareTo(longValue);
    }

    @Test
    public void testLong() {
        assertEquals(19731202L, (long) longValue.getLong());
        assertEquals(longAttribute, longValue.getAttribute());
        assertEquals(longValue, new Value(longAttribute, 19731202L));
        // Same attribute (type and name), different value
        assertFalse(longValue.equals(new Value(longAttribute, 19731231L)));
        // Same attribute type with different name, same value
        assertFalse(longValue.equals(new Value(
                Attribute.newLongAttribute("different-name"), 19731202L)));
        // Different attribute type with same name
        assertFalse(longValue.equals(new Value(Attribute.newStringAttribute(LONG_NAME), "3")));
        // Different attribute type with different name
        assertFalse(longValue.equals(stringValue));

        assertEquals(0, longValue.compareTo(new Value(longAttribute, 19731202L)));
        assertTrue(longValue.compareTo(new Value(longAttribute, 197312021100L)) < 0);
        assertTrue(longValue.compareTo(new Value(longAttribute, 1973L)) > 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidLongConstructor() {
        new Value(longAttribute, "value");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidLongGet() {
        longValue.getInteger();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidLongCompare() {
        longValue.compareTo(integerValue);
    }

    @Test
    public void testHash() {
        Set<Value> valueSet = new HashSet<>();
        valueSet.add(stringValue);
        assertTrue(valueSet.contains(stringValue));

        assertFalse(valueSet.contains(integerValue));
        valueSet.add(integerValue);
        assertTrue(valueSet.contains(integerValue));

        assertFalse(valueSet.contains(longValue));
        valueSet.add(longValue);
        assertTrue(valueSet.contains(longValue));

        // Final assertions
        assertTrue(valueSet.contains(stringValue));
        assertTrue(valueSet.contains(integerValue));
    }
}
