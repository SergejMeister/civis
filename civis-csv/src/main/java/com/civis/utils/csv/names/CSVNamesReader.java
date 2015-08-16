package com.civis.utils.csv.names;


import com.civis.utils.csv.common.CSVData;
import com.civis.utils.csv.common.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper to read csv name data from file.
 */
public class CSVNamesReader {


    public static final String DEFAULT_NAMES_CSV_RES = "names/de_names.csv";
    public static final String CSV_NAME_SEPARATOR = ";";
    public static final Integer NAME_INDEX = 0;
    public static final Integer GENDER_INDEX = 1;
    public static final Integer DEFAULT_ITEMS_COUNT = 2;
    private final static Logger LOG = LoggerFactory.getLogger(CSVNamesReader.class);

    /**
     * Read Default csf-File with more than 18.000 names.
     * Default csv-file is names.csv.
     *
     * @return CSVNameData with name and gender.
     */
    public static List<CSVNameData> read() {
        return read(DEFAULT_NAMES_CSV_RES);
    }

    public static List<CSVNameData> read(String csvFilePath) {
        List<CSVData> names = CSVReader.read(csvFilePath, CSV_NAME_SEPARATOR);
        List<CSVNameData> formatedList = new ArrayList<>();
        for (CSVData nameData : names) {
            List<String> lineItems = nameData.getItems();
            if (lineItems.size() == DEFAULT_ITEMS_COUNT) {
                CSVNameData csvNameData = createCSVNameData(lineItems);
                formatedList.add(csvNameData);
            } else {
                LOG.warn("This line has more than two items: " + nameData.getLine());
            }
        }
        return formatedList;
    }

    private static CSVNameData createCSVNameData(List<String> csvLine) {
        String name = csvLine.get(NAME_INDEX);
        String germanGender = csvLine.get(GENDER_INDEX);
        String formatedGender = getFormatedGender(germanGender);
        CSVNameData csvNameData = new CSVNameData(name, formatedGender);
        return csvNameData;
    }

    private static String getFormatedGender(String unformatedGender) {
        if (unformatedGender.toLowerCase().startsWith("m")) {
            return "M";
        } else if (unformatedGender.toLowerCase().startsWith("u")) {
            return "N";
        } else if (unformatedGender.toLowerCase().startsWith("w")) {
            return "W";
        } else if (unformatedGender.toLowerCase().startsWith("n")) {
            return "N";
        } else {
            LOG.error("Unknown gender: " + unformatedGender);
            return "Unknown";
        }
    }
}
