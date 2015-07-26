package com.civis.utils.csv.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * CCV-Reader
 */
public class CSVReader {

    private final static Logger LOG = LoggerFactory.getLogger(CSVReader.class);
    private final static String DEFAULT_ENCODING = "ISO-8859-1";

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
        try (InputStream inputStream = new FileInputStream(filePath)) {
            br = new BufferedReader(new InputStreamReader(inputStream,DEFAULT_ENCODING));
            while ((line = br.readLine()) != null) {
                String[] lineItems = line.split(separator);
                CSVData csvData = new CSVData(line, lineItems);
                result.add(csvData);
            }
        } catch (Exception e) {
            LOG.error("Error occurred while read file (" + filePath + ")", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOG.error("Error occurred while closing BufferReader", e);
                }
            }
        }

        return result;
    }
}
