package com.civis.utils.opennlp.models;

import opennlp.tools.namefind.NameSample;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;

import java.util.Map;

/**
 * Helper to build TrainConfigData object.
 */
public class TrainConfigDataBuilder {

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
     * Language code - en, de, rus ;
     */
    public TrainConfigDataBuilder setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
        return this;
    }

    /**
     * Model type, usually it's name of created model.
     */
    public TrainConfigDataBuilder setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Training data.
     */
    public TrainConfigDataBuilder setSamples(ObjectStream<NameSample> samples) {
        this.samples = samples;
        return this;
    }

    public TrainConfigDataBuilder setTrainingParameters(TrainingParameters trainingParameters) {
        this.trainingParameters = trainingParameters;
        return this;
    }

    /**
     * Put cutoff into trainingParameters.
     * If trainingParameters is null, than create default trainingParameters and put cutoff value.
     */
    public TrainConfigDataBuilder putCutoffIntoTrainingParameters(String cutoff) {
        createDefaultTrainingsParameterIfNull();
        trainingParameters.put("Cutoff", cutoff);
        return this;
    }

    private void createDefaultTrainingsParameterIfNull() {
        if(trainingParameters == null){
            trainingParameters = TrainingParameters.defaultParams();
        }
    }

    public TrainConfigDataBuilder setFeatureGenerator(AdaptiveFeatureGenerator featureGenerator) {
        this.featureGenerator = featureGenerator;
        return this;
    }

    public TrainConfigDataBuilder setResources(Map<String, Object> resources) {
        this.resources = resources;
        return this;
    }



    public TrainConfigData build() {
        TrainConfigData trainConfigData = new TrainConfigData();
        trainConfigData.setLanguageCode(languageCode);
        trainConfigData.setType(type);
        trainConfigData.setSamples(samples);
        trainConfigData.setTrainingParameters(trainingParameters);
        trainConfigData.setFeatureGenerator(featureGenerator);
        trainConfigData.setResources(resources);
        return trainConfigData;
    }
}
