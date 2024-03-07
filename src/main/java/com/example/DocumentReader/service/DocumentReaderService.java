package com.example.DocumentReader.service;

import com.example.DocumentReader.model.DocumentResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;

public interface DocumentReaderService {

    DocumentResponse getNumberOfDocumentsAndPages(String directoryPath) throws InvalidPathException;
    int getNumberOfDocuments(File file);
    int getNumberOfPages(File file);
}
