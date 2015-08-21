package com.civis.utils.opennlp.features;

import junit.framework.TestCase;
import opennlp.tools.util.featuregen.FeatureGeneratorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Base test class for all features.
 */
public abstract class BaseFeatureTest extends TestCase {


    protected List<String> createFeatures(String text, FeatureGeneratorAdapter featureGeneratorAdapter) {
        List<String> features = new ArrayList<>();
        String[] previousOutcomes = {};
        String[] tokens = text.split(" ");
        for (int i = 0; i < tokens.length; i++) {
            featureGeneratorAdapter.createFeatures(features, tokens, i, previousOutcomes);
        }

        return features;
    }
}
