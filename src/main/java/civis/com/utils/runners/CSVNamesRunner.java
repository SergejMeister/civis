package civis.com.utils.runners;

import civis.com.utils.csv.data.CSVNameData;
import civis.com.utils.csv.readers.CSVNamesReader;
import civis.com.utils.csv.writer.CsvNameWriter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Runner to read 2. csv names files, join the data and write in one new csv file.
 */
public class CSVNamesRunner {

    public static void main(String[] args) {

        String csvNamesMan =
                "/media/sergej/windows/data/Projekte/civis/utils/src/main/resources/civis/com/utils/runners/namesMan.csv";
        List<CSVNameData> listOfManNames = CSVNamesReader.read(csvNamesMan);

        String csvNamesWife =
                "/media/sergej/windows/data/Projekte/civis/utils/src/main/resources/civis/com/utils/runners/namesWife.csv";
        List<CSVNameData> listOfWifeNames = CSVNamesReader.read(csvNamesWife);

        List<CSVNameData> joinListData = join(listOfManNames, listOfWifeNames);
        System.out.println("List size: " + joinListData.size());
        String csvOutput =
                "/media/sergej/windows/data/Projekte/civis/utils/src/main/resources/civis/com/utils/runners/names.csv";
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
