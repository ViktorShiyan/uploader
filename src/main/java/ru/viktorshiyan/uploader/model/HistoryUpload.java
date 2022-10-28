package ru.viktorshiyan.uploader.model;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class HistoryUpload {
    @Id
    private UUID id;

    private String fileName;

    @UpdateTimestamp
    private LocalDateTime updateTime;
}
