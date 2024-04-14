package com.anik.capstone.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocumentResponse {
    @SerializedName("author_name")
    private List<String> authorList;
    @SerializedName("subject")
    private List<String> subjectList;
    @SerializedName("title")
    private String title;
    @SerializedName("isbn")
    private List<String> isbnList;

    @SerializedName("cover_i")
    private int coverId;
    public DocumentResponse(List<String> authorList, List<String> subjectList, String title, List<String> isbnList, int coverId) {
        this.authorList = authorList;
        this.subjectList = subjectList;
        this.title = title;
        this.isbnList = isbnList;
        this.coverId = coverId;
    }

    public List<String> getIsbnList() {
        return isbnList;
    }

    public void setIsbnList(List<String> isbnList) {
        this.isbnList = isbnList;
    }

    public List<String> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<String> authorList) {
        this.authorList = authorList;
    }

    public List<String> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<String> subjectList) {
        this.subjectList = subjectList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCoverId() {
        return coverId;
    }

    public void setCoverId(int coverId) {
        this.coverId = coverId;
    }
}
