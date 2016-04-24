import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

/**
 * The "Reporting1" program generates all your arrays for sorting, runs each of the sorting methods as many times as 
 * specified in the assignment, giving them the arrays as arguments, records the execution time of these methods, prints
 * out the results in a form suitable for inclusion into your report and/or writes out the results into a file if you need to 
 * post-process your results (e.g., by Excel to produce graphs for your report). If you do not need this output file for
 * your report, you do not have to write it.
 * 
 * @author Adam Beck
 *
 */
public class Reporting1 {

    /**
     * Default constructor
     */
    public Reporting1(){   
    }
    
    /**
     * Runs all tests the sorting algorithms as defined by the project instructions.
     */
    public void run(){
        /* The first array contains HS, QS, or MS. Each contains an array
         * of size 4, representing the 4 different sized trials. Each of those
         * contains an array of size 1, representing the median of three trials
         * on that size.
         * 
         * Key:
         * 
         * first []
         * 0 = HS
         * 1 = QS
         * 2 = MS
         * 
         * second []
         * 0 = size 1000
         * 1 = size 10000
         * 2 = size 100000
         * 3 = size 1000000
         * 
         * third[]
         * The median of three trials (or 10 for random array testing)
         * */
        int[][][] sortedArrayResults = new int[3][4][1];
        int[][][] reverseArrayResults = new int[3][4][1];
        
        /* The first array contains HS, QS, or MS. Each contains an array
         * of size 4, representing the 4 different sized trials. Each of those
         * contains an array of size 10 representing every trial. Further analysis
         * of each trial will be done later, so for now that's why I'm saving them
         * in the sized 10 array.*/
        long[][][] randomArrayResults = new long[3][4][10];
        
        /* Get sorted results for every size of test arrays */
        for (int i = 1000; i <= 1000000; i*=10){
            getResults(sortedArrayResults, i, 0, 0);
            getResults(sortedArrayResults, i, 1, 0);
            getResults(sortedArrayResults, i, 2, 0);
        }

        /* Get reverse results for every size of test arrays */
        for (int i = 1000; i <= 1000000; i*=10){
            getResults(reverseArrayResults, i, 0, 1);
            getResults(reverseArrayResults, i, 1, 1);
            getResults(reverseArrayResults, i, 2, 1);
        }
        
        /* Do a separate method for obtaining random array results, since the testing
         * deviates a lot from the automated process of the getResults() method
         */
        getRandomResults(randomArrayResults);
        
        /* A helper method for all the code to print results */
        printResults(sortedArrayResults, reverseArrayResults, randomArrayResults);
    }

    /**
     * Fills the passed result array with result data based on required tests.
     * This is only for the sorted and reversed arrays.
     * 
     * The parameters make these trials as automated as possible with as little
     * manual testing as possible.
     * 
     * @param resultArray The result array to fill with data
     * @param size The size of the trial
     * @param typeOfSort 0 = HS, 1 = QS, 2 = MS
     * @param typeOfResults 0 = sorted array, 1 = reverse array, 2 = random array
     */
    public void getResults(int[][][] resultArray, int size, int typeOfSort, int typeOfResults){
        
        /* Create an array of size 'size', and fill it */
        int[] testArray = new int[size];
       
        /* Create sorted array */
        if (typeOfResults == 0){
            for (int i = 0; i < size; i++){
                testArray[i] = i;
            }
        }
        /* Create a reverse sorted array */
        else if (typeOfResults == 1){
            for (int i = size-1; i >= 0; i--){
                testArray[size-1-i] = i;
            }
        }

        long trial1 = 0, trial2 = 0, trial3 = 0;
        
        /* Heapsort trials */
        if (typeOfSort == 0){
            trial1 = Sorting.heapSort(testArray);
            trial2 = Sorting.heapSort(testArray);
            trial3 = Sorting.heapSort(testArray);
        }   
        /* Quicksort trials */
        else if (typeOfSort == 1){
            trial1 = Sorting.quickSort(testArray);
            trial2 = Sorting.quickSort(testArray);
            trial3 = Sorting.quickSort(testArray);
        } 
       /* Mergesort trials */
        else if (typeOfSort == 2){
            trial1 = Sorting.mergeSort(testArray);
            trial2 = Sorting.mergeSort(testArray);
            trial3 = Sorting.mergeSort(testArray);
        }
        /* Assume median is the third trial. If a trial is tied with this trial for
         * the median, trial3 will override and be the median */
        long median = trial3;
        
        /* Update median if the other trials happen to be the median */
        if ((trial1 > trial2 && trial1 < trial3) || (trial1 > trial3 && trial1 < trial2)){
            median = trial1;
        }
        else if ((trial2 > trial1 && trial2 < trial3) || (trial2 > trial3 && trial2 < trial1 )){
            median = trial2;
        }
        /* For this final case, it doesn't matter what the median is. 
         * I just made it trial1 every time*/
        else if (trial1 == trial2){
            median = trial1;
        }
        
        //TODO: Find a way to do this, with a single modulo statement
        /* The position in the resulting array to place the median value. */
        int position = 0;
        if (size == 1000){
            position = 0;
        }
        else if (size == 10000){
            position = 1;
        }
        else if (size == 100000){
            position = 2;
        }
        else if (size == 1000000){
            position = 3;
        }
            
        /* Update the resultArray for median in the appropriate spot */
        resultArray[typeOfSort][position][0] = (int)median;    
    }
    
