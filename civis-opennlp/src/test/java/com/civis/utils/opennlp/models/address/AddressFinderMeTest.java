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

import com.civis.utils.html.parser.HtmlParser;
import com.civis.utils.opennlp.models.BaseModelTest;
import com.civis.utils.opennlp.models.ModelFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class AddressFinderMeTest extends BaseModelTest {

    @Test
    public void testPerlAmadeus() {
        String filePath = "text/perlAmadeus.txt";
        String exampleText = getTextExample(filePath);
        AddressFinder addressFinder = ModelFactory.getAddressFinder();
        List<AddressSpan> addressSpans = addressFinder.find(exampleText);
        Assert.assertEquals("Exact on address should be found!", 1, addressSpans.size());
        AddressSpan addressSpan = addressSpans.get(0);
        Assert.assertEquals("Kurfürstendamm", addressSpan.getStreet());
        Assert.assertEquals("21", addressSpan.getStreetNumber());
        Assert.assertEquals("10719", addressSpan.getZip());
        Assert.assertEquals("Berlin", addressSpan.getCity());
        Assert.assertEquals("Deutschland", addressSpan.getCountry());
        Assert.assertTrue("Probability should be greater than 0.7 !", addressSpan.getProbability() > 0.8);
    }

    @Test
    public void testQufox() {
        String filePath = "text/qufox.txt";
        String exampleText = getTextExample(filePath);
        AddressFinder addressFinder = ModelFactory.getAddressFinder();
        List<AddressSpan> addressSpans = addressFinder.find(exampleText);
        Assert.assertEquals("Exact on address should be found!", 1, addressSpans.size());
        AddressSpan addressSpan = addressSpans.get(0);
        Assert.assertEquals("Immanuelkirchstraße", addressSpan.getStreet());
        Assert.assertEquals("30", addressSpan.getStreetNumber());
        Assert.assertEquals("10405", addressSpan.getZip());
        Assert.assertEquals("Berlin", addressSpan.getCity());
        Assert.assertEquals("Deutschland", addressSpan.getCountry());
        Assert.assertTrue("Probability should be greater than 0.7 !", addressSpan.getProbability() > 0.8);
    }

    @Test
    public void testDibag() {
        String filePath = "text/dibag.txt";
        String exampleText = getTextExample(filePath);
        AddressFinder addressFinder = ModelFactory.getAddressFinder();
        List<AddressSpan> addressSpans = addressFinder.find(exampleText);
        Assert.assertEquals("Exact on address should be found!", 1, addressSpans.size());
        AddressSpan addressSpan = addressSpans.get(0);
        Assert.assertEquals("Lilienthalallee", addressSpan.getStreet());
        Assert.assertEquals("25", addressSpan.getStreetNumber());
        Assert.assertEquals("80939", addressSpan.getZip());
        Assert.assertEquals("München", addressSpan.getCity());
        Assert.assertEquals("Deutschland", addressSpan.getCountry());
        Assert.assertTrue(addressSpan.getProbability() > 0.7);
    }

    @Test
    public void testCad() {
        String filePath = "htmls/cad.html";
        String exampleHtml = getTextExample(filePath);
        String exampleText = new HtmlParser(exampleHtml).toPlainText().getContent();
        AddressFinder addressFinder = ModelFactory.getAddressFinder();
        List<AddressSpan> addressSpans = addressFinder.find(exampleText);
        Assert.assertEquals("Exact on address should be found!", 1, addressSpans.size());
        AddressSpan addressSpan = addressSpans.get(0);
        Assert.assertEquals("Friedenstrasse", addressSpan.getStreet());
        Assert.assertEquals("91a", addressSpan.getStreetNumber());
        Assert.assertEquals("10249", addressSpan.getZip());
        Assert.assertEquals("Berlin", addressSpan.getCity());
        Assert.assertEquals("Deutschland", addressSpan.getCountry());
        Assert.assertNull("Probability should ne null", addressSpan.getProbability());
    }
}