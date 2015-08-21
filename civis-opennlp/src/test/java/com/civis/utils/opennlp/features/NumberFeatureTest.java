package com.civis.utils.opennlp.features;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Test feature all digits.
 */
public class NumberFeatureTest extends BaseFeatureTest {

    public void testCreateFeatures() throws Exception {
        List<String> features = createFeatures("Postleitzahl ist 12345.");
        Assert.assertEquals("0 because .(dot)", 0, features.size());

        features = createFeatures("Postleitzahl ist 123-45 Berlin.");
        Assert.assertEquals("0 because -", 0, features.size());

        features = createFeatures("Postleitzahl ist 1a2345 Berlin.");
        Assert.assertEquals("0 because a", 0, features.size());

        features = createFeatures("Postleitzahl ist 12345 Berlin.");
        Assert.assertEquals("1 features should be created!", 1, features.size());
    }

    public void testNullAndEmptyToken() {
        List<String> features = new ArrayList<>();
        String[] previousOutcomes = {};
        String[] tokens = {null, ""};
        NumberFeature numberFeature = new NumberFeature();
        for (int i = 0; i < tokens.length; i++) {
            numberFeature.createFeatures(features, tokens, i, previousOutcomes);
        }
        Assert.assertTrue(features.isEmpty());
    }

    public void testPrefix() {
        List<String> features = createFeatures("PLZ ist 12345 Berlin.");
        Assert.assertEquals("1 features should be created!", 1, features.size());
        String feature = features.get(0);
        Assert.assertEquals(NumberFeature.NUMBER_PREFIX + "=12345", feature);
    }

    private List<String> createFeatures(String text) {
        NumberFeature numberFeature = new NumberFeature();
        return createFeatures(text, numberFeature);
    }
}