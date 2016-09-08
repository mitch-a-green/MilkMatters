package com.milkmatters.honoursproject.milkmatters.model;

/**
 * Class to model a news item in the feed.
 */
public class NewsItem extends FeedItem {
    /**
     * Overridden constructor for a news feed item
     * @param title the title of the feed item
     * @param content the content
     * @param timeStamp when the feed item was uploaded
     * @param hyperlink a hyperlink (if applicable)
     */
    public NewsItem(String title, String content, String timeStamp, String hyperlink) {
        super(title, content, timeStamp, hyperlink);
    }

    /**
     * Overridden constructor for a news feed item
     * @param id the ID of the feed item in the database
     * @param title the title of the feed item
     * @param content the content
     * @param timeStamp when the feed item was uploaded
     * @param hyperlink a hyperlink (if applicable)
     */
    public NewsItem(int id, String title, String content, String timeStamp, String hyperlink) {
        super(id, title, content, timeStamp, hyperlink);
    }
}
