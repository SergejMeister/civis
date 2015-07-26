package civis.com.utils.opennlp;

import opennlp.tools.util.featuregen.FeatureGeneratorAdapter;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Sergej Meister on 6/25/15.
 */
public class ContactPersonFeatureGenerator extends FeatureGeneratorAdapter {
//public class ContactPersonFeatureGenerator implements AdaptiveFeatureGenerator {


    public static final String NAME_PATTERN_PREFIX = "np";
    public static final String NEXT_NAME_PATTERN_PREFIX = "nnp";
    //    public static final String CONTACT_PERSON_START_PREFIX = "contact-person-start";
//    public static final String CONTACT_PERSON_END_PREFIX = "contact-person-end";
    //public static final String CONTACT_PERSON_START_PREFIX = "contact-start";
    //public static final String CONTACT_PERSON_END_PREFIX = "contact-end";

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

//    public void updateAdaptiveData(String[] tokens, String[] outcomes) {
//        for (int i = 0; i < tokens.length; i++) {
//            String token = tokens[i].trim();
//            if (NAME_PATTERN.matcher(token).matches()) {
//                int nextIndex = i + 1;
//                if (nextIndex < tokens.length) {
//                    String nextToken = tokens[nextIndex].trim();
//                    if (NAME_PATTERN.matcher(nextToken).matches()) {
//                        outcomes[i] = NEXT_NAME_PATTERN_PREFIX;
//                        outcomes[nextIndex] = NEXT_NAME_PATTERN_PREFIX;
//                    }else{
//                        if(nextToken.equals("von")){
//                            //TODO: remove fixed germany word!
//                            //Germany von article
//                            //Example. Johann Wolfgang von Goethe
//                            nextIndex = nextIndex + 1;
//                            if (nextIndex < tokens.length) {
//                                nextToken = tokens[nextIndex].trim();
//                                if (NAME_PATTERN.matcher(nextToken).matches()) {
//                                    outcomes[i] = NEXT_NAME_PATTERN_PREFIX;
//                                    outcomes[nextIndex] = NEXT_NAME_PATTERN_PREFIX;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
////            String token = tokens[i].trim();
////            if (NAME_PATTERN.matcher(token).matches()) {
////                int nextIndex = i + 1;
////                if (nextIndex < tokens.length) {
////                    String nextToken = tokens[nextIndex].trim();
////                    if (NAME_PATTERN.matcher(nextToken).matches()) {
////                        outputs[i] = CONTACT_PERSON_START_PREFIX;
////                        outputs[nextIndex] = CONTACT_PERSON_END_PREFIX;
////                        //features.add(NEXT_NAME_PATTERN_PREFIX + "=" + token.toLowerCase());
////                    }
////                }
////            }
//        }
//    }
//
//    public void clearAdaptiveData() {
//
//    }
}
