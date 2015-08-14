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
