package com.flickfinder.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for the Person Model.
 */
class PersonTest {

    /**
     * The person object to be tested.
     */
    private Person person;

    /**
     * Set up the person object before each test.
     *
     */
    @BeforeEach
    public void setUp() {
        person = new Person(1, "John Doe", 1999);
    }

    /**
     * Test the person object is created with the correct values.
     */
    @Test
    public void testPersonCreated() {
        assertEquals(1, person.getId());
        assertEquals("John Doe", person.getName());
        assertEquals(1999, person.getBirth());
    }

    /**
     * Test the person object is updated with the correct value setters.
     */
    @Test
    public void testPersonSetters() {
        person.setId(2);
        person.setName("Jane Doe");
        person.setBirth(2003);
        assertEquals(2, person.getId());
        assertEquals("Jane Doe", person.getName());
        assertEquals(2003, person.getBirth());
    }
}