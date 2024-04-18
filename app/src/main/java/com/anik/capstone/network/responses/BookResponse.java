package com.anik.capstone.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookResponse {
    @SerializedName("numFound")
    private int numFound;
    @SerializedName("start")
    private int start;
    @SerializedName("numFoundExact")
    private boolean numFoundExact;
    @SerializedName("docs")
    private List<DocumentResponse> documentList;

    public BookResponse(int numFound, int start, boolean numFoundExact, List<DocumentResponse> documentList) {
        this.numFound = numFound;
        this.start = start;
        this.numFoundExact = numFoundExact;
        this.documentList = documentList;
    }

    public int getNumFound() {
        return numFound;
    }

    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public boolean isNumFoundExact() {
        return numFoundExact;
    }

    public void setNumFoundExact(boolean numFoundExact) {
        this.numFoundExact = numFoundExact;
    }

    public List<DocumentResponse> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<DocumentResponse> documentList) {
        this.documentList = documentList;
    }

}