    /**
     * Fills the passed result array with result data based on required tests.
     * This is a separate method for calculating results only for the random array testing.
     * 
     * Since the random array requires additional testing on variances, etc., The result array
     * will have the same format as the getResults() method, but instead of the median for the 
     * third dimensional array, it will store all ten tests.
     * 
     * @param resultArray The result array to fill with data
     */
    public void getRandomResults(long[][][] resultArray){
        /* First, create a random array of size 1000 */
        int[] arr1 = new int[1000];
        
        /* Randomly fill these values */
        randomFill(arr1);
        
        /* Test 10 times, each time repeat the random filling */
        for (int i = 0; i < 10; i++){
            resultArray[0][0][i] = Sorting.heapSort(arr1); 
            resultArray[1][0][i] = Sorting.quickSort(arr1);
            resultArray[2][0][i] = Sorting.mergeSort(arr1);
            randomFill(arr1); // This is crucial.  A new random filling will be used for every test.
        }
        
        //TODO: All the below processes more efficiently, with possibly only one or two loops.
        /* Repeat the above processes for arrays of size 10000, 100000, and 1000000
         * No comments will be needed since this process is all explained above */
        int[] arr2 = new int[10000];
        randomFill(arr2);
        for (int i = 0; i < 10; i++){
            resultArray[0][1][i] = Sorting.heapSort(arr2); 
            resultArray[1][1][i] = Sorting.quickSort(arr2);
            resultArray[2][1][i] = Sorting.mergeSort(arr2);
            randomFill(arr2);
        }
        
        int[] arr3 = new int[100000];
        randomFill(arr3);
        for (int i = 0; i < 10; i++){
            resultArray[0][2][i] = Sorting.heapSort(arr3); 
            resultArray[1][2][i] = Sorting.quickSort(arr3);
            resultArray[2][2][i] = Sorting.mergeSort(arr3);
            randomFill(arr3);
        }
        
        int[] arr4 = new int[1000000];
        randomFill(arr4);
        for (int i = 0; i < 10; i++){
            resultArray[0][3][i] = Sorting.heapSort(arr4); 
            resultArray[1][3][i] = Sorting.quickSort(arr4);
            resultArray[2][3][i] = Sorting.mergeSort(arr4);
            randomFill(arr4);
        }  
    }
    
    /**
     * Helper method to randomly fill a passed array with random values.
     * Since the range of values was not specified in the assignment, I am filling
     * the array with values of any integer, defined by nextInt().
     * @param arr
     */
    public void randomFill(int[] arr){
        Random rand = new Random();
        
        for (int i = 0; i < arr.length; i++){
            arr[i] = rand.nextInt();
        }   
    }
    
