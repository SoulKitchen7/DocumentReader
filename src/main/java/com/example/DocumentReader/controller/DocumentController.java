package com.example.DocumentReader.controller;

import com.example.DocumentReader.model.DocumentResponse;
import com.example.DocumentReader.service.DocumentReaderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
public class DocumentController {

    private final DocumentReaderService documentReaderService;

    public DocumentController(DocumentReaderService documentReaderService) {
        this.documentReaderService = documentReaderService;
    }

    @GetMapping
    public String hello() {
        return "Hello!";
    }

    @GetMapping ("/info")
    public DocumentResponse getNumberOfDocumentsAndPages(@RequestParam String directoryPath) throws FileNotFoundException {

        int pageCount = documentReaderService.getNumberOfPages(directoryPath);
        int documentCount = documentReaderService.getNumberOfDocuments(directoryPath);

        return new DocumentResponse(documentCount, pageCount);
    }

}
