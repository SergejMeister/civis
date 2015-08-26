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

package com.civis.utils.opennlp.models;

import com.civis.utils.opennlp.models.contactperson.ContactPersonFinderMe;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * This is a help utility class to create a new model for openNLP frameworks.
 * <p/>
 * The text format should allow the form:
 * -one sentence per line
 * -names marked up with <START> and <END> tags
 */
public class ModelBuilder {


    /**
     * Load trainings data, train the model and create output file.
     *
     * @param inputPath  relative input path
     * @param outputPath relative output path
     */
    public static void build(String inputPath, String outputPath) throws
            IOException {
        Charset charset = Charset.forName("UTF-8");

        TokenNameFinderModel model;
        ObjectStream<NameSample> sampleStream = null;
        try {
            ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(inputPath), charset);
            sampleStream = new NameSampleDataStream(lineStream);

            model = ContactPersonFinderMe.train("de", "contact-person", sampleStream, 1);
        } finally {
            sampleStream.close();
        }
        FilterOutputStream modelOut = null;
        try {
            modelOut = new BufferedOutputStream(new FileOutputStream(outputPath));
            model.serialize(modelOut);
        } finally {
            if (modelOut != null) {
                modelOut.close();
            }
        }
    }
}
