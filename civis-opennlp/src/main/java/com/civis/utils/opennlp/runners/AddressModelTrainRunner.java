package com.civis.utils.opennlp.runners;


import com.civis.utils.opennlp.models.TrainConfigData;
import com.civis.utils.opennlp.models.TrainConfigDataBuilder;
import com.civis.utils.opennlp.models.address.AddressFinderMe;
import com.civis.utils.opennlp.utils.IOTrain;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Utility class to train a model.
 */
public class AddressModelTrainRunner {

    private final static Logger LOG = LoggerFactory.getLogger(AddressModelTrainRunner.class);

    public static void main(String[] args) {

        ObjectStream<NameSample> sampleStream = null;
        try {
            sampleStream = IOTrain.readData("address-train.txt");
            TrainConfigData trainConfigData =
                    new TrainConfigDataBuilder().setLanguageCode("de").setType("address").setSamples(sampleStream)
                            .putCutoffIntoTrainingParameters(
                                    "1").build();

            TokenNameFinderModel model = AddressFinderMe.initializeTrainModel(trainConfigData).train();
            IOTrain.writeData(model, "de-address.bin");
        } catch (Exception e) {
            LOG.error("Exception occurred in model build process", e);
        } finally {
            if (sampleStream != null) {
                try {
                    sampleStream.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage());
                }
            }
        }
    }
}
