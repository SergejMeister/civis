/*
 * Copyright 2015 Sergej Meister
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.civis.utils.opennlp.models.contactperson;

import com.civis.utils.opennlp.models.BaseModelTest;
import com.civis.utils.opennlp.models.ModelFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class ContactPersonFinderTest extends BaseModelTest {

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

        ContactPersonSpan contactSpanSergej = contactPersonSpans.get(0);
        Assert.assertEquals("Sergej", contactSpanSergej.getFirstName());
        Assert.assertEquals("Meister", contactSpanSergej.getSecondName());
        Assert.assertEquals("M", contactSpanSergej.getSex());
        Assert.assertTrue("Probability should be greater than 0.6 !", contactSpanSergej.getProbability() > 0.5);

        ContactPersonSpan contactSpanCaroline = contactPersonSpans.get(1);
        Assert.assertEquals("Caroline", contactSpanCaroline.getFirstName());
        Assert.assertEquals("Eckl", contactSpanCaroline.getSecondName());
        Assert.assertEquals("N", contactSpanCaroline.getSex());
        Assert.assertTrue("Probability should be greater than 0.6 !", contactSpanCaroline.getProbability() > 0.6);
    }

    @Test
    public void testIvuJob() {
        String filePath = "text/ivu.txt";
        String exampleText = getTextExample(filePath);
        ContactPersonFinder contactPersonFinder = ModelFactory.getContactPersonFinder();
        List<ContactPersonSpan> contactPersonSpans = contactPersonFinder.find(exampleText);
        Assert.assertEquals("Should be exact one contact person!", 1, contactPersonSpans.size());
        ContactPersonSpan contactSpan = contactPersonSpans.get(0);
        Assert.assertEquals("Sofia", contactSpan.getFirstName());
        Assert.assertEquals("Arngold", contactSpan.getSecondName());
        Assert.assertEquals("W", contactSpan.getSex());
    }
}