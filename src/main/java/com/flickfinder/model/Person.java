package com.flickfinder.model;

/**
 * Represents a person with its unique id, name, and birth year.
 */
public class Person {

    private int id;
    private String name;
    private int birth;

    /**
     * Constructs a Person object with the specified id, name, and birth.
     *
     * @param id    the unique id of the person
     * @param name  the name of the person
     * @param birth the birth year of the person
     */
    public Person(int id, String name, int birth) {
        this.id = id;
        this.name = name;
        this.birth = birth;
    }

    /**
     * Returns the unique id of the person.
     *
     * @return the id of the person
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the unique id of the person.
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the person.
     *
     * @return the name of the person
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the person.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the birth year of the person.
     *
     * @return the birth year of the person
     */
    public int getBirth() {
        return this.birth;
    }

    /**
     * Sets the birth year of the person.
     *
     * @param birth the birth year to set
     */
    public void setBirth(int birth) {
        this.birth = birth;
    }

    /**
     * Returns a string representation of the Person object.
     * This is primarily used for debugging purposes.
     *
     * @return a string representation of the Person object
     */
    @Override
    public String toString() {
        return String.format("Person[id=%d, name=%s, birth=%d]", this.id, this.name, this.birth);
    }
}
