package civis.com.utils.opennlp;

/**
 * Help utility class to build person training sentence.
 */
public class ModelPersonBuilder {

    public static final String START_TAG = "<START:person>";
    public static final String END_TAG = "<END>";


    public static String build(String person) {
        return START_TAG + SentenceConstants.BLANK_SEPARATOR + person + SentenceConstants.BLANK_SEPARATOR + END_TAG ;//+ SentenceConstants.BLANK_SEPARATOR;
    }
}
