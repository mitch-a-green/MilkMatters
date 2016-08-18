package com.milkmatters.honoursproject.milkmatters.model;

/**
 * Class to model a breast milk donation
 * Created by mitchell on 2016/08/18.
 */
public class Donation {
    private String date;
    private int quantity;

    /**
     * Public constructor for the donation class
     * * @param date the date on which the donation was made
     * @param quantity the quantity donated
     */
    public Donation( String date, int quantity)
    {
        this.date = date;
        this.quantity = quantity;
    }

    /**
     * Public constructor
     * Used to make a deep copy of the donation
     * @param donation the donation to copy
     */
    public Donation(Donation donation)
    {
        this.date = donation.date;
        this.quantity = donation.quantity;
    }

    /**
     * Get the date
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the date
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get the quantity donated
     * @return the quantity donated
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the quantity donated
     * @param quantity the quantity donated
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
