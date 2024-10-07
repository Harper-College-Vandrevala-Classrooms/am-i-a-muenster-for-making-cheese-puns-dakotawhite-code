package com.csc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class CheeseAnalyzer {

    public static void main(String[] args) {
        String csv = "cheese_data.csv";
        String output = "output.txt";
        int pasteurized = 0;
        int raw = 0;
        int organicmoisture = 0;
        Map<String, Integer> typecount = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] column = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (column.length > 9) {
                    String treatment = column[9].trim();
                    String organic = column[6].trim();
                    String moisture = column[3].trim();
                    String type = column[8].trim();
                    if ("Pasteurized".equals(treatment)) {
                        pasteurized++;
                    } else if ("Raw Milk".equals(treatment)) {
                        raw++;
                    }

        if("1".equals(organic)) {
          try{
            Double moistureperc = Double.parseDouble(moisture);
            if(moistureperc > 41.0){
                organicmoisture++;
            }
        } 
        catch (NumberFormatException e) {
     }
    }
    typecount.put(type, typecount.getOrDefault(type, 0) + 1);
                } 
        else {
            System.out.println("Insufficient columns in line: " + line); // Debugging output for insufficient columns
            }
        }
     } 
        catch (IOException e) {
            e.printStackTrace();
        }
        String mostcommon = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : typecount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostcommon = entry.getKey();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            writer.write("Number of cheeses that use pasteurized milk: " + pasteurized + "\n");
            writer.write("Number of cheeses that use raw milk: " + raw + "\n");
            writer.write("Number of organic cheeses that have a moisture percentage greater than 41.0%: " + organicmoisture + "\n");
            writer.write("Most of the cheeses in Canada come from: " + (mostcommon != null ? mostcommon : "N/A") + " milk\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Cheese results have been written to output.txt.");
    }
}