package com.civis.utils.opennlp.features;

import opennlp.tools.util.featuregen.FeatureGeneratorAdapter;
import opennlp.tools.util.featuregen.StringPattern;

import java.util.List;

/**
 * This generator check first letter of token.
 * <p/>
 * If token value is a upper letter char than add feature with prefix "flup".
 */
public class FirstCapitalLetterFeature extends FeatureGeneratorAdapter {

    public static final String FIRSTLETTER_UPPERCASE_PREFIX = "flup";

    public FirstCapitalLetterFeature() {
    }

    @Override
    public void createFeatures(List<String> features, String[] tokens, int index, String[] previousOutcomes) {
        String token = tokens[index];
        if (token != null && token.length() > 0) {
            StringPattern stringPattern = StringPattern.recognize(token);
            if (stringPattern.isInitialCapitalLetter()) {
                features.add(FIRSTLETTER_UPPERCASE_PREFIX + "=" + token.toLowerCase());
            }
        }
    }
}
