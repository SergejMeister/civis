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

package com.civis.utils.opennlp.runners;


import com.civis.utils.opennlp.models.TrainConfigData;
import com.civis.utils.opennlp.models.TrainConfigDataBuilder;
import com.civis.utils.opennlp.models.contactperson.ContactPersonFinderMe;
import com.civis.utils.opennlp.utils.IOTrain;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Utility class to train a model.
 */
public class ContactPersonModelTrainRunner {

    private final static Logger LOG = LoggerFactory.getLogger(ContactPersonModelTrainRunner.class);

    public static void main(String[] args) {
        ObjectStream<NameSample> sampleStream = null;
        try {
            sampleStream = IOTrain.readData("contact-train.txt");
            TrainConfigData trainConfigData =
                    new TrainConfigDataBuilder().setLanguageCode("de").setType("contact-person")
                            .setSamples(sampleStream)
                            .putCutoffIntoTrainingParameters("1").build();

            TokenNameFinderModel model = ContactPersonFinderMe.initializeTrainModel(trainConfigData).train();
            IOTrain.writeData(model, "de-contact-person.bin");
        } catch (Exception e) {
            LOG.error("Exception occurred in model build process", e);
        } finally {
            if (sampleStream != null) {
                try {
                    sampleStream.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage());
                }
            }
        }
    }
}
