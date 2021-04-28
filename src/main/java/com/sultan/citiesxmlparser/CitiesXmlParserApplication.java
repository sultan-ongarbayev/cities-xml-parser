package com.sultan.citiesxmlparser;

import com.sultan.citiesxmlparser.parser.XmlParser;
import com.sultan.citiesxmlparser.parser.dto.ParsingResult;
import com.sultan.citiesxmlparser.repository.DistrictRepository;
import com.sultan.citiesxmlparser.repository.MunicipalityRepository;
import lombok.RequiredArgsConstructor;
import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.net.URL;

@SpringBootApplication
@RequiredArgsConstructor
public class CitiesXmlParserApplication implements CommandLineRunner {

    private static final String URL = "https://vdp.cuzk.cz/vymenny_format/soucasna/20210331_OB_573060_UZSZ.xml.zip";
    private static final String FILENAME = "20210331_OB_573060_UZSZ.xml";

    private static final Logger log = LoggerFactory.getLogger(CitiesXmlParserApplication.class);

    private final XmlParser xmlParser;
    private final MunicipalityRepository municipalityRepository;
    private final DistrictRepository districtRepository;

    public static void main(String[] args) {
        SpringApplication.run(CitiesXmlParserApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        File file = new File("tmp.zip");
        log.info("Downloading file...");
        FileUtils.copyURLToFile(new URL(URL), file);
        log.info("Unzipping archive...");
        ZipFile zipFile = new ZipFile(file);
        zipFile.extractFile(FILENAME, ".");
        log.info("Parsing xml file...");
        ParsingResult result = xmlParser.parse(new File(FILENAME));
        log.info("Saving data to database...");
        municipalityRepository.saveAll(result.getMunicipalities());
        districtRepository.saveAll(result.getDistricts());
    }
}
