package com.example.DocumentReader;

import com.example.DocumentReader.service.DocumentReaderServiceImpl;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentReaderServiceTest {

    @Test
    void testCountPdfPages () throws IOException {

        File pdfFile = new File("C:\\Users\\uliss\\Downloads\\Test.pdf");
        DocumentReaderServiceImpl documentReaderService = new DocumentReaderServiceImpl();
        int expected = 2;

        int actual = documentReaderService.countPdfPages(pdfFile);

        assertEquals(expected, actual);
    }

    @Test
    void testCountDocxPages () throws IOException {

        File docxFile = new File("C:\\Users\\uliss\\Downloads\\Test.docx");
        DocumentReaderServiceImpl documentReaderService = new DocumentReaderServiceImpl();
        int expected = 2;

        int actual = documentReaderService.countDocxPages(docxFile);

        assertEquals(expected, actual);
    }
    @Test
    public void testGetNumberOfDocuments() {

        DocumentReaderServiceImpl documentReaderService = new DocumentReaderServiceImpl();
        String directoryPath = "C:\\Users\\uliss\\Downloads\\Test";
        File file = new File(directoryPath);
        int expected = 7;

        int actual = documentReaderService.getNumberOfDocuments(file);

        assertEquals(expected, actual);

    }
}

