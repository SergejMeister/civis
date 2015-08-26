package com.civis.utils.opennlp.models;

import opennlp.model.AbstractModel;
import opennlp.model.MaxentModel;
import opennlp.model.TrainUtil;
import opennlp.tools.namefind.DefaultNameContextGenerator;
import opennlp.tools.namefind.NameFinderEventStream;
import opennlp.tools.namefind.NameSampleSequenceStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.BeamSearch;
import opennlp.tools.util.Sequence;
import opennlp.tools.util.Span;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base Model for all model finder classes.
 */
public abstract class BaseModel<T> {

    protected static final int DEFAULT_BEAM_SIZE = 3;
    protected TrainConfigData trainConfigData;
    protected MaxentModel nameFinderModel;
    protected Sequence bestSequence;
    protected BeamSearch<String> beam;


    public BaseModel(MaxentModel nameFinderModel) {
        this.nameFinderModel = nameFinderModel ;
    }

    public BaseModel(TrainConfigData trainConfigData) {
        this.trainConfigData = trainConfigData;
        //exception if language null or empty.
        trainConfigData.ensureLanguageNotBlank();
        setFeaturesIfNull();
        setEmptyResourceIfNull();
    }

    /**
     * Create specified model features.
     */
    public abstract AdaptiveFeatureGenerator createDefaultFeatureGenerator();

    private void setFeaturesIfNull() {
        if (trainConfigData.getFeatureGenerator() == null) {
            trainConfigData.setFeatureGenerator(createDefaultFeatureGenerator());
        }
    }

    private void setEmptyResourceIfNull() {
        if (trainConfigData.getResources() == null) {
            trainConfigData.setResources(Collections.emptyMap());
        }
    }


    protected void setDefaultTrainingParametersIfNull() {
        if (trainConfigData.getTrainingParameters() == null) {
            trainConfigData.setTrainingParameters(TrainingParameters.defaultParams());
        }
    }


    protected TokenNameFinderModel train() throws IOException {
        HashMap<String, String> manifestInfoEntries = new HashMap<>();
        TrainingParameters trainingParameters = trainConfigData.getTrainingParameters();
        AbstractModel nameFinderModel;
        if (!TrainUtil.isSequenceTraining(trainingParameters.getSettings())) {
            NameFinderEventStream ss =
                    new NameFinderEventStream(trainConfigData.getSamples(), trainConfigData.getType(),
                            new DefaultNameContextGenerator(trainConfigData.getFeatureGenerator()));
            nameFinderModel = TrainUtil.train(ss, trainingParameters.getSettings(), manifestInfoEntries);
        } else {
            NameSampleSequenceStream ss1 =
                    new NameSampleSequenceStream(trainConfigData.getSamples(), trainConfigData.getFeatureGenerator());
            nameFinderModel = TrainUtil.train(ss1, trainingParameters.getSettings(), manifestInfoEntries);
        }

        return new TokenNameFinderModel(trainConfigData.getLanguageCode(), nameFinderModel,
                trainConfigData.getResources(), manifestInfoEntries);
    }

    protected List<T> removeDuplicated(List<T> spans) {
        return spans.parallelStream().distinct().collect(Collectors.toList());
    }

    protected double[] probs(Span[] spans) {
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

    protected double[] probs(List<Span> spans) {
        return probs(spans.toArray(new Span[spans.size()]));
    }
}
