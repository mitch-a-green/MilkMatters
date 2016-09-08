package com.milkmatters.honoursproject.milkmatters.model;

/**
 * Class to model a news item in the feed.
 */
public class EventItem extends FeedItem {
    private String eventDate;
    private String eventTime;
    private String eventPlace;
    /**
     * Overridden constructor for an event item
     * @param title     the title of the feed item
     * @param content   the content
     * @param timeStamp when the feed item was uploaded
     * @param hyperlink a hyperlink (if applicable)
     * @param eventDate the date of the event
     * @param eventTime the time of the event
     * @param eventPlace the place of the event
     */
    public EventItem(String title, String content, String timeStamp, String hyperlink,
                     String eventDate, String eventTime, String eventPlace) {
        super(title, content, timeStamp, hyperlink);
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventPlace = eventPlace;
    }

    /**
     * Overridden constructor for an event item
     * @param id        the ID of the feed item in the database
     * @param title     the title of the feed item
     * @param content   the content
     * @param timeStamp when the feed item was uploaded
     * @param hyperlink a hyperlink (if applicable)
     * @param eventDate the date of the event
     * @param eventTime the time of the event
     * @param eventPlace the place of the event
     */
    public EventItem(int id, String title, String content, String timeStamp, String hyperlink,
                     String eventDate, String eventTime, String eventPlace) {
        super(id, title, content, timeStamp, hyperlink);
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventPlace = eventPlace;
    }

    // getters and setters

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }
}
