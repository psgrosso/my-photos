package model.attribute;


import static org.testng.AssertJUnit.*;

import org.testng.annotations.Test;

import java.util.Iterator;

public class AttributeSetTest {
    private static final String STRING_NAME1 = "str-name1";
    private static final Attribute stringAttribute1 = Attribute.newStringAttribute(STRING_NAME1);
    private static final String STRING_NAME2 = "str-name2";
    private static final Attribute stringAttribute2 = Attribute.newStringAttribute(STRING_NAME2);
    private static final String INTEGER_NAME1 = "int-name1";
    private static final Attribute integerAttribute = Attribute.newIntegerAttribute(INTEGER_NAME1);

    @Test
    public void testSingleAttribute() {
        AttributeSet attributeSet = AttributeSet.of(stringAttribute1);
        System.out.println("Testing: " + attributeSet);

        Attribute sameStringAttribute = Attribute.newStringAttribute(STRING_NAME1);
        AttributeSet sameAttributeSet = AttributeSet.of(sameStringAttribute);

        assertEquals(1, attributeSet.size());

        // Test contains() with attributes
        assertTrue(attributeSet.contains(stringAttribute1));
        assertTrue(attributeSet.contains(sameStringAttribute));

        // Test contains() with attribute set
        assertTrue(attributeSet.contains(sameAttributeSet));
        assertEquals(attributeSet, sameAttributeSet);
    }

    @Test
    public void testTwoAttributes() {
        AttributeSet attributeSet = AttributeSet.of(integerAttribute, stringAttribute2);
        Attribute attribute1 = Attribute.newIntegerAttribute(INTEGER_NAME1);
        Attribute attribute2 = Attribute.newStringAttribute(STRING_NAME2);

        // Order DOES matter
        AttributeSet sameAttributeSet = AttributeSet.of(attribute2, attribute1);
        System.out.println(attributeSet.hashCode() + " " + sameAttributeSet.hashCode());

        assertFalse(attributeSet.equals(sameAttributeSet));

        assertEquals(2, attributeSet.size());
        assertTrue(attributeSet.contains(attribute1));
        assertTrue(attributeSet.contains(attribute2));

        // Test iterator order
        Iterator<Attribute> iterator = attributeSet.iterator();
        assertEquals(attribute1, iterator.next());
        assertEquals(attribute2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testThreeAttributes() {
        AttributeSet attributeSet = AttributeSet.of(integerAttribute, stringAttribute2, stringAttribute1);
        Attribute attribute1 = Attribute.newIntegerAttribute(INTEGER_NAME1);
        Attribute attribute2 = Attribute.newStringAttribute(STRING_NAME2);
        Attribute attribute3 = Attribute.newStringAttribute(STRING_NAME1);

        // Order DOES matter
        AttributeSet sameAttributeSet = AttributeSet.of(attribute2, attribute1, attribute3);

        assertEquals(3, attributeSet.size());
        assertTrue(attributeSet.contains(attribute1));
        assertTrue(attributeSet.contains(attribute2));
        assertTrue(attributeSet.contains(attribute3));
        assertFalse(attributeSet.equals(sameAttributeSet));

        // Test iterator order
        Iterator<Attribute> iterator = attributeSet.iterator();
        assertEquals(attribute1, iterator.next());
        assertEquals(attribute2, iterator.next());
        assertEquals(attribute3, iterator.next());
        assertFalse(iterator.hasNext());
    }
}
