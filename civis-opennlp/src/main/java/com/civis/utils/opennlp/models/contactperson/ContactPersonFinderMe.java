package com.civis.utils.opennlp.models.contactperson;

import com.civis.utils.opennlp.features.ContactPersonFeatureGenerator;
import com.civis.utils.opennlp.models.ModelPath;
import com.civis.utils.opennlp.validators.ContactPersonFinderSequenceValidator;
import opennlp.model.AbstractModel;
import opennlp.model.MaxentModel;
import opennlp.model.TrainUtil;
import opennlp.tools.namefind.DefaultNameContextGenerator;
import opennlp.tools.namefind.NameContextGenerator;
import opennlp.tools.namefind.NameFinderEventStream;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleSequenceStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.BeamSearch;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.Sequence;
import opennlp.tools.util.SequenceValidator;
import opennlp.tools.util.Span;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;
import opennlp.tools.util.featuregen.AdditionalContextFeatureGenerator;
import opennlp.tools.util.featuregen.CachedFeatureGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 */
public class ContactPersonFinderMe implements ContactPersonFinder {

    private final static Logger LOG = LoggerFactory.getLogger(ContactPersonFinderMe.class);
    private static final Pattern typedOutcomePattern = Pattern.compile("(.+)-\\w+");
    private static String[][] EMPTY = new String[0][0];
    private static int DEFAULT_BEAM_SIZE = 3;
    private final List<String> manSalutations;
    private final List<String> womenSalutations;
    protected MaxentModel model;
    protected NameContextGenerator contextGenerator;
    private AdditionalContextFeatureGenerator additionalContextFeatureGenerator;
    private Sequence bestSequence;
    private BeamSearch<String> beam;

    public ContactPersonFinderMe(TokenNameFinderModel model) {
        this.manSalutations = Arrays.asList("Herr", "Herrn", "Mr.", "Mr");
        this.womenSalutations = Arrays.asList("Frau", "Ms.", "Ms", "Mrs.", "Mrs");
        additionalContextFeatureGenerator = new AdditionalContextFeatureGenerator();
        this.model = model.getNameFinderModel();
        AdaptiveFeatureGenerator featureGenerator = createDefaultFeatureGenerator();
        this.contextGenerator = new DefaultNameContextGenerator(featureGenerator);
        //this.contextGenerator.addFeatureGenerator(new WindowFeatureGenerator(additionalContextFeatureGenerator, 8, 8));

        //TODO research sequence generator.
        //SequenceValidator<String> sequenceValidator = new NameFinderSequenceValidator();
        SequenceValidator<String> sequenceValidator = new ContactPersonFinderSequenceValidator();
        this.beam = new BeamSearch<>(DEFAULT_BEAM_SIZE, this.contextGenerator, this.model, sequenceValidator,
                DEFAULT_BEAM_SIZE);
    }

    public ContactPersonFinderMe(TokenNameFinderModel model, AdaptiveFeatureGenerator generator) {
        this.manSalutations = Arrays.asList("Herr", "Herrn", "Mr.", "Mr");
        this.womenSalutations = Arrays.asList("Frau", "Ms.", "Ms", "Mrs.", "Mrs");
        this.additionalContextFeatureGenerator = new AdditionalContextFeatureGenerator();
        this.model = model.getNameFinderModel();
        this.contextGenerator = new DefaultNameContextGenerator(generator);
        //this.contextGenerator.addFeatureGenerator(new WindowFeatureGenerator(this.additionalContextFeatureGenerator, 8, 8));

        //TODO research sequence generator.
        //SequenceValidator<String> sequenceValidator = new NameFinderSequenceValidator();
        SequenceValidator<String> sequenceValidator = new ContactPersonFinderSequenceValidator();
        this.beam = new BeamSearch<>(DEFAULT_BEAM_SIZE, this.contextGenerator, this.model, sequenceValidator,
                DEFAULT_BEAM_SIZE);
    }


    private static AdaptiveFeatureGenerator createDefaultFeatureGenerator() {
        ContactPersonFeatureGenerator contactPersonFeatureGenerator = new ContactPersonFeatureGenerator();
        AdaptiveFeatureGenerator[] defaultFeatures =
                new AdaptiveFeatureGenerator[]{contactPersonFeatureGenerator};
        return new CachedFeatureGenerator(defaultFeatures);
    }

