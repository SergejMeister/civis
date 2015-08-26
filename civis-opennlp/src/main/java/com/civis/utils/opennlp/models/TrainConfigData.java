package com.civis.utils.opennlp.models;

import opennlp.tools.namefind.NameSample;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;

import java.util.Map;

/**
 * Model include all params for training methods.
 */
public class TrainConfigData {

    /**
     * Language code - en, de, rus ;
     */
    private String languageCode;

    /**
     * Model type, usually it's name of created model.
     */
    private String type;

    /**
     * Training data.
     */
    private ObjectStream<NameSample> samples;


    private TrainingParameters trainingParameters;

    private AdaptiveFeatureGenerator featureGenerator;

    private Map<String, Object> resources;


    /**
     * Returns Language code - en, de, rus ;
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets Language code - en, de, rus ;
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Returns Model type, usually it's name of created model.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets Model type, usually it's name of created model.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns Training data.
     */
    public ObjectStream<NameSample> getSamples() {
        return samples;
    }

    /**
     * Sets Training data.
     */
    public void setSamples(ObjectStream<NameSample> samples) {
        this.samples = samples;
    }

    public TrainingParameters getTrainingParameters() {
        return trainingParameters;
    }

    public void setTrainingParameters(TrainingParameters trainingParameters) {
        this.trainingParameters = trainingParameters;
    }

    public AdaptiveFeatureGenerator getFeatureGenerator() {
        return featureGenerator;
    }

    public void setFeatureGenerator(AdaptiveFeatureGenerator featureGenerator) {
        this.featureGenerator = featureGenerator;
    }

    public Map<String, Object> getResources() {
        return resources;
    }

    public void setResources(Map<String, Object> resources) {
        this.resources = resources;
    }

    /**
     * If language code is null or empty throws <code>IllegalArgumentException.java</code>.
     */
    public void ensureLanguageNotBlank() {
        if(languageCode == null || languageCode.isEmpty()){
            throw new IllegalArgumentException("languageCode must not be null!");
        }
    }
}
