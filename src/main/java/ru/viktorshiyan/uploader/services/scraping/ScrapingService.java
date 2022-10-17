package ru.viktorshiyan.uploader.services.scraping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import ru.viktorshiyan.uploader.services.excel.FileParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Сервис для скрапинга
 *
 * @author Viktor Shiyan
 * @since 17.10.2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ScrapingService {

    private final RestTemplate restTemplate;

    @Value(value = "${scraping.host}")
    private String host;

    @Value(value = "${scraping.price-list}")
    private String priceList;
    private final FileParser fileParser;

    @Scheduled(initialDelay = 1L, fixedDelay = Long.MAX_VALUE)
    public void startScraping() throws IOException {
        log.info("Start scraping");
        File file = restTemplate.execute(host + getHrefForFile(), HttpMethod.GET, null, clientHttpResponse -> {
            File ret = File.createTempFile("download", "tmp");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });
        if (file == null) {
            throw new RuntimeException("File not found");
        }
        log.info("File name = {}", file.getName());
        ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
        ZipFile zipFile = new ZipFile(file);
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            log.info(zipEntry.getName());
            fileParser.parse(zipFile.getInputStream(zipEntry));
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        log.info("Finish scraping");
    }

    private String getHrefForFile() throws IOException {
        String blogUrl = host + priceList;
        Document doc = Jsoup.connect(blogUrl).get();
        Elements elementsById = doc.select("div#ctl00_plate_tdzip");
        log.info("Select elements = {}", elementsById);
        Optional<Element> first = elementsById.stream().findFirst();
        if (first.isEmpty()) {
            throw new RuntimeException("Reference not found");
        }
        Element zipElement = first.get();
        Elements a = zipElement.getElementsByTag("a");
        log.info("Element <a> = {}", a);
        String href = a.attr("href");
        log.info("Href <a> = {}", href);
        return href;
    }
}
