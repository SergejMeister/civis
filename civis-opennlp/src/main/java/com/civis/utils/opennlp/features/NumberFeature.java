package com.civis.utils.opennlp.features;

import opennlp.tools.util.featuregen.FeatureGeneratorAdapter;
import opennlp.tools.util.featuregen.StringPattern;

import java.util.List;

/**
 * This generator check first letter of token.
 * <p/>
 * If token value is a upper letter char than add feature with prefix "flup".
 */
public class NumberFeature extends FeatureGeneratorAdapter {

    public static final String NUMBER_PREFIX = "numb";

    public NumberFeature() {
    }

    @Override
    public void createFeatures(List<String> features, String[] tokens, int index, String[] previousOutcomes) {
        String token = tokens[index];
        if (token != null && token.length() > 0) {
            StringPattern stringPattern = StringPattern.recognize(token);
            if (stringPattern.isAllDigit()) {
                features.add(NUMBER_PREFIX + "=" + token.toLowerCase());
            }
        }
    }
}
