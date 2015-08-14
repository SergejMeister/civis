package com.civis.utils.opennlp.runners;


import com.civis.utils.opennlp.models.ModelFactory;
import com.civis.utils.opennlp.models.contactperson.ContactPersonFinder;
import com.civis.utils.opennlp.models.contactperson.ContactPersonSpan;
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

    private final static Logger LOG = LoggerFactory.getLogger(ContactPersonFinderRunner.class);

    public static final String JOB_BERRNER_MATTNER_PATH = "text/bernerMattnerJob.txt";
    public static final String JOB_ADO_PATH = "text/adoJob.txt";
    public static final String JOB_MATECO_PATH = "text/mateco.txt";

    public static void main(String[] args) {
        runWithFile(JOB_BERRNER_MATTNER_PATH);
        runWithFile(JOB_ADO_PATH);
        runWithFile(JOB_MATECO_PATH);
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
            jobContent = IOUtils.toString(inputStream, "UTF-8");
            inputStream.close();
        } catch (IOException e) {
            LOG.error("Exception occurred while reading file ( " + fileName + " )", e);
        }

        return jobContent;
    }
}
