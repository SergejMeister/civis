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

package com.civis.utils.opennlp.models.contactperson;

import opennlp.tools.tokenize.TokenizerModel;

import java.util.List;

/**
 * This interface include methods to find contact person.
 */
public interface ContactPersonFinder {

    /**
     * Find all contact person spans in text.
     *
     * @param tokens tokenized text.
     * @return list of <code>ContactSpan</code>.
     */
    List<ContactPersonSpan> find(String[] tokens);

    /**
     * Find all contact person spans in text.
     *
     * @param text plain text.
     * @param tokenizerModel model to tokenize the given text.
     * @return list of <code>ContactSpan</code>.
     */
    List<ContactPersonSpan> find(String text, TokenizerModel tokenizerModel);

    /**
     * Find all contact person spans in text.
     *
     * Default  tokenizerModel <code>TokenNameFinderModel</code>
     *
     * @param text plain text.
     * @return list of <code>ContactPersonSpan</code>.
     */
    List<ContactPersonSpan> find(String text);

    /**
     * Clear Data.
     */
    void clearAdaptiveData();
}
