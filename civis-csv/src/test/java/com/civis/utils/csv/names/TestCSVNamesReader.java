package com.civis.utils.csv.names;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.List;

/**
 * Test class for testing csv names readers.
 */
public class TestCSVNamesReader extends TestCase {

    public void testDefaultRead() {
        List<CSVNameData> nameDataList = CSVNamesReader.read("names/names.csv");
        Assert.assertEquals(18499, nameDataList.size());
    }

    public void testGermanyNamesRead() {
        List<CSVNameData> nameDataList = CSVNamesReader.read("names/de_names.csv");
        Assert.assertEquals(18484, nameDataList.size());
    }

}
