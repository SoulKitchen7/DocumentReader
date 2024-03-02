package com.example.DocumentReader.service;

import java.io.File;
import java.io.FileNotFoundException;

public interface DocumentReaderService {

    int getNumberOfDocuments(String directoryPath);
    int getNumberOfPages(String directoryPath) throws FileNotFoundException;
}
