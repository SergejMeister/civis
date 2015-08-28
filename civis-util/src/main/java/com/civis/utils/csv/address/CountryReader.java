/*
 * Copyright 2015 Sergej Meister
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
