package com.civis.utils.opennlp.validators;

import com.civis.utils.opennlp.features.StreetNumberFeature;
import opennlp.tools.util.SequenceValidator;
import opennlp.tools.util.featuregen.StringPattern;

/**
 * Address sequence validator
 */
public class AddressFinderSequenceValidator implements SequenceValidator<String> {

    public boolean validSequence(int index, String[] inputSequence, String[] outcomesSequence, String outcome) {
        if (outcome.startsWith("address")) {
            if (outcome.endsWith("start")) {
                StringPattern stringPattern = StringPattern.recognize(inputSequence[index]);
                return stringPattern.isInitialCapitalLetter();
            } else if (outcome.endsWith("count")) {
                String sequence = inputSequence[index];
                StringPattern stringPattern = StringPattern.recognize(sequence);
                return stringPattern.isInitialCapitalLetter() || StreetNumberFeature.STREET_NUMBER_PATTERN.matcher(sequence).matches() || stringPattern.isAllDigit() ;
            }
        }
        return true;
    }
}
