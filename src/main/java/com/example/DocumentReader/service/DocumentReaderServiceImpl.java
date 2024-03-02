package com.example.DocumentReader.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class DocumentReaderServiceImpl implements DocumentReaderService{
    @Override
    public int getNumberOfDocuments(String directoryPath) {

        File directory = new File(directoryPath);

        if (!directory.exists() && !directory.isDirectory()) {
            return 0;
        }

        int count = 0;
        File[] files = directory.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                count++;
            } else if (file.isDirectory()) {
                count += getNumberOfDocuments(file.getAbsolutePath()); // Рекурсивно вызываем метод для подпапки
            }
        }
        return count;
    }

    @Override
    public int getNumberOfPages(String directoryPath) throws FileNotFoundException {

        File directory = new File(directoryPath);

        if (!directory.exists() && !directory.isDirectory()) {
            return 0;
        }

        int count = 0;
        File[] files = directory.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                count += getNumberOfPages(file.getAbsolutePath()); // Рекурсивно вызываем метод для подпапки
            } else {
                String fileName = file.getName();
                if (fileName.endsWith(".pdf")) {
                    count+=countPdfPages(file);
                } else if (fileName.endsWith(".docx")) {
                    count+=countDocxPages(file);
                }
            }
        }
            return count;
    }

    private static int countPdfPages(File pdfFile) {

        try (PDDocument document = PDDocument.load(pdfFile)){
            return document.getNumberOfPages();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении PDF файла");
            e.printStackTrace();
            return 0;
        }
    }

    private static int countDocxPages(File docxFile) throws FileNotFoundException {

        FileInputStream fis = new FileInputStream(docxFile.getAbsolutePath());

        try (XWPFDocument document = new XWPFDocument(fis)) {
            return document.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении PDF файла");
            e.printStackTrace();
            return 0;
        }
    }

    // Расширение предусмотренно путем создания новых методов для обработки документво других типов (Excel и пр.)

}
