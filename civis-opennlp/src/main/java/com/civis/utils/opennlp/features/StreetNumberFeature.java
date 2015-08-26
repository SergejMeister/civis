package com.civis.utils.opennlp.features;

import opennlp.tools.util.featuregen.FeatureGeneratorAdapter;

import java.util.List;
import java.util.regex.Pattern;

/**
 * This generator check first letter of token.
 * <p/>
 * If token value is a upper letter char than add feature with prefix "flup".
 */
public class StreetNumberFeature extends FeatureGeneratorAdapter {

    public static final String STREET_NUMBER_PREFIX = "sn";
    public static final Pattern STREET_NUMBER_PATTERN = Pattern.compile("[0-9-]+[0-9,]$");


    public StreetNumberFeature() {
    }

    @Override
    public void createFeatures(List<String> features, String[] tokens, int index, String[] previousOutcomes) {
        String token = tokens[index];
        if (token != null && token.length() > 0) {
            if (STREET_NUMBER_PATTERN.matcher(token).matches()) {
                features.add(STREET_NUMBER_PREFIX + "=" + token.toLowerCase());
            }
        }
    }
}
