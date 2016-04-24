import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This is the second file for testing.
 * 
 * @author Adam Beck
 *
 */
public class Reporting2 {
    
    /**
     * The main method
     * 
     * @param args Only one argument, the filename of the input file
     * @throws IOException 
     */
    public static void main(String args[]) throws IOException{
        if (args.length != 1){
            System.out.println("Error, args.length must be 1.");
        }
        
        else{
            File input = new File(args[0]);
            
            /* A StringBuilder for the output */
            StringBuilder outString = new StringBuilder();
           
            outString.append(heapSortData(input));
            outString.append(quickSortData(input));
            outString.append(mergeSortData(input));
            
            System.out.println(outString.toString());      
        }
    }
    
    /**
     * Scans the input file, and converts it to an int[]
     * 
     * @param file The file to scan
     * @return An int[] built from the contents of 'file'
     * @throws FileNotFoundException 
     */
    public static int[] scanFile(File file) throws FileNotFoundException{
        
        /* Build a list of the file contents */
        ArrayList<Integer> list = new ArrayList<Integer>();
        Scanner scanner = new Scanner(file);
        
        while (scanner.hasNext()){
            list.add(Integer.parseInt(scanner.nextLine()));
        }
        scanner.close();
        
        /* Convert the list into an array then return it */
        int[] returnArray = new int[list.size()];
        
        for (int i = 0; i < list.size(); i++){
            returnArray[i] = list.get(i);
        }
        
        return returnArray;
        
    }
    
    /**
     * Returns a string for data on the heapsort. Also writes to a file the data.
     * 
     * @param file The file to scan and build the array from
     * @return A string with the testing data
     * @throws IOException
     */
    public static String heapSortData(File file) throws IOException{
        
        /* Sort a copy, not the real array so we don't modify the real array */
        int[] copy = scanFile(file);
        /* Stores the three trials of heapsort */
        long[] data = new long[3];
        
        /* Write the sorted array to a file */
        BufferedWriter b = new BufferedWriter(new FileWriter("HSakb93.txt"));
        
        /* Magic number 3 because we always have 3 tests */
        for (int i = 0; i < 3; i++){
            data[i] = Sorting.heapSort(copy);
            
            if (i == 0){
                b.write(Arrays.toString(copy));
            }
            copy = scanFile(file);
        }
        
        b.close();
        
        /* Console output to return */
        long median = getMedian(data);
        return "HS akb93: " + median + "ns; ";
    }

    /**
     * Returns a string for data on the quicksort. Also writes to a file the data.
     * 
     * @return A string with the testing data
     * @throws IOException
     */
    public static String quickSortData(File file) throws IOException{
        
        /* Sort a copy, not the real array so we don't modify the real array */
        int[] copy = scanFile(file);
        /* Stores the three trials of quicksort */
        long[] data = new long[3];
        
        /* Write the sorted array to a file */
        BufferedWriter b = new BufferedWriter(new FileWriter("QSakb93.txt"));
        
        /* Magic number 3 because we always have 3 tests */
        for (int i = 0; i < 3; i++){
            data[i] = Sorting.quickSort(copy);
            
            if (i == 0){
                b.write(Arrays.toString(copy));
            }
            copy = scanFile(file);
        }
        
        b.close();
        
        /* Console output to return */
        long median = getMedian(data);
        return "QS akb93: " + median + "ns; ";
    }
    
    
    /**
     * Returns a string for data on the quicksort. Also writes to a file the data.
     * 
     * @return A string with the testing data
     * @throws IOException
     */
    public static String mergeSortData(File file) throws IOException{
        
        /* Sort a copy, not the real array so we don't modify the real array */
        int[] copy = scanFile(file);
        /* Stores the three trials of mergesort */
        long[] data = new long[3];
        
        /* Write the sorted array to a file */
        BufferedWriter b = new BufferedWriter(new FileWriter("MSakb93.txt"));
        
        /* Magic number 3 because we always have 3 tests */
        for (int i = 0; i < 3; i++){
            data[i] = Sorting.mergeSort(copy);
            
            if (i == 0){
                b.write(Arrays.toString(copy));
            }
            copy = scanFile(file);
        }
        
        b.close();
        
        /* Console output to return */
        long median = getMedian(data);
        return "MS akb93: " + median + "ns; ";
    }
    
    /**
     * Gets the median element of an array
     * 
     * @param data The array to get the median element from
     * @return The median element from 'data'
     */
    public static long getMedian(long[] data){
        Arrays.sort(data);
        return data[data.length/2];
    }
}
