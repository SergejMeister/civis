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

package com.civis.utils.csv.names;

import com.civis.utils.csv.common.CSVData;
import com.civis.utils.csv.common.CsvWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper to write csv-name data into file.
 */
public class CsvNameWriter {

    public static final String CSV_NAME_SEPARATOR = ";";

    public static void write(String fileName, List<CSVNameData> namesDataToWrite) {
        List<CSVData> dataToWrite = new ArrayList<>();
        for (CSVNameData nameData : namesDataToWrite) {
            CSVData csvData = createNewCsvData(nameData);
            dataToWrite.add(csvData);
        }
        CsvWriter.write(fileName, dataToWrite);
    }

    private static CSVData createNewCsvData(CSVNameData csvNameData) {
        String line = csvNameData.getCapitalizeName() + CSV_NAME_SEPARATOR + csvNameData.getGender();
        List<String> items = new ArrayList<>();
        items.add(csvNameData.getName());
        items.add(csvNameData.getGender());
        CSVData csvData = new CSVData(line, items);
        return csvData;
    }
}
