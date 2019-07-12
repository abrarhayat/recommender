package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author abrar
 * since 6/15/2019
 */

public class CSVFileParser {
    private static Logger log = LoggerFactory.getLogger(CSVFileParser.class);

    public static CSVParser getCSVParser(String fileName) {
        File csvFile = new File("src\\main\\resources\\data\\" + fileName);
        CSVParser parser = null;
        try {
            FileReader reader = new FileReader(csvFile);
            parser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(reader);
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error(ex.toString());
        }
        return parser;
    }

    public static CSVParser getCSVParserFromDir(String fileDirectory) {
        File csvFile = new File(fileDirectory);
        CSVParser parser = null;
        try {
            FileReader reader = new FileReader(csvFile);
            parser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(reader);
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error(ex.toString());
        }
        return parser;
    }
}
