/*
 * Copyright 2021 pi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.huberb.apacheactivemq.examples.jms.lib;

import org.huberb.apacheactivemq.examples.jms.lib.ToConverters;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author pi
 */
public class ToConvertersTest {

    ToConverters instance;

    public ToConvertersTest() {
    }

    @BeforeEach
    public void setUp() {
        instance = new ToConverters();
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of toSupported method, of class ToConverters.
     */
    @Test
    public void testToSupported() {
        assertEquals(Boolean.TRUE, instance.toSupported(ToConverters.SupportedConverters.toBoolean, Boolean.TRUE).get());
        assertEquals(Byte.valueOf((byte) 5), instance.toSupported(ToConverters.SupportedConverters.toByte, "5").get());
        assertEquals(Double.valueOf(5.3d), instance.toSupported(ToConverters.SupportedConverters.toDouble, "5.3").get());
        assertEquals(Float.valueOf(5.3f), instance.toSupported(ToConverters.SupportedConverters.toFloat, "5.3").get());
        assertEquals(Integer.valueOf(5), instance.toSupported(ToConverters.SupportedConverters.toInteger, "5").get());
        assertEquals(Long.valueOf(5L), instance.toSupported(ToConverters.SupportedConverters.toLong, "5").get());
        Object someObject = new Object();
        assertEquals(someObject, instance.toSupported(ToConverters.SupportedConverters.toObject, someObject).get());
        assertEquals(Short.valueOf((short) 5), instance.toSupported(ToConverters.SupportedConverters.toShort, "5").get());
        assertEquals("abc", instance.toSupported(ToConverters.SupportedConverters.toString, "abc").get());

        assertFalse(instance.toSupported(null, "abc").isPresent());
        assertFalse(instance.toSupported(ToConverters.SupportedConverters.toString, null).isPresent());
        assertFalse(instance.toSupported(null, null).isPresent());

    }

    /**
     * Test of toBoolean method, of class ToConverters.
     */
    @Test
    public void testToBoolean() {
        assertEquals(true, instance.toBoolean(Boolean.TRUE).get());
        assertEquals(false, instance.toBoolean(Boolean.FALSE).get());
        assertEquals(true, instance.toBoolean("true").get());
        assertEquals(false, instance.toBoolean("false").get());

        assertFalse(instance.toBoolean(null).isPresent());
        assertTrue(instance.toBoolean("").isPresent());
        assertEquals(false, instance.toBoolean("").get());
        assertTrue(instance.toBoolean("xxx").isPresent());
        assertEquals(false, instance.toBoolean("xxx").get());
    }

    /**
     * Test of toByte method, of class ToConverters.
     */
    @Test
    public void testToByte() {
        assertEquals(5, instance.toByte((byte) 5).get().intValue());
        assertEquals(5, instance.toByte("5").get().intValue());

        assertFalse(instance.toByte(null).isPresent());
        assertFalse(instance.toByte("").isPresent());
        assertFalse(instance.toByte("xxx").isPresent());
    }

    /**
     * Test of toDouble method, of class ToConverters.
     */
    @Test
    public void testToDouble() {
        double deltaDouble = 0.0001d;
        assertEquals(5.3, instance.toDouble(5.3d).get(), deltaDouble);
        assertEquals(-5.3, instance.toDouble(-5.3d).get(), deltaDouble);
        assertEquals(5.3, instance.toDouble("5.3").get(), deltaDouble);
        assertEquals(-5.3, instance.toDouble("-5.3").get(), deltaDouble);

        assertFalse(instance.toDouble(null).isPresent());
        assertFalse(instance.toDouble("").isPresent());
        assertFalse(instance.toDouble("xxx").isPresent());
    }

    /**
     * Test of toFloat method, of class ToConverters.
     */
    @Test
    public void testToFloat() {
        float deltaFloat = 0.0001f;
        assertEquals(5.3, instance.toFloat(5.3f).get(), deltaFloat);
        assertEquals(-5.3, instance.toFloat(-5.3f).get(), deltaFloat);
        assertEquals(5.3, instance.toFloat("5.3").get(), deltaFloat);
        assertEquals(-5.3, instance.toFloat("-5.3").get(), deltaFloat);

        assertFalse(instance.toFloat(null).isPresent());
        assertFalse(instance.toFloat("").isPresent());
        assertFalse(instance.toFloat("xxx").isPresent());
    }

    /**
     * Test of toInteger method, of class ToConverters.
     */
    @Test
    public void testToInteger() {
        assertEquals(5, instance.toInteger(5).get().intValue());
        assertEquals(-5, instance.toInteger(-5).get().intValue());
        assertEquals(5, instance.toInteger("5").get().intValue());
        assertEquals(-5, instance.toInteger("-5").get().intValue());

        assertFalse(instance.toInteger(null).isPresent());
        assertFalse(instance.toInteger("").isPresent());
        assertFalse(instance.toInteger("xxx").isPresent());
    }

    /**
     * Test of toLong method, of class ToConverters.
     */
    @Test
    public void testToLong() {
        assertEquals(5, instance.toLong(5L).get().longValue());
        assertEquals(-5, instance.toLong(-5L).get().longValue());
        assertEquals(5, instance.toLong("5").get().longValue());
        assertEquals(-5, instance.toLong("-5").get().longValue());

        assertFalse(instance.toLong(null).isPresent());
        assertFalse(instance.toLong("").isPresent());
        assertFalse(instance.toLong("xxx").isPresent());
    }

    /**
     * Test of toObject method, of class ToConverters.
     */
    @Test
    public void testToObject() {
        final Object someObject = new Object();
        assertEquals(someObject, instance.toObject(someObject).get());
        assertEquals("abc", instance.toObject("abc").get());

        assertFalse(instance.toObject(null).isPresent());
        assertTrue(instance.toObject("").isPresent());
        assertEquals("", instance.toObject("").get());
        assertTrue(instance.toObject("xxx").isPresent());
        assertEquals("xxx", instance.toObject("xxx").get());
    }

    /**
     * Test of toShort method, of class ToConverters.
     */
    @Test
    public void testToShort() {
        assertEquals(5, instance.toShort(5).get().shortValue());
        assertEquals(-5, instance.toShort(-5).get().shortValue());
        assertEquals(5, instance.toShort("5").get().shortValue());
        assertEquals(-5, instance.toShort("-5").get().shortValue());

        assertFalse(instance.toShort(null).isPresent());
        assertFalse(instance.toShort("").isPresent());
        assertFalse(instance.toShort("xxx").isPresent());
    }

    /**
     * Test of toString method, of class ToConverters.
     */
    @Test
    public void testToString() {
        assertEquals("abc", instance.toString("abc").get());
        assertEquals("!abc@", instance.toString("!abc@").get());

        assertEquals("def", instance.toString(new StringBuilder("def")).get());
        assertEquals("def", instance.toString(new StringBuffer("def")).get());

        assertFalse(instance.toString(null).isPresent());
        assertTrue(instance.toString("").isPresent());
        assertEquals("", instance.toString("").get());
        assertEquals("xxx", instance.toString("xxx").get());
    }

}
