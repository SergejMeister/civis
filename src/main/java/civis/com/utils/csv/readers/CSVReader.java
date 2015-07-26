package civis.com.utils.csv.readers;

import civis.com.utils.csv.data.CSVData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * CCV-Reader
 */
public class CSVReader {

    /**
     * Read a specified file to find all items divided by given separator.
     *
     * @param filePath  path to csv file.
     * @param separator line items separator.
     *
     * @return list of CSVData.
     */
    public static List<CSVData> read(String filePath, String separator) {
        List<CSVData> result = new ArrayList<>();

        BufferedReader br = null;
        String line = "";
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)){
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((line = br.readLine()) != null) {
                String[] lineItems = line.split(separator);
                CSVData csvData = new CSVData(line, lineItems);
                result.add(csvData);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