    /**
     * Helper method to print all the results in the arrays containing result information.
     * 
     * @param sortedArrayResults An array containing result information for sorted testing
     * @param reverseArrayResults An array containing result information for reverse testing
     * @param randomArrayResults An array containing result information for random testing
     */
    public void printResults(int[][][] sortedArrayResults, int[][][] reverseArrayResults, long[][][] randomArrayResults) {
        System.out.println("The following is in nanoseconds\n");
        
        System.out.println(" HS Sorted array size 1000:" + sortedArrayResults[0][0][0]);
        System.out.println(" HS Sorted array size 10000:" + sortedArrayResults[0][1][0]);
        System.out.println(" HS Sorted array size 100000:" + sortedArrayResults[0][2][0]);
        System.out.println(" HS Sorted array size 100000:" + sortedArrayResults[0][3][0]);
        
        System.out.println();
        
        System.out.println(" QS Sorted array size 1000:" + sortedArrayResults[1][0][0]);
        System.out.println(" QS Sorted array size 10000:" + sortedArrayResults[1][1][0]);
        System.out.println(" QS Sorted array size 100000:" + sortedArrayResults[1][2][0]);
        System.out.println(" QS Sorted array size 100000:" + sortedArrayResults[1][3][0]);
        
        System.out.println();
        
        System.out.println(" MS Sorted array size 1000:" + sortedArrayResults[2][0][0]);
        System.out.println(" MS Sorted array size 10000:" + sortedArrayResults[2][1][0]);
        System.out.println(" MS Sorted array size 100000:" + sortedArrayResults[2][2][0]);
        System.out.println(" MS Sorted array size 100000:" + sortedArrayResults[2][3][0]);
        
        System.out.println();
        
        System.out.println(" HS Reversed array size 1000:" + reverseArrayResults[0][0][0]);
        System.out.println(" HS Reversed array size 10000:" + reverseArrayResults[0][1][0]);
        System.out.println(" HS Reversed array size 100000:" + reverseArrayResults[0][2][0]);
        System.out.println(" HS Reversed array size 100000:" + reverseArrayResults[0][3][0]);
        
        System.out.println();
        
        System.out.println(" QS Reversed array size 1000:" + reverseArrayResults[1][0][0]);
        System.out.println(" QS Reversed array size 10000:" + reverseArrayResults[1][1][0]);
        System.out.println(" QS Reversed array size 100000:" + reverseArrayResults[1][2][0]);
        System.out.println(" QS Reversed array size 100000:" + reverseArrayResults[1][3][0]);
        
        System.out.println();
        
        System.out.println(" MS Reversed array size 1000:" + reverseArrayResults[2][0][0]);
        System.out.println(" MS Reversed array size 10000:" + reverseArrayResults[2][1][0]);
        System.out.println(" MS Reversed array size 100000:" + reverseArrayResults[2][2][0]);
        System.out.println(" MS Reversed array size 100000:" + reverseArrayResults[2][3][0]);
        
        System.out.println();
        
        /* All data for the ten trials on the 3 types of sorts */
        System.out.println("These are all the 10 trials for the random array testing");
        System.out.println();
        
        System.out.println(" HS Random array size 1000:");
        for (int i = 0; i < 10; i++){
            System.out.println(randomArrayResults[0][0][i]);
        }
        System.out.println(" QS Random array size 1000:");
        for (int i = 0; i < 10; i++){
            System.out.println(randomArrayResults[1][0][i]);
        }
        System.out.println(" MS Random array size 1000:");
        for (int i = 0; i < 10; i++){
            System.out.println(randomArrayResults[2][0][i]);
        }
        
        System.out.println(" HS Random array size 10000:");
        for (int i = 0; i < 10; i++){
            System.out.println(randomArrayResults[0][1][i]);
        }
        System.out.println(" QS Random array size 10000:");
        for (int i = 0; i < 10; i++){
            System.out.println(randomArrayResults[1][1][i]);
        }
        System.out.println(" MS Random array size 10000:");
        for (int i = 0; i < 10; i++){
            System.out.println(randomArrayResults[2][1][i]);
        }
        
        System.out.println(" HS Random array size 100000:");
        for (int i = 0; i < 10; i++){
            System.out.println(randomArrayResults[0][2][i]);
        }
        System.out.println(" QS Random array size 100000:");
        for (int i = 0; i < 10; i++){
            System.out.println(randomArrayResults[1][2][i]);
        }
        System.out.println(" MS Random array size 100000:");
        for (int i = 0; i < 10; i++){
            System.out.println(randomArrayResults[2][2][i]);
        }
        
        System.out.println(" HS Random array size 1000000:");
        for (int i = 0; i < 10; i++){
            System.out.println(randomArrayResults[0][3][i]);
        }
        System.out.println(" QS Random array size 1000000:");
        for (int i = 0; i < 10; i++){
            System.out.println(randomArrayResults[1][3][i]);
        }
        System.out.println(" MS Random array size 1000000:");
        for (int i = 0; i < 10; i++){
            System.out.println(randomArrayResults[2][3][i]);
        }
        
        /* Methods for calculating variance with the randomArrayResults[][][] */
        /* 3 types of sorting, 4 sizes, 1 result of the mean value */
        double[][][] meanVals = new double[3][4][1];
        
        /* Gets the mean values for all three types of sorts for all sizes */
        for (int i = 0; i < 3; i++){
            meanVals[i][0][0] = meanVal(randomArrayResults[i][0]);
            meanVals[i][1][0] = meanVal(randomArrayResults[i][1]);
            meanVals[i][2][0] = meanVal(randomArrayResults[i][2]);
            meanVals[i][3][0] = meanVal(randomArrayResults[i][3]);
        }
        
        /* 3 types of sorting, 4 sizes, 1 value for the variance of that test
         * for the current sorting and size */
        double[][][] varianceVals = new double[3][4][1];
        
        /* Populate the variance values for all three types of sorts and all sizes */
        for (int i = 0; i < 3; i++){
            varianceVals[i][0][0] = varianceVal(randomArrayResults[i][0], meanVals[i][0][0]);
            varianceVals[i][1][0] = varianceVal(randomArrayResults[i][1], meanVals[i][1][0]);
            varianceVals[i][2][0] = varianceVal(randomArrayResults[i][2], meanVals[i][2][0]);
            varianceVals[i][3][0] = varianceVal(randomArrayResults[i][3], meanVals[i][3][0]);
        }
        
        
        /* Print variance data for all three types of sorts */
        for (int i = 0; i < 3; i++){
            System.out.println();
            
            if (i == 0){
                System.out.println("Heapsort data:");
            }
            else if (i == 1){
                System.out.println("Quicksort data");
            }
            else{
                System.out.println("Mergesort data");
            }
            
            for (int a = 0, size = 1000; a < 4; a++, size *= 10){
                System.out.println(" random size " + size + " variance " + varianceVals[i][a][0]);
                System.out.println(" random size " + size + " mean " + meanVals[i][a][0]);
            }
            
            System.out.println();
        }
        
    }
    
