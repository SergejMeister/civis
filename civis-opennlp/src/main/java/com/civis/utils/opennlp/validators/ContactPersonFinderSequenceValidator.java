package com.civis.utils.opennlp.validators;

import opennlp.tools.util.SequenceValidator;

/**
 * ContactPersonSequenceValidator
 */
public class ContactPersonFinderSequenceValidator implements SequenceValidator<String> {


    public boolean validSequence(int index, String[] inputSeqence, String[] outcomesSequence, String outcome) {
        return true;
    }
}
