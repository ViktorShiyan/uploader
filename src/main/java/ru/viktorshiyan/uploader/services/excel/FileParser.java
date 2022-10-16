package ru.viktorshiyan.uploader.services.excel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.viktorshiyan.uploader.dto.MedicineDto;

import java.io.BufferedInputStream;
import java.io.IOException;

import static ru.viktorshiyan.uploader.util.Constants.TOPIC_MEDICINE;

/**
 * Сервис для парсинга файла
 *
 * @author Viktor Shiyan
 * @since 16.10.2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FileParser {
    private final KafkaTemplate<Long, MedicineDto> kafkaTemplate;

    public void sendMessage(MedicineDto msg) {
        log.info("Send message: message = {}", msg);
        kafkaTemplate.send(TOPIC_MEDICINE, msg);
    }

    public void parse(MultipartFile file) throws IOException {
        log.info("Start parse file");
        BufferedInputStream fileInputStream = new BufferedInputStream(file.getInputStream());
        log.info("BufferedInputStream ready");
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        log.info("Workbook ready");

        Sheet sheet = workbook.getSheetAt(0);
        int i = 0;
        for (Row row : sheet) {
            if (i < 3) {
                i++;
                continue;
            }
            MedicineDto medicineDto = new MedicineDto();
            int j = 0;
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING:
                        this.setValueFromIndex(j, medicineDto, cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        this.setValueFromIndex(j, medicineDto, String.valueOf(cell.getNumericCellValue()));
                        break;
                }
                j++;
            }
            i++;
            if (medicineDto.getBarcode() == null) continue;
            sendMessage(medicineDto);
        }
    }

    private void setValueFromIndex(int index, MedicineDto medicineDto, String value) {
        switch (index) {
            case 0:
                medicineDto.setMnn(value);
                break;
            case 1:
                medicineDto.setTradeName(value);
                break;
            case 2:
                medicineDto.setDosageForm(value);
                break;
            case 3:
                medicineDto.setManufacturer(value);
                break;
            case 4:
                medicineDto.setCodeAtx(value);
                break;
            case 5:
                medicineDto.setAmount(value);
                break;
            case 6:
                medicineDto.setLimitPriceWithoutVat(value);
                break;
            case 7:
                medicineDto.setPrimaryPackagingPrice(value);
                break;
            case 8:
                medicineDto.setNumberRu(value);
                break;
            case 9:
                medicineDto.setPriceRegistrationDateAndSolutions(value);
                break;
            case 10:
                medicineDto.setBarcode(value);
                break;
            case 11:
                medicineDto.setEffectiveDate(value);
                break;
        }
    }
}

