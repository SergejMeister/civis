package com.civis.utils.csv.address;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.List;


public class CSVAddressReaderTest extends TestCase {

    public void testReadDataSize() throws Exception {
        List<CSVAddressData> addressDataList = CSVAddressReader.read();
        Assert.assertEquals(58066, addressDataList.size());
    }

    public void testRead() throws Exception {
        List<CSVAddressData> addressDataList = CSVAddressReader.read();
        int defaultIndex = 210 ;
        CSVAddressData csvAddressData = addressDataList.get(defaultIndex);
        Assert.assertEquals("Luginsland",csvAddressData.getCity());
        Assert.assertEquals("70327",csvAddressData.getZip());
        Assert.assertEquals("Baden-Württemberg",csvAddressData.getRegion());
    }
}