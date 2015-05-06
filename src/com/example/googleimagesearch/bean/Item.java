
package com.example.googleimagesearch.bean;

import com.google.gson.annotations.Expose;

public class Item {

    @Expose
    private String kind;
    @Expose
    private String title;
    @Expose
    private String htmlTitle;
    @Expose
    private String link;
    @Expose
    private String displayLink;
    @Expose
    private String snippet;
    @Expose
    private String htmlSnippet;
    @Expose
    private String cacheId;
    @Expose
    private String formattedUrl;
    @Expose
    private String htmlFormattedUrl;
    @Expose
    private Pagemap pagemap;

    /**
     * 
     * @return
     *     The kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * 
     * @param kind
     *     The kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

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
     *     The htmlTitle
     */
    public String getHtmlTitle() {
        return htmlTitle;
    }

    /**
     * 
     * @param htmlTitle
     *     The htmlTitle
     */
    public void setHtmlTitle(String htmlTitle) {
        this.htmlTitle = htmlTitle;
    }

    /**
     * 
     * @return
     *     The link
     */
    public String getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * 
     * @return
     *     The displayLink
     */
    public String getDisplayLink() {
        return displayLink;
    }

    /**
     * 
     * @param displayLink
     *     The displayLink
     */
    public void setDisplayLink(String displayLink) {
        this.displayLink = displayLink;
    }

    /**
     * 
     * @return
     *     The snippet
     */
    public String getSnippet() {
        return snippet;
    }

    /**
     * 
     * @param snippet
     *     The snippet
     */
    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    /**
     * 
     * @return
     *     The htmlSnippet
     */
    public String getHtmlSnippet() {
        return htmlSnippet;
    }

    /**
     * 
     * @param htmlSnippet
     *     The htmlSnippet
     */
    public void setHtmlSnippet(String htmlSnippet) {
        this.htmlSnippet = htmlSnippet;
    }

    /**
     * 
     * @return
     *     The cacheId
     */
    public String getCacheId() {
        return cacheId;
    }

    /**
     * 
     * @param cacheId
     *     The cacheId
     */
    public void setCacheId(String cacheId) {
        this.cacheId = cacheId;
    }

    /**
     * 
     * @return
     *     The formattedUrl
     */
    public String getFormattedUrl() {
        return formattedUrl;
    }

    /**
     * 
     * @param formattedUrl
     *     The formattedUrl
     */
    public void setFormattedUrl(String formattedUrl) {
        this.formattedUrl = formattedUrl;
    }

    /**
     * 
     * @return
     *     The htmlFormattedUrl
     */
    public String getHtmlFormattedUrl() {
        return htmlFormattedUrl;
    }

    /**
     * 
     * @param htmlFormattedUrl
     *     The htmlFormattedUrl
     */
    public void setHtmlFormattedUrl(String htmlFormattedUrl) {
        this.htmlFormattedUrl = htmlFormattedUrl;
    }

    /**
     * 
     * @return
     *     The pagemap
     */
    public Pagemap getPagemap() {
        return pagemap;
    }

    /**
     * 
     * @param pagemap
     *     The pagemap
     */
    public void setPagemap(Pagemap pagemap) {
        this.pagemap = pagemap;
    }

}
