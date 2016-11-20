package com.milkmatters.honoursproject.milkmatters.model;

import android.util.Log;

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
     * Get the SQL compatible date
     * @return the date
     */
    public String getSQLDate() {
        String output = "";
        String[] dateArray = date.split("-");

        if ((dateArray[0].length() == 1) && (dateArray[1].length() == 1))
        {
            output = output + "0" + dateArray[0] + "-0" + dateArray[1] + "-" + dateArray[2];
        }
        else if (dateArray[1].length() == 1)
        {
            output = output + dateArray[0] + "-0" + dateArray[1] + "-" + dateArray[2];
        }
        else if (dateArray[0].length() == 1)
            output = output + "0" + dateArray[0] + "-" + dateArray[1] + "-" + dateArray[2];
        else
            output = output + dateArray[0] + "-" + dateArray[1] + "-" + dateArray[2];

        return output;
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

    /**
     * Method to get an analogue representation of the date
     * @return the analogue date
     */
    public String getAnalogueDate()
    {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec"};

        String[] splitDate = this.date.split("-");
        String outDate = String.valueOf(splitDate[0]);
        outDate = outDate + " " + months[Integer.valueOf(splitDate[1]) - 1];
        outDate = outDate + " " + splitDate[2].substring(2);

        return outDate;
    }
}
