package com.example.DocumentReader.service;

import com.example.DocumentReader.model.DocumentResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;

@Service
public class DocumentReaderServiceImpl implements DocumentReaderService{

    private static final Logger logger = LoggerFactory.getLogger (DocumentReaderServiceImpl.class);
    @Override
    public DocumentResponse getNumberOfDocumentsAndPages(String directoryPath) throws InvalidPathException {

        File directory = new File(directoryPath);
        int documentsCount = 0;
        int pagesCount = 0;

        if (!directory.exists() && !directory.isDirectory()) {
            throw  new InvalidPathException(directoryPath, "Неверный путь к файлам");
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                DocumentResponse documentResponse = getNumberOfDocumentsAndPages(file.getAbsolutePath());
                if (documentResponse != null) {
                    documentsCount += documentResponse.getDocuments();
                    pagesCount += documentResponse.getPages();
                }
            } else {
                documentsCount += getNumberOfDocuments(file);
                pagesCount += getNumberOfPages(file);
            }
        }

        return new DocumentResponse(documentsCount, pagesCount);
    }

    @Override
    public int getNumberOfDocuments(File file) {

        int count = 0;

        if (file.isFile()) {
            count++;
        }
        return count;
    }

    @Override
    public int getNumberOfPages(File file) {

        int count = 0;
        String fileName = file.getName();

        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        switch (fileExtension) {
            case "pdf":
                count += countPdfPages(file);
                break;
            case "docx":
                count += countDocxPages(file);
                break;
            default:
                logger.error("Расширение файла не поддерживается");
                return 0;
        }

        return count;
    }

    public static int countPdfPages(File pdfFile) {

        try (PDDocument document = PDDocument.load(pdfFile)) {
            return document.getNumberOfPages();
        } catch (FileNotFoundException e) {
            logger.error("Файл " + pdfFile.getName() + " не найден");
            return 0;
        } catch (IOException e) {
            logger.error("Ошибка при чтении файла: " + pdfFile.getName());
            return 0;
        } catch (SecurityException e) {
            logger.error("Нет доступа к файлу: " + pdfFile.getName());
            return 0;
        }
    }

    public static int countDocxPages(File docxFile) {

        try (FileInputStream fis = new FileInputStream(docxFile.getAbsolutePath())) {
            XWPFDocument document = new XWPFDocument(fis);
            return document.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
        } catch (FileNotFoundException e) {
            logger.error("Файл " + docxFile.getName() + " не найден");
            return 0;
        } catch (IOException e) {
            logger.error("Ошибка при чтении файла: " + docxFile.getName());
            return 0;
        } catch (SecurityException e) {
            logger.error("Нет доступа к файлу: " + docxFile.getName());
            return 0;
        }
    }

    // Расширение предусмотренно путем создания новых методов для
    // обработки документво других типов (Excel, PowerPoint и пр.)

}
