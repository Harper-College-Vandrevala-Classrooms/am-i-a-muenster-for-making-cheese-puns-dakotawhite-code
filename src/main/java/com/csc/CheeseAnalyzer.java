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
        String noheaderscsv = "cheese_without_headers.csv";
        String noidscsv = "cheese_without_ids.csv";
        double totalmoisture = 0.0;
        int moisturecount = 0;



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
            totalmoisture += moistureperc;
            moisturecount++; 
        } 
        catch (NumberFormatException e) {
     }
    }
    typecount.put(type, typecount.getOrDefault(type, 0) + 1);
                } 
        else {
            System.out.println("Insufficient columns in line: " + line);
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

        try (BufferedReader br = new BufferedReader(new FileReader(csv));
        BufferedWriter bw = new BufferedWriter(new FileWriter(noidscsv))) {
        String line;
        br.readLine();
        while ((line = br.readLine()) != null) {
        String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            if (columns.length > 0) {
                String newLine = String.join(",", java.util.Arrays.copyOfRange(columns, 1, columns.length));
                bw.write(newLine);
                bw.newLine();
            }
        }
    } catch (IOException e) {
    e.printStackTrace();
}
        try (BufferedReader br = new BufferedReader(new FileReader(csv));
            BufferedWriter bw = new BufferedWriter(new FileWriter(noheaderscsv))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
            bw.write(line);
            bw.newLine();
        }
            } catch (IOException e) {
            e.printStackTrace();
        }
        
        double averageMoisture = moisturecount > 0 ? totalmoisture / moisturecount : 0.0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            writer.write("Number of cheeses that use pasteurized milk: " + pasteurized + "\n");
            writer.write("Number of cheeses that use raw milk: " + raw + "\n");
            writer.write("Number of organic cheeses that have a moisture percentage greater than 41.0%: " + organicmoisture + "\n");
            writer.write("Most of the cheeses in Canada come from: " + (mostcommon != null ? mostcommon : "N/A") + " milk\n");
            writer.write("Average moisture percentage of all cheeses: " + String.format("%.2f", averageMoisture) + "%\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Cheese results have been written to output.txt.");
        System.out.println("Cheese data without headers has been written to cheese_without_headers.csv.");
        System.out.println("Cheese data without IDs has been written to cheese_without_ids.csv.");
    }
}