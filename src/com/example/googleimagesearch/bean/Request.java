
package com.example.googleimagesearch.bean;

import com.google.gson.annotations.Expose;

public class Request {

    @Expose
    private String title;
    @Expose
    private String totalResults;
    @Expose
    private String searchTerms;
    @Expose
    private Integer count;
    @Expose
    private Integer startIndex;
    @Expose
    private String inputEncoding;
    @Expose
    private String outputEncoding;
    @Expose
    private String safe;
    @Expose
    private String cx;

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
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
     *     The searchTerms
     */
    public String getSearchTerms() {
        return searchTerms;
    }

    /**
     * 
     * @param searchTerms
     *     The searchTerms
     */
    public void setSearchTerms(String searchTerms) {
        this.searchTerms = searchTerms;
    }

    /**
     * 
     * @return
     *     The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 
     * @return
     *     The startIndex
     */
    public Integer getStartIndex() {
        return startIndex;
    }

    /**
     * 
     * @param startIndex
     *     The startIndex
     */
    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * 
     * @return
     *     The inputEncoding
     */
    public String getInputEncoding() {
        return inputEncoding;
    }

    /**
     * 
     * @param inputEncoding
     *     The inputEncoding
     */
    public void setInputEncoding(String inputEncoding) {
        this.inputEncoding = inputEncoding;
    }

    /**
     * 
     * @return
     *     The outputEncoding
     */
    public String getOutputEncoding() {
        return outputEncoding;
    }

    /**
     * 
     * @param outputEncoding
     *     The outputEncoding
     */
    public void setOutputEncoding(String outputEncoding) {
        this.outputEncoding = outputEncoding;
    }

    /**
     * 
     * @return
     *     The safe
     */
    public String getSafe() {
        return safe;
    }

    /**
     * 
     * @param safe
     *     The safe
     */
    public void setSafe(String safe) {
        this.safe = safe;
    }

    /**
     * 
     * @return
     *     The cx
     */
    public String getCx() {
        return cx;
    }

    /**
     * 
     * @param cx
     *     The cx
     */
    public void setCx(String cx) {
        this.cx = cx;
    }

}
