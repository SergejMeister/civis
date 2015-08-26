package com.civis.utils.opennlp.models;

import opennlp.tools.namefind.TokenNameFinderModel;

import java.io.IOException;

/**
 * Interface with train services.
 */
public interface TrainModel {

    /**
     * Train model.
     */
    TokenNameFinderModel train() throws IOException;
}
