package civis.com.utils.runners;

import civis.com.utils.opennlp.ContactPersonFinder;
import civis.com.utils.opennlp.ContactPersonSpan;
import civis.com.utils.opennlp.ModelFactory;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Runner to test model!
 */
public class ContactPersonFinderRunner {

    public static final String JOB_BERRNER_MATTNER_PATH = "text/bernerMattnerJob.txt";
    public static final String JOB_ADO_PATH = "text/adoJob.txt";

    private final static Logger LOG = LoggerFactory.getLogger(ContactPersonFinderRunner.class);

    public static void main(String[] args) {
        runWithFile(JOB_BERRNER_MATTNER_PATH);
        runWithFile(JOB_ADO_PATH);
    }

    private static void runWithFile(String filePath) {
        String exampleText = getTextExample(filePath);
        ContactPersonFinder contactPersonFinder = ModelFactory.getContactPersonFinder();
        List<ContactPersonSpan> contactPersonSpans = contactPersonFinder.find(exampleText);
        System.out.println("ListSize: " + contactPersonSpans.size());
        for (ContactPersonSpan contactSpan : contactPersonSpans) {
            System.out.println("firstName: " + contactSpan.getFirstName());
            System.out.println("secondName: " + contactSpan.getSecondName());
            String sex = contactSpan.getSex() == null ? "NULL" : contactSpan.getSex();
            System.out.println("sex: " + sex);
            System.out.println("probability: " + contactSpan.getProbability());
        }
        printRunnerSeparator();
    }

    private static void printRunnerSeparator() {
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------");
    }

    private static String getTextExample(String fileName) {
        String jobContent = "";
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            jobContent = IOUtils.toString(inputStream, "ISO-8859-1");
            inputStream.close();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

        return jobContent;
    }
}
