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

import java.io.BufferedReader;
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
    private final static String DEFAULT_ENCODING = "UTF-8";

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
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)) {
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
