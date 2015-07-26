package com.civis.utils.csv.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

/**
 * Utility to write csv file.
 */
public class CsvWriter {

    private final static Logger LOG = LoggerFactory.getLogger(CsvWriter.class);
    private final static String DEFAULT_ENCODING = "UTF-8";
    private final static String NEW_LINE_SEPARATOR = "\n";

    public static void write(String fileName, List<CSVData> data) {
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName), DEFAULT_ENCODING)) {
            Writer out = new BufferedWriter(outputStreamWriter);

            //Write a new data object list to the CSV file
            for (CSVData csvData : data) {
                out.append(csvData.getLine());
                out.append(NEW_LINE_SEPARATOR);
            }
        } catch (Exception e) {
            LOG.error("Error occurred while writing to file (" + fileName + ")", e);
        }
    }
}
