package com.milkmatters.honoursproject.milkmatters.model;

/**
 * Class to model a feed item.
 */
public class FeedItem {
    private int id;
    private String title;
    private String content;
    private String timeStamp;
    private String hyperlink;

    /**
     * Constructor for a feed item
     * @param title the title of the feed item
     * @param content the content
     * @param timeStamp when the feed item was uploaded
     * @param hyperlink a hyperlink (if applicable)
     */
    public FeedItem(String title, String content, String timeStamp, String hyperlink) {
        this.title = title;
        this.content = content;
        this.timeStamp = timeStamp;
        this.hyperlink = hyperlink;
    }

    /**
     * Constructor for a feed item
     * @param id the ID of the feed item in the database
     * @param title the title of the feed item
     * @param content the content
     * @param timeStamp when the feed item was uploaded
     * @param hyperlink a hyperlink (if applicable)
     */
    public FeedItem(int id, String title, String content, String timeStamp, String hyperlink) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.timeStamp = timeStamp;
        this.hyperlink = hyperlink;
    }

    /**
     * Method to return a string of the date (and time and place where appropriate)
     * @return the date (and time and place where appropriate)
     */
    public String toString()
    {
        return this.timeStamp;
    }

    // getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }
}
