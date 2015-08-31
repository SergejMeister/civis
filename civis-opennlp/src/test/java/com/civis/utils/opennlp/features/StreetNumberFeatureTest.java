package com.civis.utils.opennlp.features;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Test feature street number feature.
 */
public class StreetNumberFeatureTest extends BaseFeatureTest {


    public void testCreateFeatures() {
        List<String> features = createFeatures("Ich sage meine Adresse nicht.");
        Assert.assertEquals("There is no number", 0, features.size());

        features = createFeatures("Postleitzahl ist 1a2345 Berlin.");
        Assert.assertEquals("0 because a", 0, features.size());

        features = createFeatures("Postleitzahl ist 1_2345 Berlin.");
        Assert.assertEquals("0 because a", 0, features.size());

        features = createFeatures("Meine Adresse ist BerlinerStr. 13 12207 Berlin");
        Assert.assertEquals("Should be 2.", 2, features.size());
        features.clear();

        features = createFeatures("Meine Adresse ist BerlinerStr. 13-108 12207 Berlin");
        Assert.assertEquals("Should be 2.", 2, features.size());

        features = createFeatures("Meine Adresse ist Südstraße 1,23 53175 Bonn");
        Assert.assertEquals("Should be 1 because comma in center.", 1, features.size());

        features = createFeatures("Meine Adresse ist Südstraße 123, 53175 Bonn");
        Assert.assertEquals("Should be 2, because comma at the end", 2, features.size());
    }

    public void testLetterInStreetNumber() {
        List<String> features = createFeatures("Meine Adresse ist Südstraße 12a, 53175 Bonn");
        Assert.assertEquals("Should be 2", 2, features.size());
    }


    public void testNullAndEmptyToken() {
        List<String> features = new ArrayList<>();
        String[] previousOutcomes = {};
        String[] tokens = {null, ""};
        StreetNumberFeature streetNumberFeature = new StreetNumberFeature();
        for (int i = 0; i < tokens.length; i++) {
            streetNumberFeature.createFeatures(features, tokens, i, previousOutcomes);
        }
        Assert.assertTrue(features.isEmpty());
    }

    public void testPrefix() {
        List<String> features = createFeatures("PLZ ist 12345 Berlin.");
        Assert.assertEquals("1 features should be created!", 1, features.size());
        String feature = features.get(0);
        Assert.assertEquals(StreetNumberFeature.STREET_NUMBER_PREFIX + "=12345", feature);
    }

    private List<String> createFeatures(String text) {
        StreetNumberFeature streetNumberFeature = new StreetNumberFeature();
        return createFeatures(text, streetNumberFeature);
    }
}