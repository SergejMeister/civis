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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper to read csv address data from file.
 * <p>
 * Default csf address file is german file.
 * </p>
 */
public class CSVAddressReader {

    public static final String DEFAULT_ADDRESS_CSV_RES = "address/de_zip_ort.csv";
    public static final String CSV_COLUMN_SEPARATOR = ",";
    public static final Integer REGION_INDEX = 0;
    public static final Integer ZIP_INDEX = 1;
    public static final Integer CITY_INDEX = 2;
    public static final Integer MIN_ITEMS_COUNT = 3;
    private final static Logger LOG = LoggerFactory.getLogger(CSVAddressReader.class);

    /**
     * Read Default csf-File with germany data: city, zip, region.
     * Default csv-file is address/de_zip_ort.csv.
     *
     * @return CSVNameData with name and gender.
     */
    public static List<CSVAddressData> read() {
        return read(DEFAULT_ADDRESS_CSV_RES);
    }

    public static List<CSVAddressData> read(String csvFilePath) {
        List<CSVData> addresses = CSVReader.read(csvFilePath, CSV_COLUMN_SEPARATOR);
        List<CSVAddressData> addressList = new ArrayList<>();
        for (CSVData addressData : addresses) {
            if (addressData.getItems().size() >= MIN_ITEMS_COUNT) {
                CSVAddressData csvAddressData = createCSVAddressData(addressData.getItems());
                addressList.add(csvAddressData);
            } else {
                LOG.warn("This line has lesser than 3 items: " + addressData.getLine());
            }
        }
        return removeDuplicated(addressList);
    }

    private static List<CSVAddressData> removeDuplicated(List<CSVAddressData> addressList) {
        return addressList.parallelStream().distinct().collect(Collectors.toList());
    }

    private static CSVAddressData createCSVAddressData(List<String> csvLine) {
        String region = csvLine.get(REGION_INDEX);
        String zip = csvLine.get(ZIP_INDEX);
        String city = csvLine.get(CITY_INDEX);
        return new CSVAddressData(city, zip, region);
    }
}
