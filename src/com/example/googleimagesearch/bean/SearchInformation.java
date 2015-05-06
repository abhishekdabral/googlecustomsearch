
package com.example.googleimagesearch.bean;

import com.google.gson.annotations.Expose;

public class SearchInformation {

    @Expose
    private Float searchTime;
    @Expose
    private String formattedSearchTime;
    @Expose
    private String totalResults;
    @Expose
    private String formattedTotalResults;

    /**
     * 
     * @return
     *     The searchTime
     */
    public Float getSearchTime() {
        return searchTime;
    }

    /**
     * 
     * @param searchTime
     *     The searchTime
     */
    public void setSearchTime(Float searchTime) {
        this.searchTime = searchTime;
    }

    /**
     * 
     * @return
     *     The formattedSearchTime
     */
    public String getFormattedSearchTime() {
        return formattedSearchTime;
    }

    /**
     * 
     * @param formattedSearchTime
     *     The formattedSearchTime
     */
    public void setFormattedSearchTime(String formattedSearchTime) {
        this.formattedSearchTime = formattedSearchTime;
    }

    /**
     * 
     * @return
     *     The totalResults
     */
    public String getTotalResults() {
        return totalResults;
    }

    /**
     * 
     * @param totalResults
     *     The totalResults
     */
    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * 
     * @return
     *     The formattedTotalResults
     */
    public String getFormattedTotalResults() {
        return formattedTotalResults;
    }

    /**
     * 
     * @param formattedTotalResults
     *     The formattedTotalResults
     */
    public void setFormattedTotalResults(String formattedTotalResults) {
        this.formattedTotalResults = formattedTotalResults;
    }

}
