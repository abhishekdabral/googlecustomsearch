
package com.example.googleimagesearch.bean;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Queries {

    @Expose
    private List<NextPage> nextPage = new ArrayList<NextPage>();
    @Expose
    private List<Request> request = new ArrayList<Request>();

    /**
     * 
     * @return
     *     The nextPage
     */
    public List<NextPage> getNextPage() {
        return nextPage;
    }

    /**
     * 
     * @param nextPage
     *     The nextPage
     */
    public void setNextPage(List<NextPage> nextPage) {
        this.nextPage = nextPage;
    }

    /**
     * 
     * @return
     *     The request
     */
    public List<Request> getRequest() {
        return request;
    }

    /**
     * 
     * @param request
     *     The request
     */
    public void setRequest(List<Request> request) {
        this.request = request;
    }

}
