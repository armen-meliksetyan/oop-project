package com.example.oopproject.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class for reading and writing lines to and from files.
 * <p>
 * This class provides methods to read data from a file and write data to a file.
 * It handles basic file I/O operations using `Scanner` for reading and `PrintWriter`
 * for writing, with appropriate exception handling.
 * </p>
 */
public class FileUtils {

    /**
     * Writes a list of strings to a file.
     * <p>
     * Each string from the list is written to a new line in the specified file.
     * If the file cannot be opened, the program will terminate.
     * </p>
     *
     * @param lines the list of strings to write to the file
     * @param filePath the path to the file where the lines should be written
     */
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

    /**
     * Reads all lines from a file and returns them as a list of strings.
     * <p>
     * If the file does not exist or cannot be opened, an empty list is returned.
     * </p>
     *
     * @param filePath the path to the file to be read
     * @return a list of strings, where each string represents a line in the file
     */
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
