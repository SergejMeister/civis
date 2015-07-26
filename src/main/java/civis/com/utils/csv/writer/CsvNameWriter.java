package civis.com.utils.csv.writer;

import civis.com.utils.csv.data.CSVData;
import civis.com.utils.csv.data.CSVNameData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergej Meister on 6/7/15.
 */
public class CsvNameWriter {

    public static final String CSV_NAME_SEPARATOR = ";";

    public static void write(String fileName, List<CSVNameData> namesDataToWrite) {
        List<CSVData> dataToWrite = new ArrayList<>();
        for(CSVNameData nameData : namesDataToWrite){
            CSVData csvData = createNewCsvData(nameData);
            dataToWrite.add(csvData);
        }
        CsvWriter.write(fileName,dataToWrite);
    }

    private static CSVData createNewCsvData(CSVNameData csvNameData){
        String line = csvNameData.getName() + CSV_NAME_SEPARATOR + csvNameData.getGender();
        List<String> items = new ArrayList<>();
        items.add(csvNameData.getName());
        items.add(csvNameData.getGender());
        CSVData csvData = new CSVData(line,items);
        return csvData;
    }
}
