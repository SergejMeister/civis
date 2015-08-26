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

package com.civis.utils.opennlp.utils;

import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Utility to read training data from text file.
 * <p/>
 * Default training folder is '/train'
 */
public class IOTrain {

    public static final String TRAIN_FOLDER = "train";
    public static final String MODEL_FOLDER = "models";
    public static final Charset CHARSET = Charset.forName("UTF-8");
    private final static Logger LOG = LoggerFactory.getLogger(IOTrain.class);

    /**
     * Read train data from text file in default 'train' folder;
     */
    public static ObjectStream<NameSample> readData(String trainFileName) throws FileNotFoundException {
        URL resourceTrainUrl = Thread.currentThread().getContextClassLoader().getResource(TRAIN_FOLDER);
        String trainInputPath = resourceTrainUrl.getPath() + "/" + trainFileName;
        ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(trainInputPath), CHARSET);
        return new NameSampleDataStream(lineStream);
    }

    /**
     * Write trained model into bin file.
     */
    public static void writeData(TokenNameFinderModel model, String modelFileName) {
        URL resourceModelUrl = Thread.currentThread().getContextClassLoader().getResource(MODEL_FOLDER);
        String modelOutputPath = resourceModelUrl.getPath() + "/" + modelFileName;
        FilterOutputStream modelOut = null;
        try (OutputStream output = new FileOutputStream(modelOutputPath)) {
            modelOut = new BufferedOutputStream(output);
            model.serialize(modelOut);
        } catch (FileNotFoundException fne) {
            printWriterErrorLog(fne, modelFileName);
        } catch (IOException e) {
            printWriterErrorLog(e, modelFileName);
        } finally {
            if (modelOut != null) {
                try {
                    modelOut.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage());
                }
            }
        }
    }

    private static void printWriterErrorLog(Exception e, String modelFileName) {
        LOG.error("Error to write a model {} into folder {}", modelFileName, MODEL_FOLDER);
        LOG.error(e.getMessage());
    }

}
