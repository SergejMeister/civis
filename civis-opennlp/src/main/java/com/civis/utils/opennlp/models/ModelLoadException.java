package com.civis.utils.opennlp.models;

/**
 * Exception, if OpenNlp model can not be loaded successfully!
 */
public class ModelLoadException extends RuntimeException {

    public ModelLoadException(String modelName) {
        super("Model (" + modelName + ") could not be loaded!");
    }
}
