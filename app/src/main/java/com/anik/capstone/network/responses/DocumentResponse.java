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

    public DocumentResponse(List<String> authorList, List<String> subjectList, String title) {
        this.authorList = authorList;
        this.subjectList = subjectList;
        this.title = title;
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

    public String getTitleList() {
        return title;
    }

    public void setTitleList(String title) {
        this.title = title;
    }
}
