package ru.viktorshiyan.uploader.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO для отправки в кафку
 */
@Data
@NoArgsConstructor
public class MedicineDto {
    /**
     * МНН
     */
    private String mnn;
    /**
     * Торговое наименование лекарственного препарата
     */
    private String tradeName;
    /**
     * Лекарственная форма, дозировка, упаковка (полная)
     */
    private String dosageForm;
    /**
     * Владелец РУ/производитель/упаковщик/Выпускающий контроль
     */
    private String manufacturer;
    /**
     * Код АТХ
     */
    private String codeAtx;
    /**
     * Количество в потреб. упаковке
     */
    private String amount;
    /**
     * Предельная цена руб. без НДС
     */
    private String limitPriceWithoutVat;
    /**
     * Цена указана для первич. упаковки
     */
    private String primaryPackagingPrice;
    /**
     * № РУ
     */
    private String numberRu;
    /**
     * Дата регистрации цены
     * (№ решения)
     */
    private String priceRegistrationDateAndSolutions;
    /**
     * Штрих-код (EAN13)
     */
    private String barcode;
    /**
     * Дата вступления в силу
     */
    private String effectiveDate;

}
