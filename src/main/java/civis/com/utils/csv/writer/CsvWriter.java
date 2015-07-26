package civis.com.utils.csv.writer;

import civis.com.utils.csv.data.CSVData;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

/**
 * Utility tow write csv file.
 */
public class CsvWriter {

    private static final String NEW_LINE_SEPARATOR = "\n";

    public static void write(String fileName, List<CSVData> data) {
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));

            //Write a new data object list to the CSV file
            for (CSVData csvData : data) {
                out.append(csvData.getLine());
                out.append(NEW_LINE_SEPARATOR);
            }

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }
    }
}
