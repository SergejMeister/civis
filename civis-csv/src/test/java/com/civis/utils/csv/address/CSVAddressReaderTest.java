package com.civis.utils.csv.address;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.List;


public class CSVAddressReaderTest extends TestCase {

    public void testReadDataSize() throws Exception {
        List<CSVAddressData> addressDataList = CSVAddressReader.read();
        Assert.assertEquals(58022, addressDataList.size());
    }

    public void testRead() throws Exception {
        List<CSVAddressData> addressDataList = CSVAddressReader.read();
        int defaultIndex = 210;
        CSVAddressData csvAddressData = addressDataList.get(defaultIndex);
        Assert.assertEquals("Stammheim", csvAddressData.getCity());
        Assert.assertEquals("70435", csvAddressData.getZip());
        Assert.assertEquals("Baden-Württemberg", csvAddressData.getRegion());
    }
}