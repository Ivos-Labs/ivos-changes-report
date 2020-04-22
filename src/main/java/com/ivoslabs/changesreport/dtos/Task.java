/**
 * 
 */
package com.ivoslabs.changesreport.dtos;

/**
 * 
 * @author www.ivoslabs.com
 *
 */
public class Task {

    private String number;

    private String description;

    /**
     * Gets the number
     *
     * @return {@code String} The number
     */
    public String getNumber() {
        return this.number;
    }

    /**
     * Sets the number
     *
     * @param number {@code String} The number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Gets the description
     *
     * @return {@code String} The description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description
     *
     * @param description {@code String} The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Task [number=" + number + ", description=" + description + "]";
    }

}
