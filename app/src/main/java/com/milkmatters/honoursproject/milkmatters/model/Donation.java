package com.milkmatters.honoursproject.milkmatters.model;

/**
 * Class to model a breast milk donation
 * Created by mitchell on 2016/08/18.
 */
public class Donation {
    private String date;
    private int id;
    private int quantity;

    /**
     * Public constructor for the donation class
     * @param date the date on which the donation was made
     * @param quantity the quantity donated
     */
    public Donation( String date, int quantity)
    {
        this.date = date;
        this.id = -1;
        this.quantity = quantity;
    }

    /**
     * Public constructor for the donation class
     * @param date the date on which the donation was made
     * @param id the id of the donation (in the database)
     * @param quantity the quantity donated
     */
    public Donation( String date, int id, int quantity)
    {
        this.date = date;
        this.id = id;
        this.quantity = quantity;
    }

    /**
     * Public constructor
     * Used to make a deep copy of the donation
     * @param donation the donation to copy
     */
    public Donation(Donation donation)
    {
        this.date = donation.getDate();
        this.id = donation.getID();
        this.quantity = donation.getQuantity();
    }

    /**
     * Get the date
     * @return the date
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Set the date
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get the id
     * @return the id
     */
    public int getID() {
        return this.id;
    }

    /**
     * Set the id
     * @param id the id
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Get the quantity donated
     * @return the quantity donated
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Set the quantity donated
     * @param quantity the quantity donated
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Method to increase the donation by a given amount
     * @param quantity the quantity to increase the donation by
     */
    public void increaseDonation(int quantity)
    {
        this.quantity = quantity + this.quantity;
    }
}
