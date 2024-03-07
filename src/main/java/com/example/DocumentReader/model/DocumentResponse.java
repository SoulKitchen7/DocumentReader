package com.example.DocumentReader.model;

public class DocumentResponse {
    private int documents;
    private int pages;

    public DocumentResponse(int documents, int pages) {
        this.documents = documents;
        this.pages = pages;
    }

    public int getDocuments() {
        return documents;
    }

    public int getPages() {
        return pages;
    }

}
