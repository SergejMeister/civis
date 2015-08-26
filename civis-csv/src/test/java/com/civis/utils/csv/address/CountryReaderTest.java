package com.civis.utils.csv.address;


import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Set;


public class CountryReaderTest extends TestCase {

    public void testReadDefaultDataSize() throws Exception {
        Set<String> countries = CountryReader.read();
        Assert.assertEquals(237, countries.size());
    }

    public void testRead() throws Exception {
        Set<String> countries = CountryReader.read();
        Assert.assertTrue(countries.contains("Deutschland"));
        Assert.assertTrue(countries.contains("Russland"));
    }
}