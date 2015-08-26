package com.civis.utils.opennlp.models;

import opennlp.tools.tokenize.TokenizerModel;

import java.util.List;

/**
 * Generic default finder interface.
 */
public interface DefaultFinder<T> {

    /**
     * Find all affected items in text.
     *
     * @param tokens tokenized text.
     *
     * @return list of founded items.
     */
    List<T> find(String[] tokens);

    /**
     * Find all affected items in text.
     *
     * @param text           plain text.
     * @param tokenizerModel model to tokenize the given text.
     *
     * @return list of founded items.
     */
    List<T> find(String text, TokenizerModel tokenizerModel);

    /**
     * Find all affected items in text.
     * <p/>
     * Default  tokenizerModel <code>TokenNameFinderModel</code>
     *
     * @param text plain text.
     *
     * @return list of founded items.
     */
    List<T> find(String text);
}
