/*
 * Copyright 2015 Sergej Meister
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.civis.utils.opennlp.models.contactperson;

import com.civis.utils.csv.names.CSVNameData;
import com.civis.utils.csv.names.CSVNamesReader;
import com.civis.utils.opennlp.features.ContactPersonFeatureGenerator;
import com.civis.utils.opennlp.features.FirstCapitalLetterFeature;
import com.civis.utils.opennlp.features.FirstNameFeatureGenerator;
import com.civis.utils.opennlp.models.BaseModel;
import com.civis.utils.opennlp.models.ModelPath;
import com.civis.utils.opennlp.models.TrainConfigData;
import com.civis.utils.opennlp.models.TrainModel;
import com.civis.utils.opennlp.utils.Constants;
import com.civis.utils.opennlp.validators.ContactPersonFinderSequenceValidator;
import opennlp.tools.namefind.DefaultNameContextGenerator;
import opennlp.tools.namefind.NameContextGenerator;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.BeamSearch;
import opennlp.tools.util.SequenceValidator;
import opennlp.tools.util.Span;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;
import opennlp.tools.util.featuregen.AdditionalContextFeatureGenerator;
import opennlp.tools.util.featuregen.CachedFeatureGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a contact person model.
 * <p/>
 * Model include logic for model training and implements interface to find address in plain text.
 */
public class ContactPersonFinderMe extends BaseModel<ContactPersonSpan> implements ContactPersonFinder, TrainModel {

    private final static Logger LOG = LoggerFactory.getLogger(ContactPersonFinderMe.class);
    private static final Pattern typedOutcomePattern = Pattern.compile("(.+)-\\w+");
    private static String[][] EMPTY = new String[0][0];
    protected NameContextGenerator contextGenerator;
    private AdditionalContextFeatureGenerator additionalContextFeatureGenerator;
    private FirstNameFeatureGenerator firstNameFeatureGenerator;
    private Map<String, String> mapNamesGender;

    public ContactPersonFinderMe(TokenNameFinderModel model) {
        super(model.getNameFinderModel());
        initMapNamesGender();

        Set<String> excludeNames = generateExcludeNames();
        firstNameFeatureGenerator = new FirstNameFeatureGenerator(this.mapNamesGender.keySet(), excludeNames);
        additionalContextFeatureGenerator = new AdditionalContextFeatureGenerator();
        AdaptiveFeatureGenerator featureGenerator = createDefaultFeatureGenerator();
        contextGenerator = new DefaultNameContextGenerator(featureGenerator);
        contextGenerator.addFeatureGenerator(firstNameFeatureGenerator);

        SequenceValidator<String> sequenceValidator = new ContactPersonFinderSequenceValidator();
        beam = new BeamSearch<>(DEFAULT_BEAM_SIZE, contextGenerator, nameFinderModel, sequenceValidator,
                DEFAULT_BEAM_SIZE);
    }

    /**
     * Default constructor to init train madel.
     */
    public ContactPersonFinderMe(TrainConfigData trainConfigData) {
        super(trainConfigData);
        setDefaultTrainingParametersIfNull();
    }

    /**
     * Train models.
     * <p/>
     * If TrainConfigData.trainingParameters null, than create default training parameters
     * If resources null, than create empty map.
     *
     * @param trainConfigData Language code - en, de, rus ,
     *                        Model type, usually it's name of created model,
     *                        Training data - data tor train a model,
     *                        Training parameters - cutoff(default 5), iteration(default 100), algorithm (default maxent)
     *                        Features - model features,
     *                        Resources
     *
     * @return train model.
     */
    public static TrainModel initializeTrainModel(TrainConfigData trainConfigData) {
        return new ContactPersonFinderMe(trainConfigData);
    }

