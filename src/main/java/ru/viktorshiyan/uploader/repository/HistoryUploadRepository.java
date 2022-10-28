package ru.viktorshiyan.uploader.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.viktorshiyan.uploader.model.HistoryUpload;

import java.util.UUID;

@Repository
public interface HistoryUploadRepository extends CrudRepository<HistoryUpload, UUID> {

}
