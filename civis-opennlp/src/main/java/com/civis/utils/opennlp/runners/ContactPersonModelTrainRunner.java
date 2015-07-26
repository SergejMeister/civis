package com.civis.utils.opennlp.runners;



import com.civis.utils.opennlp.models.ModelBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Sergej Meister on 6/12/15.
 */
public class ContactPersonModelTrainRunner {

    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String BLANK_SEPARATOR = " ";

    public static void main(String[] args) {
        URL resourceTrainUrl = Thread.currentThread().getContextClassLoader().getResource("train");
        String dePersonTrain = resourceTrainUrl.getPath() + "/formated.txt";
        URL resourceModelUrl = Thread.currentThread().getContextClassLoader().getResource("models");
        String modelOutputPath = resourceModelUrl.getPath() + "/de-contact-person.bin";
        try {
            ModelBuilder.build(dePersonTrain, modelOutputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static StringBuilder getTrainTextPattern(String pathToTrainTextPattern) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = null;
        try {
            InputStream inputStream =
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(pathToTrainTextPattern);
            br = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(NEW_LINE_SEPARATOR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return stringBuilder;
    }
}