    private String extractNameType(String outcome) {
        Matcher matcher = typedOutcomePattern.matcher(outcome);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    /**
     * Generate map with more than 18.000 names.
     * Map-Key is name, Map-Value is gender.
     * <p/>
     * CsvNameReader read data from file names.csv in civis-csv module.
     */
    private void initMapNamesGender() {
        this.mapNamesGender = new HashMap<>();
        List<CSVNameData> nameDataList = CSVNamesReader.read();
        for (CSVNameData csvNameData : nameDataList) {
            mapNamesGender.put(csvNameData.getName(), csvNameData.getGender());
        }
    }

    private Set<String> generateExcludeNames() {
        Set<String> excludeNames = new HashSet<>();
        excludeNames.add("Land");
        excludeNames.add("Europa");
        excludeNames.add("Job");
        excludeNames.add("Brand");
        return excludeNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearAdaptiveData() {
        contextGenerator.clearAdaptiveData();
    }

    //-------------------------------------------------------------------------------------------------------
    //------------------------- Train Logic -----------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public AdaptiveFeatureGenerator createDefaultFeatureGenerator() {
        AdaptiveFeatureGenerator[] defaultFeatures =
                new AdaptiveFeatureGenerator[]{new ContactPersonFeatureGenerator(), new FirstCapitalLetterFeature()};
        return new CachedFeatureGenerator(defaultFeatures);
    }

    /**
     * {@inheritDoc}
     */
    public TokenNameFinderModel train() throws IOException {
        return super.train();
    }


    //-------------------------------------------------------------------------------------------------------
    //------------------------- Find Logic ------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ContactPersonSpan> find(String text, TokenizerModel tokenizerModel) {
        Tokenizer tokenizer = new TokenizerME(tokenizerModel);
        String[] tokens = tokenizer.tokenize(text);
        return find(tokens);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ContactPersonSpan> find(String text) {
        try (InputStream tokenizerModelInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
                ModelPath.DE_TOKEN_BIN)) {
            TokenizerModel modelToken = new TokenizerModel(tokenizerModelInputStream);
            return find(text, modelToken);
        } catch (Exception e) {
            LOG.error("Tokenizer Models can not be loaded successfully!", e);
        }

        return Collections.emptyList();
    }

