package ru.viktorshiyan.uploader.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.viktorshiyan.uploader.services.excel.FileParser;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/upload")
@Slf4j
@RequiredArgsConstructor
public class FileUploadController {

    private final FileParser fileParser;

    @PostMapping("/file")
    public String uploadFile(@RequestPart MultipartFile file) throws IOException {
        log.info("Start upload file: name={}, time={}", file.getOriginalFilename(), LocalDateTime.now());
        var result = file.getOriginalFilename();
        fileParser.parse(file);
        log.info("Finish upload file: name={}, time={}", file.getOriginalFilename(), LocalDateTime.now());
        return result;
    }
}
