package civis.com.utils.csv.readers;

import civis.com.utils.csv.data.CSVData;
import civis.com.utils.csv.data.CSVNameData;
import java.util.ArrayList;
import java.util.List;

/**
 * Reader for names in csv files.
 */
public class CSVNamesReader {

    public static final String CSV_NAME_SEPARATOR = ";";
    public static final Integer NAME_INDEX = 0;
    public static final Integer GENDER_INDEX = 1;
    public static final Integer DEFAULT_ITEMS_COUNT = 2;

    /**
     * Read Default csf-File with more than 18.000 names.
     * Default csv-file is names.csv.
     *
     * @return CSVNameData with name ang gender.
     */
    public static List<CSVNameData> read() {
        //URL csvOrderUrl = Thread.currentThread().getContextClassLoader().getResource("names.csv");
        String csvFilePath = Thread.currentThread().getContextClassLoader().getResource("names.csv").getPath();
        List<CSVData> names = CSVReader.read(csvFilePath, CSV_NAME_SEPARATOR);
        List<CSVNameData> formatedList = new ArrayList<>();
        for (CSVData nameData : names) {
            List<String> lineItems = nameData.getItems();
            if (lineItems.size() == DEFAULT_ITEMS_COUNT) {
                CSVNameData csvNameData =  createCSVNameData(lineItems);
                formatedList.add(csvNameData);
            } else {
                System.out.println("This line has more than two items: " + nameData.getLine());
            }
        }
        return formatedList;
    }

    public static List<CSVNameData> read(String csvFilePath) {
        List<CSVData> names = CSVReader.read(csvFilePath, CSV_NAME_SEPARATOR);
        List<CSVNameData> formatedList = new ArrayList<>();
        for (CSVData nameData : names) {
            List<String> lineItems = nameData.getItems();
            if (lineItems.size() == DEFAULT_ITEMS_COUNT) {
                CSVNameData csvNameData =  createCSVNameData(lineItems);
                formatedList.add(csvNameData);
            } else {
                System.out.println("This line has more than two items: " + nameData.getLine());
            }
        }
        return formatedList;
    }

    private static CSVNameData createCSVNameData(List<String> csvLine){
        String name = csvLine.get(NAME_INDEX);
        String germanGender = csvLine.get(GENDER_INDEX);
        String formatedGender = getFormatedGender(germanGender);
        CSVNameData csvNameData = new CSVNameData(name,formatedGender);
        return csvNameData;
    }

    private static String getFormatedGender(String unformatedGender){
        if (unformatedGender.toLowerCase().startsWith("m")) {
            return "M";
        } else if (unformatedGender.toLowerCase().startsWith("u")) {
            return "N";
        } else if (unformatedGender.toLowerCase().startsWith("w")) {
            return "W";
        } else if (unformatedGender.toLowerCase().startsWith("n")) {
            return "N";
        } else {
            System.out.println("Unknown gender:" + unformatedGender);
            return "Unknown";
        }
    }
}
