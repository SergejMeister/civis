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
