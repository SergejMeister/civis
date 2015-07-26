package civis.com.utils.opennlp;

import opennlp.tools.util.SequenceValidator;

/**
 * ContactPersonSequenceVallidator
 */
public class ContactPersonFinderSequenceValidator implements SequenceValidator<String> {


    public boolean validSequence(int index, String[] inputSequence, String[] outcomesSequence, String outcome) {
        return true;
    }
}
