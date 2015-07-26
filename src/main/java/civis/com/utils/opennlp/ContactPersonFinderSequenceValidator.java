package civis.com.utils.opennlp;

import opennlp.tools.util.SequenceValidator;

/**
 * ContactPersonSequenceVallidator
 */
public class ContactPersonFinderSequenceValidator implements SequenceValidator<String> {


    public boolean validSequence(int index, String[] inputSequence, String[] outcomesSequence, String outcome) {
        //NameFinderSequenceValidator,
//        if (outcome.endsWith("contact-start")) {
//            int lastIndex = outcomesSequence.length - 1;
//            if (lastIndex == -1) {
//                return false;
//            }
//
//            if (outcomesSequence[lastIndex].endsWith("other")) {
//                return false;
//            }
//
////            if (outcomesSequence[lastIndex].endsWith("cont")) {
////                String previousNameType = NameFinderME.extractNameType(outcomesSequence[lastIndex]);
////                String nameType = NameFinderME.extractNameType(outcome);
////                if (previousNameType != null || nameType != null) {
////                    if (nameType != null && nameType.equals(previousNameType)) {
////                        return true;
////                    }
////
////                    return false;
////                }
////            }
//        }
        return true;
    }
}