    /**
     * Returns the mean value in a passed array.
     * 
     * @param arr The array to find a mean value from
     * @return The mean value in the passes array
     */
    public double meanVal(long[] arr){
        double sum = 0;
        
        for (int i = 0; i < arr.length; i++){
            sum += arr[i];
        }
        
        return sum/10; // magic number 10, because we always have 10 values.
    }
    
    /**
     * Returns the variance in an array.
     * 
     * @param arr The array passed in to find the variance of its elements
     * @param mean The mean value of the array
     * @return
     */
    public double varianceVal(long[] arr, double mean){
        double sum = 0;
        
        /* adds up the summation (Xi -Xmean)^2 */
        for (int i = 0; i < arr.length; i++){
            sum += Math.pow((double)arr[i]-mean, 2);
        }
        
        /* divides by n-1 to calculate variance */
        return (sum / arr.length-1);
    }
    /**
     * Main method to run the testing.
     * @param args
     */
    public static void main(String args[]){
        System.out.println("All console output is saved to ConsoleOutput.txt");
        
        /* Convert console output to the print steam and write all output to a file */
        try{
            File file = new File("ConsoleOutput.txt");
            FileOutputStream fos = new FileOutputStream(file);
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);
        } catch(Exception e){
            System.out.println("Error saving console output to output file");
            System.out.println(e);
        }
        
        Reporting1 testing = new Reporting1();
        testing.run();
    }

}
