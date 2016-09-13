package com.milkmatters.honoursproject.milkmatters.model;

import android.location.Location;

/**
 * Class to model a depot
 */
public class Depot {
    private String name, hours, contactName, contactDetails, extra;
    private Location location;
    private int id;

    /**
     * Constructor for the depot class.
     * @param name the depot name
     * @param hours the depot's opening hours
     * @param contactName the name of the Milk Matters contact at the depot
     * @param contactDetails the contact details of the depot
     * @param extra any extra information about the depot
     * @param location the location of the depot
     */
    public Depot(String name, String hours, String contactName, String contactDetails, String extra, Location location) {
        this.name = name;
        this.hours = hours;
        this.contactName = contactName;
        this.contactDetails = contactDetails;
        this.extra = extra;
        this.location = location;
        this.id = -1;
    }

    /**
     * Constructor for the depot class.
     * Includes the depot ID (from the database).
     * @param name the depot name
     * @param hours the depot's opening hours
     * @param contactName the name of the Milk Matters contact at the depot
     * @param contactDetails the contact details of the depot
     * @param extra any extra information about the depot
     * @param location the location of the depot
     * @param id the depot ID (from the database)
     */
    public Depot(String name, String hours, String contactName, String contactDetails, String extra, Location location, int id) {
        this.name = name;
        this.hours = hours;
        this.contactName = contactName;
        this.contactDetails = contactDetails;
        this.extra = extra;
        this.location = location;
        this.id = id;
    }

    /**
     * Method to get the details (a snippet) of the depot.
     * To be used in the info window of the map marker denoting this depot
     * @return the details of the depot
     */
    public String getSnippet()
    {
        String output = "";

        if (!checkIfEmpty(this.hours))
            output = output + "Business Hours: " + this.hours + "\n";
        if (!checkIfEmpty(this.contactName))
            output = output + "Milk Matters Contact: " + this.contactName + "\n";
        if (!checkIfEmpty(this.contactDetails))
            output = output + "Contact Number: " + this.contactDetails + "\n";
        if (!checkIfEmpty(this.extra))
            output = output + " Other: " + this.extra + "\n";

        return output.trim();
    }

    /**
     * Method to check whether a string is empty or null
     * @param toCheck the string to check
     * @return a boolean indicating whether the string is empty or null
     */
    private boolean checkIfEmpty(String toCheck)
    {
        if (toCheck.equals("") || toCheck == null)
            return true;
        return false;
    }

    // getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
