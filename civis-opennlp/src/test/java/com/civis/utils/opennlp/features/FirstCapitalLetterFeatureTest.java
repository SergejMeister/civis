package com.civis.utils.opennlp.features;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Test feature first letter upper case.
 */
public class FirstCapitalLetterFeatureTest extends BaseFeatureTest {


    public void testCreateFeatures() {
        List<String> features = createFeatures("Das ist einfach ein Satz.");
        Assert.assertEquals("2 features should be created!", 2, features.size());
        features.clear();

        features = createFeatures("DAS ist einfach ein Satz.");
        Assert.assertEquals("2 features should be created!", 2, features.size());
        features.clear();

        features = createFeatures("dAS ist einfach ein Satz.");
        Assert.assertEquals("1 features should be created!", 1, features.size());
        features.clear();

        features = createFeatures("Über dieses Buch wurde viel gesprochen.");
        Assert.assertEquals("2 features should be created!", 2, features.size());
    }

    public void testNullAndEmptyToken() {
        List<String> features = new ArrayList<>();
        String[] previousOutcomes = {};
        String[] tokens = {null, ""};
        FirstCapitalLetterFeature firstLetterUpperCaseFeature = new FirstCapitalLetterFeature();
        for (int i = 0; i < tokens.length; i++) {
            firstLetterUpperCaseFeature.createFeatures(features, tokens, i, previousOutcomes);
        }
        Assert.assertTrue(features.isEmpty());
    }

    public void testPrefix() {
        List<String> features = createFeatures("Alles ist klein");
        Assert.assertEquals("1 features should be created!", 1, features.size());
        String feature = features.get(0);
        Assert.assertEquals(FirstCapitalLetterFeature.FIRSTLETTER_UPPERCASE_PREFIX + "=alles", feature);
    }

    private List<String> createFeatures(String text) {
        FirstCapitalLetterFeature firstLetterUpperCaseFeature = new FirstCapitalLetterFeature();
        return createFeatures(text, firstLetterUpperCaseFeature);
    }
}