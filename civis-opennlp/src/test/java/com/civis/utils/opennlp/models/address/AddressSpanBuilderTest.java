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

package com.civis.utils.opennlp.models.address;

import com.civis.utils.csv.address.CSVAddressData;
import com.civis.utils.csv.address.CSVAddressReader;
import com.civis.utils.csv.address.CountryReader;
import opennlp.tools.util.Span;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;


public class AddressSpanBuilderTest {

    @Test
    public void testBuildDefaultAddress() throws Exception {
        String street = "Kurfürstenstraße";
        String streetNr = "12";
        String zip = "12207";
        String city = "Berlin";
        String[] tokens = new String[]{"other", "other", street, streetNr, zip, city, "other", "other"};
        Span span = new Span(2, 6, "address");
        AddressSpan addressSpan = new AddressSpanBuilder(span, 0.6, tokens).build();
        Assert.assertEquals(street, addressSpan.getStreet());
        Assert.assertEquals(streetNr, addressSpan.getStreetNumber());
        Assert.assertEquals(zip, addressSpan.getZip());
        Assert.assertEquals(city, addressSpan.getCity());
        Assert.assertNull(addressSpan.getCountry());
    }

    @Test
    public void testBuildStreet() throws Exception {
        String street1_0 = "Brandenburger";
        String street1_1 = "Tor";
        String streetNr = "12";
        String zip = "12207";
        String city = "Berlin";
        String[] tokens = new String[]{"other", "other", street1_0, street1_1, streetNr, zip, city, "other", "other"};
        Span span = new Span(2, 7, "address");
        AddressSpan addressSpan = new AddressSpanBuilder(span, 0.6, tokens).build();
        Assert.assertEquals(street1_0 + " " + street1_1, addressSpan.getStreet());
        Assert.assertEquals(streetNr, addressSpan.getStreetNumber());
        Assert.assertEquals(zip, addressSpan.getZip());
        Assert.assertEquals(city, addressSpan.getCity());
        Assert.assertNull(addressSpan.getCountry());
    }

    @Test
    public void testBuildWithCSV() throws Exception {
        String street = "Reamurstr.";
        String streetNr = "20";
        String zip = "12207";
        String city = "Berlin";
        String[] tokens = new String[]{"other", "other", street, streetNr, zip, city, "other", "other"};
        Span span = new Span(2, 6, "address");
        List<CSVAddressData> csvAddressDataList = CSVAddressReader.read();
        Set<String> countries = CountryReader.read();
        AddressSpan addressSpan =
                new AddressSpanBuilder(span, 0.6, tokens).setCsvAddressData(csvAddressDataList).setCountries(countries)
                        .build();
        Assert.assertEquals(street, addressSpan.getStreet());
        Assert.assertEquals(streetNr, addressSpan.getStreetNumber());
        Assert.assertEquals(zip, addressSpan.getZip());
        Assert.assertEquals(city, addressSpan.getCity());
        Assert.assertEquals("Deutschland", addressSpan.getCountry());
    }

    @Test
    public void testBuildAdvancedAddress() throws Exception {
        String street = "Reamurstr.";
        String streetNr = "20";
        String NotValidZip = "1234";
        String city = "Frankfurt am Main";
        String country = "Deutschland" ;
        String[] tokens = new String[]{"other", "other", street, streetNr, NotValidZip, city, country, "other"};
        Span span = new Span(2, 7, "address");
        List<CSVAddressData> csvAddressDataList = CSVAddressReader.read();
        Set<String> countries = CountryReader.read();
        AddressSpan addressSpan =
                new AddressSpanBuilder(span, 0.6, tokens).setCsvAddressData(csvAddressDataList).setCountries(countries)
                        .build();
        Assert.assertEquals(street, addressSpan.getStreet());
        Assert.assertEquals(streetNr, addressSpan.getStreetNumber());
        Assert.assertEquals(NotValidZip, addressSpan.getZip());
        Assert.assertEquals(city, addressSpan.getCity());
        Assert.assertEquals(country, addressSpan.getCountry());
    }
}