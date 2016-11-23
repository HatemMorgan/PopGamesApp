package com.example.hatem.popgames.ORM;

/**
 * Created by hatem on 11/23/16.
 */


        import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAPIObject {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("limit")
    @Expose
    private long limit;
    @SerializedName("offset")
    @Expose
    private long offset;
    @SerializedName("number_of_page_results")
    @Expose
    private long numberOfPageResults;
    @SerializedName("number_of_total_results")
    @Expose
    private long numberOfTotalResults;
    @SerializedName("status_code")
    @Expose
    private long statusCode;
    @SerializedName("results")
    @Expose
    private List<Review> results = new ArrayList<>();
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
    public long getLimit() {
        return limit;
    }

    /**
     *
     * @param limit
     *     The limit
     */
    public void setLimit(long limit) {
        this.limit = limit;
    }

    /**
     *
     * @return
     *     The offset
     */
    public long getOffset() {
        return offset;
    }

    /**
     *
     * @param offset
     *     The offset
     */
    public void setOffset(long offset) {
        this.offset = offset;
    }

    /**
     *
     * @return
     *     The numberOfPageResults
     */
    public long getNumberOfPageResults() {
        return numberOfPageResults;
    }

    /**
     *
     * @param numberOfPageResults
     *     The number_of_page_results
     */
    public void setNumberOfPageResults(long numberOfPageResults) {
        this.numberOfPageResults = numberOfPageResults;
    }

    /**
     *
     * @return
     *     The numberOfTotalResults
     */
    public long getNumberOfTotalResults() {
        return numberOfTotalResults;
    }

    /**
     *
     * @param numberOfTotalResults
     *     The number_of_total_results
     */
    public void setNumberOfTotalResults(long numberOfTotalResults) {
        this.numberOfTotalResults = numberOfTotalResults;
    }

    /**
     *
     * @return
     *     The statusCode
     */
    public long getStatusCode() {
        return statusCode;
    }

    /**
     *
     * @param statusCode
     *     The status_code
     */
    public void setStatusCode(long statusCode) {
        this.statusCode = statusCode;
    }

    /**
     *
     * @return
     *     The results
     */
    public List<Review> getResults() {
        return results;
    }

    /**
     *
     * @param results
     *     The results
     */
    public void setResults(List<Review> results) {
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
