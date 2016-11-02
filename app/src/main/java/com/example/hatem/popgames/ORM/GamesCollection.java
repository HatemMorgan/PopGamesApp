package com.example.hatem.popgames.ORM;

/**
 * Created by hatem on 11/2/16.
 */


        import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GamesCollection {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("limit")
    @Expose
    private int limit;
    @SerializedName("offset")
    @Expose
    private int offset;
    @SerializedName("number_of_page_results")
    @Expose
    private int numberOfPageResults;
    @SerializedName("number_of_total_results")
    @Expose
    private int numberOfTotalResults;
    @SerializedName("status_code")
    @Expose
    private int statusCode;
    @SerializedName("results")
    @Expose
    private List<ResultGames> results = new ArrayList<ResultGames>();
    @SerializedName("version")
    @Expose
    private String version;

    /**
     *
     * @return
     *     The error
     */
    public String getError() {
        return error;
    }

    /**
     *
     * @param error
     *     The error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     *
     * @return
     *     The limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     *
     * @param limit
     *     The limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     *
     * @return
     *     The offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     *
     * @param offset
     *     The offset
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     *
     * @return
     *     The numberOfPageResults
     */
    public int getNumberOfPageResults() {
        return numberOfPageResults;
    }

    /**
     *
     * @param numberOfPageResults
     *     The number_of_page_results
     */
    public void setNumberOfPageResults(int numberOfPageResults) {
        this.numberOfPageResults = numberOfPageResults;
    }

    /**
     *
     * @return
     *     The numberOfTotalResults
     */
    public int getNumberOfTotalResults() {
        return numberOfTotalResults;
    }

    /**
     *
     * @param numberOfTotalResults
     *     The number_of_total_results
     */
    public void setNumberOfTotalResults(int numberOfTotalResults) {
        this.numberOfTotalResults = numberOfTotalResults;
    }

    /**
     *
     * @return
     *     The statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     *
     * @param statusCode
     *     The status_code
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     *
     * @return
     *     The results
     */
    public List<ResultGames> getGamesResults() {
        return results;
    }

    /**
     *
     * @param results
     *     The results
     */
    public void setGamesResults(List<ResultGames> results) {
        this.results = results;
    }

    /**
     *
     * @return
     *     The version
     */
    public String getVersion() {
        return version;
    }

    /**
     *
     * @param version
     *     The version
     */
    public void setVersion(String version) {
        this.version = version;
    }

}
