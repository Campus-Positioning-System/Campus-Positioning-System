package com.example.campus_positioning_system;

import com.opencsv.CSVWriter;
// Csv Writer http://opencsv.sourceforge.net/

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataExport {

    // This Class is mainly inspired from the Tutorial at :https://mkyong.com/java/how-to-export-data-to-csv-file-java/

    // Purpose: Write the acquired Data into a CSV File

    public static int addDataToFile(List<String[]> csvData) {
        String[] header = {"SSID", "BSS", "RSSI", "Direction"};


        return 0;
    }

    public static void createFile(String fileName) {
        List<String[]> csvData = null;

        // default all fields are enclosed in double quotes
        // default separator is a comma
        try (CSVWriter writer = new CSVWriter(new FileWriter("c:\\test\\test.csv"))) {
            writer.writeAll(csvData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
