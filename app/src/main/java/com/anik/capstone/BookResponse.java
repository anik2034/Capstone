package com.anik.capstone;

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
    private List<Doc> docs;

    @SerializedName("q")
    private String q;




    public BookResponse(int numFound, int start, boolean numFoundExact, List<Doc> docs) {
        this.numFound = numFound;
        this.start = start;
        this.numFoundExact = numFoundExact;
        this.docs = docs;
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
    public class Doc {

        @SerializedName("author_name")
        private List<String> authorList;
        @SerializedName("subject")
        private List<String> subjectList;
        @SerializedName("title")
        private List<String> titleList;

        public Doc(List<String> authorList, List<String> subjectList, List<String> titleList) {
            this.authorList = authorList;
            this.subjectList = subjectList;
            this.titleList = titleList;
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

        public List<String> getTitleList() {
            return titleList;
        }

        public void setTitleList(List<String> titleList) {
            this.titleList = titleList;
        }
    }
}


