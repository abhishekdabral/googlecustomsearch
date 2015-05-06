
package com.example.googleimagesearch.bean;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagemap {

    @SerializedName("cse_image")
    @Expose
    private List<CseImage> cseImage = new ArrayList<CseImage>();
    @SerializedName("cse_thumbnail")
    @Expose
    private List<CseThumbnail> cseThumbnail = new ArrayList<CseThumbnail>();
    @Expose
    private List<Book> book = new ArrayList<Book>();
    @Expose
    private List<Metatag> metatags = new ArrayList<Metatag>();

    /**
     * 
     * @return
     *     The cseImage
     */
    public List<CseImage> getCseImage() {
        return cseImage;
    }

    /**
     * 
     * @param cseImage
     *     The cse_image
     */
    public void setCseImage(List<CseImage> cseImage) {
        this.cseImage = cseImage;
    }

    /**
     * 
     * @return
     *     The cseThumbnail
     */
    public List<CseThumbnail> getCseThumbnail() {
        return cseThumbnail;
    }

    /**
     * 
     * @param cseThumbnail
     *     The cse_thumbnail
     */
    public void setCseThumbnail(List<CseThumbnail> cseThumbnail) {
        this.cseThumbnail = cseThumbnail;
    }

    /**
     * 
     * @return
     *     The book
     */
    public List<Book> getBook() {
        return book;
    }

    /**
     * 
     * @param book
     *     The book
     */
    public void setBook(List<Book> book) {
        this.book = book;
    }

    /**
     * 
     * @return
     *     The metatags
     */
    public List<Metatag> getMetatags() {
        return metatags;
    }

    /**
     * 
     * @param metatags
     *     The metatags
     */
    public void setMetatags(List<Metatag> metatags) {
        this.metatags = metatags;
    }

}