    private static String extractNameType(String outcome) {
        Matcher matcher = typedOutcomePattern.matcher(outcome);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    public static TokenNameFinderModel train(String languageCode, String type, ObjectStream<NameSample> samples,
                                             int cutoff) throws IOException {
        TrainingParameters trainParams = TrainingParameters.defaultParams();
        trainParams.put("Cutoff", Integer.toString(cutoff));
        return train(languageCode, type, samples, trainParams, Collections.emptyMap());
    }

    public static TokenNameFinderModel train(String languageCode, String type, ObjectStream<NameSample> samples)
            throws IOException {
        return train(languageCode, type, samples, TrainingParameters.defaultParams(),
                Collections.emptyMap());
    }

    public static TokenNameFinderModel train(String languageCode, String type, ObjectStream<NameSample> samples,
                                             Map<String, Object> resources) throws IOException {
        return train(languageCode, type, samples, TrainingParameters.defaultParams(), resources);
    }

    public static TokenNameFinderModel train(String languageCode, String type, ObjectStream<NameSample> samples,
                                             TrainingParameters trainParams, Map<String, Object> resources)
            throws IOException {
        AdaptiveFeatureGenerator featureGenerator = createDefaultFeatureGenerator();
        return train(languageCode, type, samples, trainParams, featureGenerator, resources);
    }

    public static TokenNameFinderModel train(String languageCode, String type, ObjectStream<NameSample> samples,
                                             TrainingParameters trainParams, AdaptiveFeatureGenerator featureGenerator,
                                             Map<String, Object> resources) throws IOException {
        if (languageCode == null) {
            throw new IllegalArgumentException("languageCode must not be null!");
        } else {
            HashMap<String, String> manifestInfoEntries = new HashMap<>();
            AbstractModel nameFinderModel;
            if (!TrainUtil.isSequenceTraining(trainParams.getSettings())) {
                NameFinderEventStream ss = new NameFinderEventStream(samples, type,
                        new DefaultNameContextGenerator(featureGenerator));
                nameFinderModel = TrainUtil.train(ss, trainParams.getSettings(), manifestInfoEntries);
            } else {
                NameSampleSequenceStream ss1 = new NameSampleSequenceStream(samples, featureGenerator);
                nameFinderModel = TrainUtil.train(ss1, trainParams.getSettings(), manifestInfoEntries);
            }

            return new TokenNameFinderModel(languageCode, nameFinderModel, resources, manifestInfoEntries);
        }
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
            } else if (chunkTag.endsWith("other") && start != -1) {
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

    public List<ContactPersonSpan> find(String[] tokens) {
        Span[] spans = this.find(tokens, EMPTY);
        List<Span> personSpans = new ArrayList<>();
        List<Span> salutationSpans = new ArrayList<>();
        for (int i = 0; i < spans.length; i++) {
            String spanType = spans[i].getType();
            if (spanType.equals(ContactPersonSpan.PREFIX_TYPE_PERSON)) {
                personSpans.add(spans[i]);
            }
            if (spanType.equals(ContactPersonSpan.PREFIX_TYPE_SALUTATION)) {
                salutationSpans.add(spans[i]);
            }
        }

        //find probabilities for names
        double[] personSpanProbs = this.probs(personSpans);

        //3. add founded contact persons to the result list
        List<ContactPersonSpan> contactSpans = new ArrayList<>();
        for (int i = 0; i < personSpans.size(); i++) {
            Span personSpan = personSpans.get(i);
            String firstName = tokens[personSpan.getStart()];
            String sexPrefix = findSex(firstName, salutationSpans, tokens);
            double probability = personSpanProbs[i];

            int nextTokenIndex = personSpan.getStart() + 1;
            if (nextTokenIndex < tokens.length) {
                String lastName = tokens[nextTokenIndex];
                if (ContactPersonFeatureGenerator.NAME_PATTERN.matcher(lastName).matches()) {
                    contactSpans.add(new ContactPersonSpan(firstName, lastName, sexPrefix, probability));
                } else {
                    // Hm. next token doesn't have name format. Maybe, the firstName is secondName, like - Herr Meister :)
                    lastName = firstName;
                    contactSpans.add(new ContactPersonSpan(null, lastName, sexPrefix, probability));
                }
            }
        }

        return removeDuplicated(contactSpans);
    }

    private List<ContactPersonSpan> removeDuplicated(List<ContactPersonSpan> contactSpans) {
        return contactSpans.parallelStream().distinct().collect(Collectors.toList());
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
        if (this.manSalutations.contains(sexText)) {
            return "M";
        } else if (this.womenSalutations.contains(sexText)) {
            return "W";
        } else {
            return "N";
        }
    }

    public List<ContactPersonSpan> find(String text, TokenizerModel tokenizerModel) {
        Tokenizer tokenizer = new TokenizerME(tokenizerModel);
        String[] tokens = tokenizer.tokenize(text);
        return find(tokens);
    }

    @Override
    public List<ContactPersonSpan> find(String text) {
        try (InputStream tokenizerModelInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
                ModelPath.DE_TOKEN_BIN)) {
            TokenizerModel modelToken = new TokenizerModel(tokenizerModelInputStream);
            return find(text, modelToken);
        } catch (Exception e) {
            LOG.error("Tokenizer Models can not be loaded successfully!", e);
        }

        return Collections.EMPTY_LIST;
    }

    private double[] probs(Span[] spans) {
        double[] sprobs = new double[spans.length];
        double[] probs = this.bestSequence.getProbs();
        for (int i = 0; i < spans.length; ++i) {
            double p = 0.0D;

            for (int oi = spans[i].getStart(); oi < spans[i].getEnd(); ++oi) {
                p += probs[oi];
            }

            p /= (double) spans[i].length();
            sprobs[i] = p;
        }

        return sprobs;
    }

    private double[] probs(List<Span> spans) {
        return probs(spans.toArray(new Span[spans.size()]));
    }

    public void clearAdaptiveData() {
        this.contextGenerator.clearAdaptiveData();
        this.manSalutations.clear();
        this.womenSalutations.clear();
    }
}
