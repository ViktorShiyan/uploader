package ru.viktorshiyan.uploader.services.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class FileParser {
    public void parse(MultipartFile file) throws IOException {
        InputStream fileInputStream = file.getInputStream();

    }
}
