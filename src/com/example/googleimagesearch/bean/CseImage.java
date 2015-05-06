
package com.example.googleimagesearch.bean;

import com.google.gson.annotations.Expose;
public class CseImage {

    @Expose
    private String src;

    /**
     * 
     * @return
     *     The src
     */
    public String getSrc() {
        return src;
    }

    /**
     * 
     * @param src
     *     The src
     */
    public void setSrc(String src) {
        this.src = src;
    }

}
