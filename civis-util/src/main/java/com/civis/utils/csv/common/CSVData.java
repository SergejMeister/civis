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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Data transfer object csv data.
 */
public class CSVData {

    /**
     * Represents a file line!
     */
    private String line;

    /**
     * List of line items.
     */
    private List<String> items;

    /**
     * DefaultConstructor
     */
    public CSVData() {
        this.line = "";
        this.items = new ArrayList<>();
    }

    /**
     * Constructor with array of line items.
     */
    public CSVData(String line, String[] items) {
        this.line = line;
        this.items = Arrays.asList(items);
    }

    /**
     * Constructor with list of line items.
     */
    public CSVData(String line, List<String> items) {
        this.line = line;
        this.items = items;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public void setItems(String[] items) {
        this.items = Arrays.asList(items);
    }
}
