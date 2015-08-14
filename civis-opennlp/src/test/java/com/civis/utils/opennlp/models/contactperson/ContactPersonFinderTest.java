package com.civis.utils.opennlp.models.contactperson;

import com.civis.utils.opennlp.models.ModelFactory;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class ContactPersonFinderTest extends TestCase {

    @Test
    public void testAdoJob() {
        String filePath = "text/adoJob.txt";
        String exampleText = getTextExample(filePath);
        ContactPersonFinder contactPersonFinder = ModelFactory.getContactPersonFinder();
        List<ContactPersonSpan> contactPersonSpans = contactPersonFinder.find(exampleText);
        Assert.assertEquals("Should be exact one contact person!", 1, contactPersonSpans.size());
        ContactPersonSpan contactSpan = contactPersonSpans.get(0);
        Assert.assertNull("Firstname should be null!", contactSpan.getFirstName());
        Assert.assertEquals("Falke", contactSpan.getSecondName());
        Assert.assertEquals("W", contactSpan.getSex());
        Assert.assertTrue("Probability should be greater than 0.7 !", contactSpan.getProbability() > 0.7);
    }

    @Test
    public void testMatecoJob() {
        String filePath = "text/mateco.txt";
        String exampleText = getTextExample(filePath);
        ContactPersonFinder contactPersonFinder = ModelFactory.getContactPersonFinder();
        List<ContactPersonSpan> contactPersonSpans = contactPersonFinder.find(exampleText);
        Assert.assertEquals("Should be exact one contact person!", 1, contactPersonSpans.size());
        ContactPersonSpan contactSpan = contactPersonSpans.get(0);
        Assert.assertEquals("Rene", contactSpan.getFirstName());
        Assert.assertEquals("Malonn", contactSpan.getSecondName());
        Assert.assertEquals("M", contactSpan.getSex());
        Assert.assertTrue("Probability should be greater than 0.7 !", contactSpan.getProbability() > 0.7);
    }

    @Test
    public void testBernerMattnerJob() {
        String filePath = "text/bernerMattnerJob.txt";
        String exampleText = getTextExample(filePath);
        ContactPersonFinder contactPersonFinder = ModelFactory.getContactPersonFinder();
        List<ContactPersonSpan> contactPersonSpans = contactPersonFinder.find(exampleText);
        Assert.assertEquals("Should be exact two contact person!", 2, contactPersonSpans.size());

        ContactPersonSpan contactSpanCaroline = contactPersonSpans.get(0);
        Assert.assertEquals("Caroline", contactSpanCaroline.getFirstName());
        Assert.assertEquals("Eckl", contactSpanCaroline.getSecondName());
        Assert.assertEquals("N", contactSpanCaroline.getSex());
        Assert.assertTrue("Probability should be greater than 0.6 !", contactSpanCaroline.getProbability() > 0.6);

        ContactPersonSpan contactSpanSergej = contactPersonSpans.get(1);
        Assert.assertEquals("Sergej", contactSpanSergej.getFirstName());
        Assert.assertEquals("Meister", contactSpanSergej.getSecondName());
        Assert.assertEquals("M", contactSpanSergej.getSex());
        Assert.assertTrue("Probability should be greater than 0.6 !", contactSpanSergej.getProbability() > 0.6);
    }

    private static String getTextExample(String fileName) {
        String jobContent = "";
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            jobContent = IOUtils.toString(inputStream, "UTF-8");
            inputStream.close();
        } catch (IOException e) {
            Assert.fail();
        }

        return jobContent;
    }
}