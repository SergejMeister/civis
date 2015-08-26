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


import com.civis.utils.opennlp.models.ModelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * Utility class to train a model.
 */
public class ContactPersonModelTrainRunner {

    private final static Logger LOG = LoggerFactory.getLogger(ContactPersonModelTrainRunner.class);

    public static void main(String[] args) {
        URL resourceTrainUrl = Thread.currentThread().getContextClassLoader().getResource("train");
        String dePersonTrain = resourceTrainUrl.getPath() + "/formated.txt";
        URL resourceModelUrl = Thread.currentThread().getContextClassLoader().getResource("models");
        String modelOutputPath = resourceModelUrl.getPath() + "/de-contact-person.bin";
        try {
            ModelBuilder.build(dePersonTrain, modelOutputPath);
        } catch (IOException e) {
            LOG.error("Exception occurred in model build process", e);
        }
    }
}
