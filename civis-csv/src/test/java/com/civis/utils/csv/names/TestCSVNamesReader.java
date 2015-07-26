package com.civis.utils.csv.names;

import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Test class for testing csv names readers.
 */
public class TestCSVNamesReader {

    @Test
    public void testDefaultRead(){
        List<CSVNameData> nameDataList = CSVNamesReader.read();
        Assert.assertEquals(18414,nameDataList.size());
    }

}
