package com.civis.utils.csv.address;


import com.civis.utils.csv.common.CSVData;
import com.civis.utils.csv.common.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Helper to read csv countries data from file.
 * <p>
 * Default csf address file is german file address/de_country.csv.
 * </p>
 */
public class CountryReader {

    public static final String DEFAULT_ADDRESS_CSV_RES = "address/de_country.csv";
    public static final String CSV_COLUMN_SEPARATOR = ",";
    public static final Integer COUNTRY_INDEX = 0;
    public static final Integer MIN_ITEMS_COUNT = 1;
    private final static Logger LOG = LoggerFactory.getLogger(CountryReader.class);

    /**
     * Read Default csf-File country.
     * Default csv-file is address/de_country.csv.
     *
     * @return CSVNameData with name and gender.
     */
    public static Set<String> read() {
        return read(DEFAULT_ADDRESS_CSV_RES);
    }

    public static Set<String> read(String csvFilePath) {
        List<CSVData> countries = CSVReader.read(csvFilePath, CSV_COLUMN_SEPARATOR);
        Set<String> countryList = new HashSet<>();
        for (CSVData country : countries) {
            if (country.getItems().size() >= MIN_ITEMS_COUNT) {
                countryList.add(country.getItems().get(COUNTRY_INDEX));
            } else {
                LOG.warn("This line has 0 items: " + country.getLine());
            }
        }
        return countryList;
    }
}
