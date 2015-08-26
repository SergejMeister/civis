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
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * This feature generator check token for address start point.
 * <p/>
 * If token value is a upper letter char than add feature with prefix "flup".
 */
public class AddressFeature extends FeatureGeneratorAdapter {

    public static final String ADDRESS_START_PREFIX = "ma";  // ma = maybe address :)
    public static final String ADDRESS_STREET_PREFIX = "ms"; // ms = maybe street :)
    public static final String ADDRESS_STREET_NUMBER_PREFIX = "msn"; // ms = maybe street number :)
    public static final String ADDRESS_ZIP_PREFIX = "mz"; // ms = maybe zip :)
    public static final String ADDRESS_CITY_PREFIX = "mc"; // ms = maybe city :)
    public static final String ADDRESS_COUNTRY_PREFIX = "mcou"; // ms = maybe country :)

    //\p{L} compare to [A-Za-z] matches germany letters like ß,ü,ö
    public static final Pattern STREET_PATTERN = Pattern.compile("[\\p{L}-.]+");


    public AddressFeature() {
    }

    @Override
    public void createFeatures(List<String> features, String[] tokens, int index, String[] previousOutcomes) {
        int minAddressSpanSize = 4;
        if (tokens.length >= index + minAddressSpanSize) {
            //min. - Street, number, zip, city.
            int maybeStreetIndex = index;
            String maybeStreetValue = tokens[maybeStreetIndex];
            if (checkWord(maybeStreetValue)) {
                int maybeStreetNumberIndex = maybeStreetIndex + 1;
                String maybeStreetNumberValue = tokens[maybeStreetNumberIndex];
                if (checkStreetNumber(maybeStreetNumberValue)) {
                    int maybeZipIndex = maybeStreetNumberIndex + 1;
                    String maybeZipValue = tokens[maybeZipIndex];
                    if (checkDigit(maybeZipValue)) {
                        int maybeCityIndex = maybeZipIndex + 1;
                        String maybeCityValue = tokens[maybeCityIndex];
                        if (checkWord(maybeCityValue)) {
                            features.add(ADDRESS_START_PREFIX + "=" + maybeStreetValue.toLowerCase());
                            features.add(ADDRESS_STREET_PREFIX + "=" + maybeStreetValue.toLowerCase());
                            features.add(ADDRESS_STREET_NUMBER_PREFIX + "=" + maybeStreetNumberValue.toLowerCase());
                            features.add(ADDRESS_ZIP_PREFIX + "=" + maybeZipValue.toLowerCase());
                            features.add(ADDRESS_CITY_PREFIX + "=" + maybeCityValue.toLowerCase());
                            int maybeCountryIndex = maybeCityIndex + 1;
                            if (checkCountry(tokens, maybeCountryIndex)) {
                                features.add(ADDRESS_COUNTRY_PREFIX + "=" + tokens[maybeCountryIndex].toLowerCase());
                            }
                        }
                    }
                }
            }
        }
    }

    private Boolean checkWord(String token) {
        return StringUtils.isNotBlank(token) && StringPattern.recognize(token).isInitialCapitalLetter() &&
                STREET_PATTERN.matcher(token).matches();
    }

    private Boolean checkCountry(String[] tokens, int maybeCountryIndex) {
        if (maybeCountryIndex < tokens.length) {
            String maybeCountryValue = tokens[maybeCountryIndex];
            if (StringUtils.isNotBlank(maybeCountryValue)) {
                StringPattern stringPattern = StringPattern.recognize(maybeCountryValue);
                return stringPattern.isInitialCapitalLetter() && STREET_PATTERN.matcher(maybeCountryValue).matches();
            }
        }

        return false;
    }

    private Boolean checkDigit(String token) {
        return StringUtils.isNotBlank(token) && StringPattern.recognize(token).isAllDigit();
    }

    private Boolean checkStreetNumber(String token) {
        return StringUtils.isNotBlank(token) && StreetNumberFeature.STREET_NUMBER_PATTERN.matcher(token).matches();
    }
}
