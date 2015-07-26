package civis.com.utils.opennlp;

import opennlp.tools.tokenize.TokenizerModel;

import java.util.List;

/**
 * ContactPersonFinder interface include methods
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
