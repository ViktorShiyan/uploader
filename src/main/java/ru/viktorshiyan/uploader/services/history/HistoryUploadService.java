package ru.viktorshiyan.uploader.services.history;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.viktorshiyan.uploader.model.HistoryUpload;
import ru.viktorshiyan.uploader.repository.HistoryUploadRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoryUploadService {

    private final HistoryUploadRepository historyUploadRepository;

    public void createHistory(String fileName) {
        HistoryUpload historyUpload = new HistoryUpload();
        historyUpload.setId(UUID.randomUUID());
        historyUpload.setFileName(fileName);
        historyUploadRepository.save(historyUpload);
    }
}
