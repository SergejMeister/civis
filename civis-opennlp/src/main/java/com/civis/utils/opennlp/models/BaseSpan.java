package com.civis.utils.opennlp.models;

/**
 * Base class for all span classes.
 */
public abstract class BaseSpan {

    protected Double probability;


    public BaseSpan () {
    }

    public BaseSpan (Double probability) {
        setProbability(probability);
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }
}
