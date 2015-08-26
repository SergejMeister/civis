package com.civis.utils.opennlp.models.address;

import com.civis.utils.opennlp.models.BaseModelTest;
import com.civis.utils.opennlp.models.ModelFactory;
import org.junit.Assert;

import java.util.List;


public class AddressFinderMeTest extends BaseModelTest {

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
        Assert.assertNull(addressSpan.getProbability());
    }
}