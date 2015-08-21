package com.civis.utils.opennlp.features;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Test feature address.
 */
public class AddressFeatureTest extends BaseFeatureTest {

    public void testCreateFeatures() {
        List<String> features = createFeatures("Das ist einfach ein Satz.");
        Assert.assertEquals("0 features should be created!", 0, features.size());

        features = createFeatures("Meine Addresse ist Vatikanstr.");
        Assert.assertEquals("0 features should be created!", 0, features.size());

        features = createFeatures("Meine Addresse ist Vatikanstr. 13");
        Assert.assertEquals("0 features should be created!", 0, features.size());

        features = createFeatures("Meine Addresse ist Vatikanstr. 13 12345");
        Assert.assertEquals("0 features should be created!", 0, features.size());

        features = createFeatures("Meine Addresse ist Vatikanstr. 13 12345 Rom");
        Assert.assertEquals("5 features should be created!", 5, features.size());

        String expendedPrefix = AddressFeature.ADDRESS_START_PREFIX + "=" + "vatikanstr." ;
        Assert.assertEquals("AddressStartPrefix",  expendedPrefix, features.get(0));

        expendedPrefix = AddressFeature.ADDRESS_STREET_PREFIX + "=" + "vatikanstr." ;
        Assert.assertEquals("AddressStreetPrefix",  expendedPrefix, features.get(1));

        expendedPrefix = AddressFeature.ADDRESS_STREET_NUMBER_PREFIX + "=" + "13" ;
        Assert.assertEquals("AddressStreetNumberPrefix",  expendedPrefix, features.get(2));

        expendedPrefix = AddressFeature.ADDRESS_ZIP_PREFIX + "=" + "12345" ;
        Assert.assertEquals("AddressZipPrefix",  expendedPrefix, features.get(3));

        expendedPrefix = AddressFeature.ADDRESS_CITY_PREFIX + "=" + "rom" ;
        Assert.assertEquals("AddressCityPrefix",  expendedPrefix, features.get(4));
    }

    public void testCreateFeaturesWithCountry() {
        List<String> features = createFeatures("Meine Addresse ist Brunnenstraße 65-66 13340 Berlin Deutschland");
        Assert.assertEquals("6 features should be created!", 6, features.size());

        String expendedPrefix = AddressFeature.ADDRESS_START_PREFIX + "=" + "brunnenstraße" ;
        Assert.assertEquals("AddressStartPrefix",  expendedPrefix, features.get(0));

        expendedPrefix = AddressFeature.ADDRESS_STREET_PREFIX + "=" + "brunnenstraße" ;
        Assert.assertEquals("AddressStreetPrefix",  expendedPrefix, features.get(1));

        expendedPrefix = AddressFeature.ADDRESS_STREET_NUMBER_PREFIX + "=" + "65-66" ;
        Assert.assertEquals("AddressStreetNumberPrefix",  expendedPrefix, features.get(2));

        expendedPrefix = AddressFeature.ADDRESS_ZIP_PREFIX + "=" + "13340" ;
        Assert.assertEquals("AddressZipPrefix",  expendedPrefix, features.get(3));

        expendedPrefix = AddressFeature.ADDRESS_CITY_PREFIX + "=" + "berlin" ;
        Assert.assertEquals("AddressCityPrefix",  expendedPrefix, features.get(4));

        expendedPrefix = AddressFeature.ADDRESS_COUNTRY_PREFIX + "=" + "deutschland" ;
        Assert.assertEquals("AddressCountryPrefix",  expendedPrefix, features.get(5));
    }

    public void testNullAndEmptyToken() {
        List<String> features = new ArrayList<>();
        String[] previousOutcomes = {};
        String[] tokens = {null, ""};
        AddressFeature addressFeature = new AddressFeature();
        for (int i = 0; i < tokens.length; i++) {
            addressFeature.createFeatures(features, tokens, i, previousOutcomes);
        }
        Assert.assertTrue(features.isEmpty());
    }

    private List<String> createFeatures(String text) {
        AddressFeature addressFeature = new AddressFeature();
        return createFeatures(text, addressFeature);
    }

}