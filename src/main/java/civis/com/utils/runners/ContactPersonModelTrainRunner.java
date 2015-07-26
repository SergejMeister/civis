package civis.com.utils.runners;

import civis.com.utils.opennlp.ModelBuilder;

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


//    public static void main(String[] args) {
//        String pathToTrainTextPattern = "train/de-contact-person-train.txt";
//        StringBuilder trainTextPattern = getTrainTextPattern(pathToTrainTextPattern);
//
//        String namesCsvPath = "names.csv";
////                "/media/sergej/windows/data/Projekte/civis/utils/src/main/resources/civis/com/utils/runners/names.csv";
//        List<CSVNameData> listOfPersonNames = CSVNamesReader.read(namesCsvPath);
//
//        StringBuilder trainTextBuilder = new StringBuilder();
//        for (CSVNameData csVNameData : listOfPersonNames) {
//            char firstChar = Character.toUpperCase(csVNameData.getName().charAt(0));
//            String firstCharUpperName = firstChar + csVNameData.getName().substring(1);
//            Pattern p = Pattern.compile("<FIRSTNAME>");
//            Matcher m = p.matcher(trainTextPattern);
//            String tempTrainText = m.replaceAll(firstCharUpperName);
//            trainTextBuilder.append(tempTrainText);
//        }
//
//        Writer out = null;
//        URL resourceTrainUrl = Thread.currentThread().getContextClassLoader().getResource("train");
//        String dePersonTrain = resourceTrainUrl.getPath() + "/de-contact-person.train";
////        String dePersonTrain =
////                "/media/sergej/windows/data/Projekte/civis/utils/src/main/resources/civis/com/utils/runners/models/de-person.train";
//        try {
//            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dePersonTrain), "ISO-8859-1"));
//            out.append(trainTextBuilder);
//        } catch (Exception e) {
//            System.out.println("Error in write file to - " + dePersonTrain);
//            e.printStackTrace();
//        } finally {
//            try {
//                out.flush();
//                out.close();
//            } catch (IOException e) {
//                System.out.println("Error while flushing/closing fileWriter !!!");
//                e.printStackTrace();
//            }
//        }
//
////        String inputPath =
////                "/media/sergej/windows/data/Projekte/civis/utils/src/main/resources/civis/com/utils/runners/models";
//        //String inputPath = "models";
//        //String trainFileName = "de-contact-person.train";
//        URL resourceModelUrl = Thread.currentThread().getContextClassLoader().getResource("models");
//        String modelOutputPath = resourceModelUrl.getPath() + "/de-contact-person.bin";
//        String outputPath = "models";
//        //String modelName = "de-person";
//        try {
//            //ModelBuilder.build(inputPath, trainFileName, outputPath, modelName);
//            ModelBuilder.build(dePersonTrain, modelOutputPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

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
