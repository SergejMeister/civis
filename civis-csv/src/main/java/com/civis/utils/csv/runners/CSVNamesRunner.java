package com.civis.utils.csv.runners;


import com.civis.utils.csv.names.CSVNameData;
import com.civis.utils.csv.names.CSVNamesReader;
import com.civis.utils.csv.names.CsvNameWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Runner to read 2. csv names files, join the data and write in one new csv file.
 */
public class CSVNamesRunner {

    private final static Logger LOG = LoggerFactory.getLogger(CSVNamesRunner.class);

    public static void main(String[] args) {
        //Root resource folder with names files.
        URL namesFolderUrl = Thread.currentThread().getContextClassLoader().getResource("names");

        //Read man names.
        String csvNamesMan = namesFolderUrl.getPath() + "/namesMan.csv";
        List<CSVNameData> listOfManNames = CSVNamesReader.read(csvNamesMan);

        //Read wife names.
        String csvNamesWife = namesFolderUrl.getPath() + "/namesWife.csv";
        List<CSVNameData> listOfWifeNames = CSVNamesReader.read(csvNamesWife);

        //Join names data.
        List<CSVNameData> joinListData = join(listOfManNames, listOfWifeNames);
        LOG.info("List size: " + joinListData.size());

        //Write into a new file.
        String csvOutput = namesFolderUrl.getPath() + "/names.csv";
        CsvNameWriter.write(csvOutput, joinListData);
    }

    private static List<CSVNameData> join(List<CSVNameData> list1, List<CSVNameData> list2) {
        List<CSVNameData> result = new ArrayList<>();
        Set<String> addedNames = new HashSet<>();

        //add all names in first list.
        for (CSVNameData csvNameData1 : list1) {
            CSVNameData nameEqual = findByName(list2, csvNameData1.getName());
            if (nameEqual == null) {
                result.add(csvNameData1);
            } else {
                //check Gender
                if (csvNameData1.getGender().equals(nameEqual.getGender())) {
                    result.add(csvNameData1);
                } else {
                    CSVNameData neutralNameData = new CSVNameData(csvNameData1.getName(), "N");
                    result.add(neutralNameData);
                }
                addedNames.add(csvNameData1.getName());
            }
        }

        //add names in second list.
        for (CSVNameData csvNameData2 : list2) {
            if (!addedNames.contains(csvNameData2.getName())) {
                result.add(csvNameData2);
            }
        }

        return result;
    }

    private static CSVNameData findByName(List<CSVNameData> list, String name) {
        for (CSVNameData csvNameData : list) {
            if (csvNameData.getName().equals(name)) {
                return csvNameData;
            }
        }
        return null;
    }
}
