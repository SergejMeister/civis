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