    private Span[] find(String[] tokens, String[][] additionalContext) {
        this.additionalContextFeatureGenerator.setCurrentContext(additionalContext);
        this.bestSequence = this.beam.bestSequence(tokens, additionalContext);
        List<String> c = this.bestSequence.getOutcomes();
        this.contextGenerator.updateAdaptiveData(tokens, c.toArray(new String[c.size()]));
        int start = -1;
        int end = -1;
        ArrayList<Span> spans = new ArrayList<>(tokens.length);
        for (int li = 0; li < c.size(); ++li) {
            String chunkTag = c.get(li);
            if (chunkTag.endsWith("start")) {
                if (start != -1) {
                    spans.add(new Span(start, end, extractNameType(c.get(li - 1))));
                }

                start = li;
                end = li + 1;
            } else if (chunkTag.endsWith("cont")) {
                end = li + 1;
            } else if (start != -1 && chunkTag.endsWith("other")) {
                spans.add(new Span(start, end, extractNameType(c.get(li - 1))));
                start = -1;
                end = -1;
            }
        }

        if (start != -1) {
            spans.add(new Span(start, end, extractNameType(c.get(c.size() - 1))));
        }

        return spans.toArray(new Span[spans.size()]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ContactPersonSpan> find(String[] tokens) {
        Span[] spans = this.find(tokens, EMPTY);
        List<Span> personSpans = new ArrayList<>();
        List<Span> salutationSpans = new ArrayList<>();
        for (Span span : spans) {
            String spanType = span.getType();
            if (spanType.equals(ContactPersonSpan.PREFIX_TYPE_PERSON)) {
                personSpans.add(span);
            }
            if (spanType.equals(ContactPersonSpan.PREFIX_TYPE_SALUTATION)) {
                salutationSpans.add(span);
            }
        }

        //find probabilities for names
        double[] personSpanProbs = this.probs(personSpans);

        //3. add founded contact persons to the result list
        Set<ContactPersonSpan> contactSpans = new HashSet<>();
        for (int i = 0; i < personSpans.size(); i++) {
            Span personSpan = personSpans.get(i);
            String firstName = tokens[personSpan.getStart()];
            String sexPrefix = findSex(firstName, salutationSpans, tokens);
            double probability = personSpanProbs[i];

            int nextTokenIndex = personSpan.getStart() + 1;
            if (nextTokenIndex < tokens.length) {
                ContactPersonSpan contactPersonSpan = new ContactPersonSpan(probability, sexPrefix);
                String lastName = tokens[nextTokenIndex];
                if (ContactPersonFeatureGenerator.NAME_PATTERN.matcher(lastName).matches()) {
                    contactPersonSpan.setFirstName(firstName);
                    contactPersonSpan.setSecondName(lastName);
                } else {
                    // Hm. next token doesn't have name format. Maybe, the firstName is secondName, like - Herr Meister :)
                    lastName = firstName;
                    contactPersonSpan.setSecondName(lastName);
                }
                contactSpans.add(contactPersonSpan);
            }
        }

        if (!contactSpans.isEmpty()) {
            //contact persons found
            //TODO it should be a better solution to remove similarity items.
            // like Rene Mono and Rene.
            return removeSimilarity(contactSpans);
        } else {
            //contact persons not found, than check in firstNameFeatureGenerator
            return getContactPersonsFromFirstNameFeature(tokens);
        }
    }

    /**
     * Remove similarity contact persons items.
     * <p/>
     * Similarity contact person items are two items, that are not equal, but their first or second name are equal.
     * If a similarity contact person found return contact with better probability value!
     */
    private List<ContactPersonSpan> removeSimilarity(Set<ContactPersonSpan> contactPersonSpans) {
        Set<ContactPersonSpan> result = new HashSet<>();
        if (contactPersonSpans.size() < 1) {
            return toList(contactPersonSpans);
        }

        for (ContactPersonSpan contactPersonSpan : contactPersonSpans) {
            ContactPersonSpan similarityContact = findSimilarityItem(contactPersonSpans, contactPersonSpan);
            if (similarityContact != null && similarityContact.getProbability() > contactPersonSpan.getProbability()) {
                //similarity contact has a better probability value.
                result.add(similarityContact);
            } else {
                result.add(contactPersonSpan);
            }
        }

        return toList(result);
    }

    private List<ContactPersonSpan> toList(Set<ContactPersonSpan> contactPersonSpans) {
        return new ArrayList<>(contactPersonSpans);
    }

    private ContactPersonSpan findSimilarityItem(Set<ContactPersonSpan> contactPersonSpans, ContactPersonSpan check) {
        for (ContactPersonSpan contactPersonSpan : contactPersonSpans) {
            if (!contactPersonSpan.equals(check)) {
                if (contactPersonSpan.contains(check.getFullNameWithoutWhiteSpace())) {
                    return contactPersonSpan;
                }
            }
        }

        return null;
    }

    private List<ContactPersonSpan> getContactPersonsFromFirstNameFeature(String[] tokens) {
        List<ContactPersonSpan> contactSpans = new ArrayList<>();
        for (Integer firstNameIndex : firstNameFeatureGenerator.getIndexes()) {
            String firstName = tokens[firstNameIndex];
            String secondName = tokens[firstNameIndex + 1];
            String sexPrefix = mapNamesGender.get(firstName);
            contactSpans.add(new ContactPersonSpan(firstName, secondName, sexPrefix));
        }

        firstNameFeatureGenerator.clear();
        return contactSpans;
    }

    private String findSex(String personName, List<Span> salutationSpans, String[] tokens) {
        String sexText = "";
        for (Span salutationSpan : salutationSpans) {
            String nextWordAfterSalutation = tokens[salutationSpan.getEnd()];
            if (nextWordAfterSalutation.equals(personName)) {
                //That mean after Salutation is coming person name (first or second name? :) ).
                sexText = tokens[salutationSpan.getStart()];
            }
        }

        return getSexPrefix(sexText);
    }

    private String getSexPrefix(String sexText) {
        if (Constants.MAN_SALUTATIONS.contains(sexText)) {
            return "M";
        } else if (Constants.WOMEN_SALUTATIONS.contains(sexText)) {
            return "W";
        } else {
            return "N";
        }
    }
}