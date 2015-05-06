
package com.example.googleimagesearch.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metatag {

    @SerializedName("dc.type")
    @Expose
    private String dcType;
    @SerializedName("dc.title")
    @Expose
    private String dcTitle;
    @SerializedName("dc.contributor")
    @Expose
    private String dcContributor;
    @SerializedName("dc.date")
    @Expose
    private String dcDate;
    @SerializedName("dc.relation")
    @Expose
    private String dcRelation;
    @SerializedName("citation_patent_number")
    @Expose
    private String citationPatentNumber;
    @SerializedName("og:url")
    @Expose
    private String ogUrl;
    @Expose
    private String title;
    @SerializedName("og:title")
    @Expose
    private String ogTitle;
    @SerializedName("og:type")
    @Expose
    private String ogType;
    @SerializedName("og:site_name")
    @Expose
    private String ogSiteName;
    @SerializedName("og:image")
    @Expose
    private String ogImage;

    /**
     * 
     * @return
     *     The dcType
     */
    public String getDcType() {
        return dcType;
    }

    /**
     * 
     * @param dcType
     *     The dc.type
     */
    public void setDcType(String dcType) {
        this.dcType = dcType;
    }

    /**
     * 
     * @return
     *     The dcTitle
     */
    public String getDcTitle() {
        return dcTitle;
    }

    /**
     * 
     * @param dcTitle
     *     The dc.title
     */
    public void setDcTitle(String dcTitle) {
        this.dcTitle = dcTitle;
    }

    /**
     * 
     * @return
     *     The dcContributor
     */
    public String getDcContributor() {
        return dcContributor;
    }

    /**
     * 
     * @param dcContributor
     *     The dc.contributor
     */
    public void setDcContributor(String dcContributor) {
        this.dcContributor = dcContributor;
    }

    /**
     * 
     * @return
     *     The dcDate
     */
    public String getDcDate() {
        return dcDate;
    }

    /**
     * 
     * @param dcDate
     *     The dc.date
     */
    public void setDcDate(String dcDate) {
        this.dcDate = dcDate;
    }

    /**
     * 
     * @return
     *     The dcRelation
     */
    public String getDcRelation() {
        return dcRelation;
    }

    /**
     * 
     * @param dcRelation
     *     The dc.relation
     */
    public void setDcRelation(String dcRelation) {
        this.dcRelation = dcRelation;
    }

    /**
     * 
     * @return
     *     The citationPatentNumber
     */
    public String getCitationPatentNumber() {
        return citationPatentNumber;
    }

    /**
     * 
     * @param citationPatentNumber
     *     The citation_patent_number
     */
    public void setCitationPatentNumber(String citationPatentNumber) {
        this.citationPatentNumber = citationPatentNumber;
    }

    /**
     * 
     * @return
     *     The ogUrl
     */
    public String getOgUrl() {
        return ogUrl;
    }

    /**
     * 
     * @param ogUrl
     *     The og:url
     */
    public void setOgUrl(String ogUrl) {
        this.ogUrl = ogUrl;
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
     *     The ogTitle
     */
    public String getOgTitle() {
        return ogTitle;
    }

    /**
     * 
     * @param ogTitle
     *     The og:title
     */
    public void setOgTitle(String ogTitle) {
        this.ogTitle = ogTitle;
    }

    /**
     * 
     * @return
     *     The ogType
     */
    public String getOgType() {
        return ogType;
    }

    /**
     * 
     * @param ogType
     *     The og:type
     */
    public void setOgType(String ogType) {
        this.ogType = ogType;
    }

    /**
     * 
     * @return
     *     The ogSiteName
     */
    public String getOgSiteName() {
        return ogSiteName;
    }

    /**
     * 
     * @param ogSiteName
     *     The og:site_name
     */
    public void setOgSiteName(String ogSiteName) {
        this.ogSiteName = ogSiteName;
    }

    /**
     * 
     * @return
     *     The ogImage
     */
    public String getOgImage() {
        return ogImage;
    }

    /**
     * 
     * @param ogImage
     *     The og:image
     */
    public void setOgImage(String ogImage) {
        this.ogImage = ogImage;
    }

}
