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

import java.util.List;
import java.util.regex.Pattern;

/**
 * Contact persons features.
 *
 * The first name begins with Upper case.
 * Second name should begin with Upper case too.
 */
public class ContactPersonFeatureGenerator extends FeatureGeneratorAdapter {

    //Feature, if a word matches the name pattern.
    public static final String NAME_PATTERN_PREFIX = "np";

    //Feature, if a next word matches the name pattern.
    public static final String NEXT_NAME_PATTERN_PREFIX = "nnp";

    //Only letters and minus character(-)
    //First letter is upper Character.
    public static final Pattern NAME_PATTERN = Pattern.compile("^[A-Z][a-z][a-z\\-]*");

    public void createFeatures(List<String> features, String[] tokens, int index, String[] preds) {
        String token = tokens[index].trim();
        if (NAME_PATTERN.matcher(token).matches()) {
            features.add(NAME_PATTERN_PREFIX + "=" + token.toLowerCase());
            int nextIndex = index + 1;
            if (nextIndex < tokens.length) {
                String nextToken = tokens[nextIndex].trim();
                if (NAME_PATTERN.matcher(nextToken).matches()) {
                    features.add(NEXT_NAME_PATTERN_PREFIX + "=" + token.toLowerCase());
                }else{
                    if(nextToken.equals("von")) {
                        //TODO: remove fixed germany word!
                        //Germany von article
                        //Example. Johann Wolfgang von Goethe
                        nextIndex = nextIndex + 1;
                        if (nextIndex < tokens.length) {
                            nextToken = tokens[nextIndex].trim();
                            if (NAME_PATTERN.matcher(nextToken).matches()) {
                                features.add(NEXT_NAME_PATTERN_PREFIX + "=" + token.toLowerCase());
                            }
                        }
                    }
                }
            }
        }
    }
}
