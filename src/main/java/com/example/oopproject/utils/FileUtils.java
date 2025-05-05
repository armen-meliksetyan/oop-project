package com.example.oopproject.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {

    public static void writeLines(List<String> lines, String filePath) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream(filePath));
            for (String line : lines) {
                outputStream.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file " + filePath);
            System.exit(0);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public static ArrayList<String> readLines(String filePath) {
        ArrayList<String> lines = new ArrayList<>();
        Scanner inputStream = null;

        try {
            inputStream = new Scanner(new FileInputStream(filePath));
            while (inputStream.hasNextLine()) {
                lines.add(inputStream.nextLine());
            }
        } catch (FileNotFoundException e) {
            return lines;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return lines;
    }
}
