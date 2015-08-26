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

package com.civis.utils.opennlp.features;

import opennlp.tools.util.featuregen.FeatureGeneratorAdapter;
import opennlp.tools.util.featuregen.StringPattern;

import java.util.List;

/**
 * This generator check first letter of token.
 * <p/>
 * If token value is a upper letter char than add feature with prefix "flup".
 */
public class NumberFeature extends FeatureGeneratorAdapter {

    public static final String NUMBER_PREFIX = "numb";

    public NumberFeature() {
    }

    @Override
    public void createFeatures(List<String> features, String[] tokens, int index, String[] previousOutcomes) {
        String token = tokens[index];
        if (token != null && token.length() > 0) {
            if (StringPattern.recognize(token).isAllDigit()) {
                features.add(NUMBER_PREFIX + "=" + token.toLowerCase());
            }
        }
    }
}
