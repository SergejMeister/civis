package com.civis.utils.opennlp.features;

import opennlp.tools.util.featuregen.FeatureGeneratorAdapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This generator check token value in set of names.
 * <p/>
 * If token value exists in the set of names than add feature.
 */
public class FirstNameFeatureGenerator extends FeatureGeneratorAdapter {

    public static final String FIRSTNAME_PREFIX = "fn";

    private Set<String> names;
    private Set<Integer> indexes;
    private Set<String> foundedNames;

    public FirstNameFeatureGenerator() {
        this.names = new HashSet<>();
        init();
    }

    public FirstNameFeatureGenerator(Set<String> names) {
        this.names = names;
        init();
    }

    public FirstNameFeatureGenerator(Set<String> names, Set<String> excludeNames) {
        this.names = names;
        this.names.removeAll(excludeNames);
        init();
    }

    private void init() {
        this.indexes = new HashSet<>();
        this.foundedNames = new HashSet<>();
    }


    @Override
    public void createFeatures(List<String> features, String[] tokens, int index, String[] previousOutcomes) {
        String token = tokens[index];
        if (names.contains(token)) {
            if (checkContactPersonFeatures(features)) {
                if(!foundedNames.contains(token)){
                    foundedNames.add(token);
                    indexes.add(index);
                    features.add(FIRSTNAME_PREFIX + "=" + token.toLowerCase());
                }
            }
        }
    }

    /**
     * List features should include two ContactPersonFeature with name and next name prefix (np=token and nnp=token).
     * It means, that this and next token should be matched with name pattern.
     */
    private Boolean checkContactPersonFeatures(List<String> features) {
        if (features.size() < 2) {
            return Boolean.FALSE;
        } else {
            for (String feature : features) {
                if (feature.contains(ContactPersonFeatureGenerator.NEXT_NAME_PATTERN_PREFIX + "=")) {
                    return Boolean.TRUE;
                }
            }
        }

        return Boolean.FALSE;
    }

    @Override
    public void clearAdaptiveData() {
        names.clear();
        foundedNames.clear();
    }

    /**
     * Return list of token indexes, which value matches first name.
     */
    public Set<Integer> getIndexes() {
        return indexes;
    }

    public void clear() {
        clearAdaptiveData();
        indexes.clear();
    }
}
