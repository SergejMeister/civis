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

package com.civis.utils.opennlp.models.address;

import com.civis.utils.csv.address.CSVAddressData;
import com.civis.utils.csv.address.CSVAddressReader;
import com.civis.utils.csv.address.CountryReader;
import com.civis.utils.opennlp.features.AddressFeature;
import com.civis.utils.opennlp.features.FirstCapitalLetterFeature;
import com.civis.utils.opennlp.features.NumberFeature;
import com.civis.utils.opennlp.features.StreetNumberFeature;
import com.civis.utils.opennlp.models.BaseModel;
import com.civis.utils.opennlp.models.ModelPath;
import com.civis.utils.opennlp.models.TrainConfigData;
import com.civis.utils.opennlp.models.TrainModel;
import com.civis.utils.opennlp.validators.AddressFinderSequenceValidator;
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
import opennlp.tools.util.featuregen.StringPattern;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is a address model.
 * <p/>
 * Model include logic for model training and implements interface to find address in plain text.
 */
public class AddressFinderMe extends BaseModel<AddressSpan> implements AddressFinder, TrainModel {

    private static final Logger LOG = LoggerFactory.getLogger(AddressFinderMe.class);

    private static String[][] EMPTY = new String[0][0];
    private final Set<String> countries;
    protected NameContextGenerator contextGenerator;
    private AdditionalContextFeatureGenerator additionalContextFeatureGenerator;
    private List<CSVAddressData> csvAddressDataList;


    public AddressFinderMe(TokenNameFinderModel model) {
        super(model.getNameFinderModel());
        additionalContextFeatureGenerator = new AdditionalContextFeatureGenerator();
        AdaptiveFeatureGenerator featureGenerator = createDefaultFeatureGenerator();
        contextGenerator = new DefaultNameContextGenerator(featureGenerator);
        SequenceValidator<String> sequenceValidator = new AddressFinderSequenceValidator();
        beam = new BeamSearch<>(DEFAULT_BEAM_SIZE, contextGenerator, nameFinderModel, sequenceValidator,
                DEFAULT_BEAM_SIZE);
        this.csvAddressDataList = CSVAddressReader.read();
        this.countries = CountryReader.read();
    }

    /**
     * Default constructor to init train madel.
     */
    public AddressFinderMe(TrainConfigData trainConfigData) {
        super(trainConfigData);
        this.csvAddressDataList = Collections.emptyList();
        this.countries = Collections.emptySet();
        setDefaultTrainingParametersIfNull();
    }

    //-------------------------------------------------------------------------------------------------------
    //------------------------- Train Logic ------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------

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
        return new AddressFinderMe(trainConfigData);
    }

    @Override
    public AdaptiveFeatureGenerator createDefaultFeatureGenerator() {
        AdaptiveFeatureGenerator[] defaultFeatures =
                new AdaptiveFeatureGenerator[]{new FirstCapitalLetterFeature(), new StreetNumberFeature(),
                        new NumberFeature(), new AddressFeature()};
        return new CachedFeatureGenerator(defaultFeatures);
    }

    public TokenNameFinderModel train() throws IOException {
        return super.train();
    }


    //-------------------------------------------------------------------------------------------------------
    //------------------------- Find Logic ------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------

    @Override
    public List<AddressSpan> find(String text) {
        try (InputStream tokenizerModelInputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(ModelPath.DE_TOKEN_BIN)) {
            TokenizerModel modelToken = new TokenizerModel(tokenizerModelInputStream);
            return find(text, modelToken);
        } catch (Exception e) {
            LOG.error("Tokenizer Models can not be loaded successfully!", e);
        }

        return Collections.emptyList();
    }

    @Override
    public List<AddressSpan> find(String text, TokenizerModel tokenizerModel) {
        Tokenizer tokenizer = new TokenizerME(tokenizerModel);
        String[] tokens = tokenizer.tokenize(text);
        return find(tokens);
    }

    @Override
    public List<AddressSpan> find(String[] tokens) {
        Span[] spans = this.find(tokens, EMPTY);
        if (spans.length == 0) {
            // try to find address with zip code.
            return tryToFindAddressByZip(tokens);
        } else {
            List<Span> fullAddressSpans = new ArrayList<>();
            for (Span span : spans) {
                String spanType = span.getType();
                if (spanType.contains(AddressSpan.PREFIX_TYPE_ADDRESS)) {
                    fullAddressSpans.add(span);
                }
            }

            //find probabilities for address
            double[] addressSpanProbs = this.probs(fullAddressSpans);

            //3. add founded contact persons to the result list
            List<AddressSpan> addressSpans = new ArrayList<>();
            for (int i = 0; i < fullAddressSpans.size(); i++) {
                Span fullAddressSpan = fullAddressSpans.get(i);
                double probability = addressSpanProbs[i];
                AddressSpan addressSpan =
                        new AddressSpanBuilder(fullAddressSpan, probability, tokens).setCountries(countries)
                                .setCsvAddressData(csvAddressDataList).build();
                //createAddressSpan(fullAddressSpan, probability, tokens);
                addressSpans.add(addressSpan);
            }

            return removeDuplicated(addressSpans);
        }
    }

    private List<AddressSpan> tryToFindAddressByZip(String[] tokens) {
        AddressSpan addressSpan = new AddressSpan();
        Set<String> zipSet = extractZips();
        String zip = findSetValueInToken(tokens, zipSet);
        addressSpan.setZip(zip);
        addressSpan.setCountry("Deutschland");
        if (StringUtils.isNotBlank(zip)) {
            zipSet.clear();
            Set<String> citySet = extractCitiesByZip(zip);
            String city = findSetValueInToken(tokens, citySet);
            addressSpan.setCity(city);
            if (StringUtils.isNotBlank(city)) {
                int zipIndex = tokenAt(tokens, zip);
                for (int i = zipIndex; i > -1; i--) {
                    // start on zip index and loop back
                    if (StreetNumberFeature.STREET_NUMBER_PATTERN.matcher(tokens[i]).matches()) {
                        addressSpan.setStreetNumber(tokens[i]);
                        int streetIndex = i - 1;
                        if (streetIndex > -1) {
                            StringPattern stringPattern = StringPattern.recognize(tokens[streetIndex]);
                            if (stringPattern.isInitialCapitalLetter()) {
                                addressSpan.setStreet(tokens[streetIndex]);
                                return Collections.singletonList(addressSpan);
                            }
                        }
                    }
                }
            }
        }

        return Collections.emptyList();
    }

    private int tokenAt(String[] tokens, String value) {
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    private String findSetValueInToken(String[] tokens, Set<String> sets) {
        for (String token : tokens) {
            if (sets.contains(token)) {
                return token;
            }
        }
        return null;
    }

    private Set<String> extractZips() {
        return csvAddressDataList.stream().map(CSVAddressData::getZip).collect(Collectors.toSet());
    }

    private Set<String> extractCitiesByZip(String zip) {
        return csvAddressDataList.stream().filter(addressData -> addressData.getZip().equals(zip))
                .map(CSVAddressData::getCity).collect(Collectors.toSet());
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
                    spans.add(new Span(start, end, c.get(li - 1)));
                }

                start = li;
                end = li + 1;
            } else if (chunkTag.endsWith("cont")) {
                end = li + 1;
            } else if (chunkTag.endsWith("other") && start != -1) {
                spans.add(new Span(start, end, c.get(li - 1)));
                start = -1;
                end = -1;
            }
        }

        if (start != -1) {
            spans.add(new Span(start, end, c.get(c.size() - 1)));
        }

        return spans.toArray(new Span[spans.size()]);
    }
}
