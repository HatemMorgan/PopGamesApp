
package com.example.hatem.popgames.ORM;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("deck")
    @Expose
    private String deck;
    @SerializedName("reviewer")
    @Expose
    private String reviewer;
    @SerializedName("score")
    @Expose
    private long score;
    @SerializedName("site_detail_url")
    @Expose
    private String siteDetailUrl;

    /**
     *
     * @return
     *     The deck
     */
    public String getDeck() {
        return deck;
    }

    /**
     *
     * @param deck
     *     The deck
     */
    public void setDeck(String deck) {
        this.deck = deck;
    }

    /**
     *
     * @return
     *     The reviewer
     */
    public String getReviewer() {
        return reviewer;
    }

    /**
     *
     * @param reviewer
     *     The reviewer
     */
    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    /**
     *
     * @return
     *     The score
     */
    public long getScore() {
        return score;
    }

    /**
     *
     * @param score
     *     The score
     */
    public void setScore(long score) {
        this.score = score;
    }

    /**
     *
     * @return
     *     The siteDetailUrl
     */
    public String getSiteDetailUrl() {
        return siteDetailUrl;
    }

    /**
     *
     * @param siteDetailUrl
     *     The site_detail_url
     */
    public void setSiteDetailUrl(String siteDetailUrl) {
        this.siteDetailUrl = siteDetailUrl;
    }

}