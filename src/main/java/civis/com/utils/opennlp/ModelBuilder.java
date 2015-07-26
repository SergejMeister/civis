package civis.com.utils.opennlp;

import opennlp.tools.namefind.NameFinderME;
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
 * <p>
 * The text format should allow the form:
 * -one sentence per line
 * -names marked up with <START> and <END> tags
 */
public class ModelBuilder {


    /**
     * Load trainings data, train the model and create output file.
     *
     * @param inputPath relative input path
     * @param outputPath relative output path
     */
    public static void build(String inputPath, String outputPath) throws
            IOException {
        // String trainUrl = inputPath + "/" + trainFileName;
        Charset charset = Charset.forName("ISO-8859-1");

        TokenNameFinderModel model;
        ObjectStream<NameSample> sampleStream = null;
        try {
            ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(inputPath), charset);
            sampleStream = new NameSampleDataStream(lineStream);

            model = ContactPersonFinderMe.train("de", "contact-person", sampleStream,1);
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


    /**
     * Load trainings data, train the model and create output file.
     */
    public static void build(String inputPath, String trainFileName, String outputPath, String modelName) throws
            IOException {
        String trainUrl = inputPath + "/" + trainFileName;
        Charset charset = Charset.forName("ISO-8859-1");


        TokenNameFinderModel model;
        ObjectStream<NameSample> sampleStream = null;
        try {
            ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(trainUrl), charset);
            sampleStream = new NameSampleDataStream(lineStream);
            model = ContactPersonFinderMe.train("de", "person", sampleStream);
        } finally {
            sampleStream.close();
        }
        //NameFinderME.train
        String modelFilePath = outputPath + "/" + modelName + ".bin";
        FilterOutputStream modelOut = null;
        try {
            modelOut = new BufferedOutputStream(new FileOutputStream(modelFilePath));
            model.serialize(modelOut);
        } finally {
            if (modelOut != null) {
                modelOut.close();
            }
        }


//        //load trained data into memory
//        //names marked up with <START> and <END> tags
//        //one sentence per line
//        String trainUrl = inputPath + "/" + trainFileName;
////        File inFile = new File(trainUrl);
//
//        //create NameSampleDataStream
//        //converts tagged strings from trained data into NameSample objects
//        //populated in next step
//        //NameSampleDataStream nss = null;
////        ObjectStream<NameSample> sampleStream;
//        TokenNameFinderModel model = null;
//        try {
//            Charset charset = Charset.forName("ISO-8859-1");
//            ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(trainUrl), charset);
//            ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);
//            model = NameFinderME.train("de", "person", sampleStream, TrainingParameters.defaultParams(),null, Collections.<String, Object> emptyMap());
////            nss = new NameSampleDataStream(new PlainTextByLineStream(new java.io.FileReader(inFile)));
//
//
//        }
//        catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
//
//        //create "person" model
//
////        int iterations = 100;
////        //int cutoff = 5;
////        //int iterations = 1;
////        int cutoff = 1;
////
////        try {
////            model = NameFinderME.train("de", "person", sampleStream, TrainingParameters.defaultParams(),
////                    null, Collections.<String, Object> emptyMap());
//////            model = NameFinderME.train(
//////                    "de", //language of the training data (relevant to tokenization)
//////                    "person", //type of model
//////                    nss, //the NameSample collection, created above
//////                    (AdaptiveFeatureGenerator) null, //null=use default set of feature generators for NE detection
//////                    Collections.<String, Object> emptyMap(), //empty, not adding additional resources to the model
//////                    iterations, //number of iterations before the model outputs, not important
//////                    cutoff); //lower bound for the number of times a feature exists before it is included in the model
////        }
////        catch (Exception ex) {
////            System.out.println(ex.getMessage());
////        }
//
//        //save the model to disk
//        //used in testing and production
//
//        File outFile ;
//        String modelFilePath = outputPath + "/" + modelName + ".bin";
//        try {
//            outFile = new File(modelFilePath);
//            FileOutputStream outFileStream = new FileOutputStream(outFile);
//            model.serialize(outFileStream);
//        }
//        catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
    }
}
