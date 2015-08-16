package com.civis.utils.csv.names;

import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Test class for testing csv names readers.
 */
public class TestCSVNamesReader {

    @Test
    public void testDefaultRead() {
        List<CSVNameData> nameDataList = CSVNamesReader.read("names/names.csv");
        Assert.assertEquals(18499,nameDataList.size());
    }

    @Test
    public void testGermanyNamesRead() {
        List<CSVNameData> nameDataList = CSVNamesReader.read("names/de_names.csv");
        Assert.assertEquals(18484,nameDataList.size());
    }

}
