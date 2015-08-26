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

package com.civis.utils.csv.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
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
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName),
                DEFAULT_ENCODING)) {
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
