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
