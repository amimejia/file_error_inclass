package guilford.edu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DataFileRead {
    public static void main(String[] args)
    // right click on main and click new folder
    // create resources folder
    // right click on resources folder and click new file
    // create data.txt file
    {
        Scanner scan = new Scanner(System.in);
        Scanner scanFile = null;
        Path dataLocation = null; // what's in our data file
        boolean validData = false; // did we get valid data from the file
        double[][] values = null; // where are we putting the data when we get it?
        String fileName = null;

        System.out.println("Enter the name of the file to read: ");
        // let's get a file name from the user and try to open the associate file
        fileName = scan.next();

        try {
            dataLocation = Paths.get(DataFileRead.class.getResource("/" + fileName).toURI());
            // What is unhandled exception type?
            // It's a checked exception that we are not handling

            // FileRead is a a strwam that reads a file one character (Unicode) at a time
            // It's meant for text files
            // If we had a binary file, we would use FileInputStream that reads a rile one
            // byte at a time
            FileReader dataFile = new FileReader(dataLocation.toString());
            BufferedReader dataBuffer = new BufferedReader(dataFile); // so that we are reading data efficiently
            scanFile = new Scanner(dataBuffer); // so that we can read data line by line
            values = readData(scanFile); // so that we can read data line by line
        } catch (URISyntaxException | FileNotFoundException |NullPointerException e) {
            // Todo aUTO-generated catch block
            e.printStackTrace();
        }

        try {
        writeData(values, "output.txt");
        } catch (IOException | URISyntaxException ex){
            ex.printStackTrace();
            System.exit(1);// 1 becayse it ended with an error
        }

    }

    public static double[][] readData(Scanner scan) {
        // returns a double array of the data in the file
        double[][] inputValues = null;
        // get the number of rows and columns from the first row of the file
        int rows = scan.nextInt();
        int cols = scan.nextInt();

        // instantiate the appropriate size array
        inputValues = new double[rows][cols];
        int i = 0;
        int j = 0;
        // try reading the data from the fille, catching any exception that take place
        try {
            for (i = 0; i < rows; i++) {
                for (j = 0; j < cols; j++) {
                    inputValues[i][j] = scan.nextDouble();
                }
            }
        } catch (InputMismatchException ex) {
            System.out.println(i + " " + j);
        } catch (NoSuchElementException ex) {
            System.out.println("Not enough data in the file");
        }
        return inputValues;
    }

    // Write values to a file
    public static void writeData(double[][] values, String location) throws URISyntaxException, IOException{
        //"throws" means "not our problem", it's the problem of whoever asked us to run this method
        //Get the path of the right folder
        Path locationPath = Paths.get(DataFileRead.class.getResource("/guilford/edu/").toURI());
        FileWriter fileLocation = new FileWriter (locationPath.toString() + "/" + location);
        BufferedWriter bufferWrite = new BufferedWriter(fileLocation);
        //Write the data to the file
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                bufferWrite.write(values[i][j] + " ");
            }
            bufferWrite.newLine();
        }
        bufferWrite.close();
    }

    public static class ScannerException extends Exception {
        public ScannerException(String message) {
            super(message);
        }
    }
}
