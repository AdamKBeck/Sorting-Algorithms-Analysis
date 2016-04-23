import java.util.Random;

/**
 * This class contains the three sorting methods used in my
 * experimental sorting research.
 * 
 * @author Adam Beck
 *
 */
public class Sorting {
    
    
    /*--------------------------------------HEAPSORT----------------------------------*/
    
    /**
     * Sorts an input array in increasing order.
     * 
     * @param arr Input array to sort.
     * @return Execution time of the method
     */
    public synchronized static long heapSort(int[] arr){
        long startTime = System.nanoTime();
        
        int size = arr.length-1;
        
        buildHeap(arr, size);
        
        for (int i = size; i > 0; i--){
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            
            size--; // Important to keep track of size aftre every operation above.
            
            makeMaxHeap(arr, 0, size);
        }
        
         /* Test print, commented out for final version of this class */
//        for (int temp: arr){
//            System.out.println(temp + " ");
//        }
        
        return System.nanoTime() - startTime;
    }
    
    /**
     * Helper method for heapSort().
     * 
     * Running time: O(n)
     * @param arr The input array
     * @param size The current size to operate on.
     */
    public synchronized static void buildHeap(int[] arr, int size){
        for (int i = size; i >= 0; i--){
            makeMaxHeap(arr,i, size);
        }
    }
    
    /**
     * This method maintains the max-heap property of having 
     * a greater root than child. 
     * 
     * Running time: O(log(n)).
     * 
     * @param arr The input array to sort
     * @param index 
     * @param size The current size of the array to operate on
     */
    public synchronized  static void makeMaxHeap(int[] arr, int index, int size){
        /* Properties of a max heap's left and right */
        int left = 2 * index;
        int right = 2 * index + 1;
        int largest;
        
        /* Set the largest appropriately */
        if (left <= size && arr[left] > arr[index]){
            largest = left;
        }
        else{
            largest = index;
        }
        
        /* If the right is larger though, make this th enew largest */
        if (right <= size && arr[right] > arr[largest]){
            largest = right;
        }
        /* Swap elements in this case */
        if (largest != index){
            int temp = arr[largest];
            arr[largest] = arr[index];
            arr[index] = temp;
            
            /* Recursive call */
            makeMaxHeap(arr, largest, size);
        }   
    }
    
    
    /*--------------------------------------QUICKSORT----------------------------------*/
    
    /**
     * Sorts an input array in increasing order.
     * 
     * @param arr Input array to sort.
     * @return Execution time of the method
     */
    public synchronized static long quickSort(int[] arr) {
        
        if (arr.length == 1){
            return 0;
        }
        
        long startTime = System.nanoTime();
        
        /* Calls recursive method to execute the quick sort */
        recursiveQuickSort(arr, 0, arr.length - 1);
        
        /* Test print, commented out for final version of this class */
//        for (int temp: arr){
//            System.out.print(temp + " ");
//        }
        return System.nanoTime() - startTime;
    }
  
    /**
     * This is a helper method for the quickSort() method.
     *
     * @param arr The array to recursively sort
     * @param leftIndex In index at the start of the array to sort
     * @param rightIndex The index at the end of the array to sort
     */
    public synchronized static void recursiveQuickSort(int[] arr, int leftIndex, int rightIndex) {
        int index = partition(arr, leftIndex, rightIndex);
       
        /* On the left subarray, quicksort recursively */
        if (leftIndex < index - 1) {
            recursiveQuickSort(arr, leftIndex, index - 1);
        }

        /* On the right subarray, quicksort recursively */
        if (rightIndex > index) {
            recursiveQuickSort(arr, index, rightIndex);
        }
    }

    /**
     * 
     * By definition, split the array from pivot, with the left side taking elements less than 
     * the pivot while the right side contains element elements greater than the pivot.
     *
     * @param array The subarray to be partitioned.
     * @param left The leftmost bound of the subarray.
     * @param right The rightmost bound of the subarray.
     * @return The index of the partition.
     */
    public synchronized static int partition(int[] array, int left, int right) {
        
        /* As noted in the instructions, my way of choosing the pivot
         * is to take the median element of three randomly chosen indexes. */
        Random r = new Random();
        r.setSeed(System.nanoTime()); // This ensures a close to a random selection as possible of values. 
        
        /* Randomly choose three elements. The median element is how I'm choosing my pivot */
        int r1 = r.nextInt(array.length), r2 = r.nextInt(array.length), r3 = r.nextInt(array.length);
        
        /* Chooses the median element for the pivot
         * I originally did this, but it caused a lot of
         * stack overflow erros at low array sizes, so instead
         * I chose my pivot to be (left+right)/2 */
       // int pivot = ((array[r1]-array[r2])*(array[r3]-array[r1]) >= 0) ? array[r1] : ((array[r2]-array[r1])*(array[r3]-array[r2]) >= 0) ? array[r2] : array[r3];
        int pivot = array[(left+right)/2];
        
        while (left <= right) {
            /* Bottom up searching for a greater element than the pivot */
            while (array[left] < pivot) {
                left++;
            }
            /* Top down searching through the array for an element less than the pivot */
            while (array[right] > pivot) {
                right--;
            }

            /* Swap */
            if (left <= right) {
                int tmp = array[left];
                array[left] = array[right];
                array[right] = tmp;

                /* After swapped, remember to adjust indices */
                left++;
                right--;
            }
        }
        return left;
    }
    

    /*--------------------------------------MERGESORT----------------------------------*/
    
    /**
     * Sorts an input array in increasing order.
     * 
     * @param arr Input array to sort.
     * @return Execution time of the method
     */
    public synchronized static long mergeSort(int[] arr) {
        long start = System.nanoTime();
        
        int[] beginning, end;
        beginning= new int[arr.length];
        end = arr;

        /* merge */
        for (int chunk = 1; chunk < arr.length; chunk = chunk * 2) {
            for (int i = 0; i < arr.length; i += chunk * 2) {
                helperMethodMerge(end, beginning, i, chunk);
            }
            int[] temp = end;
            end = beginning;
            beginning = temp;
        }

        if (!(arr == end)) {
            for (int i = 0; i < arr.length; i++)
                arr[i] = end[i];
        }
        
        /* Test print, commented out for final version of this class */
//      for (int temp: arr){
//          System.out.print(temp + " ");
//      }
        
        return System.nanoTime() - start;
    }

    /**
     * This method merges all chunks from the start to end array
     */
    public synchronized static void helperMethodMerge(int[] end, int[] start, int index, int block) {
        int lower, middle, high;
        lower = index;
        middle = Math.min(end.length, index + block);
        high = Math.min(end.length, index + block * 2);

        int leftIndex = lower;
        int rightIndex = middle;

        for (int i = lower; i < high; i++) {
            /* For the first array */
            if (leftIndex == middle){
                start[i] = end[rightIndex++];
            }
            
            /* For the second array */
            else if (rightIndex == high){
                start[i] = end[leftIndex++];
            }
            
            else if (end[leftIndex] < end[rightIndex]){
                start[i] = end[leftIndex++];
            }
            
            else{
                start[i] = end[rightIndex++];
            }
        } 
    }
    
     
    public static void main(String args[]){
        /* Verifying if each sort is nlogn */
//        for(int a =5;a<100;a++){
//            int arr[] = new int[a];
//            for(int b = 0;b<a;b++)
//                arr[b] = (int)(Math.random()*10);
//            System.out.println("QS "+ (int)(quickSort(arr)/(a*Math.log(a)))+ " HS "+ (int)(heapSort(arr)/(a*Math.log(a))));
//        }
       
        /* Basic sorting to make sure the methods work */
        Random rand = new Random();
        int[] arr = new int[1000];
        for (int i = 0; i < arr.length; i++){
            arr[i] = rand.nextInt();
        }
//        
//        System.out.println("HS akb93 = " + heapSort(arr) + "ns");
//        System.out.println("QS akb93 = " + quickSort(arr) + "ns");
//        System.out.println("MS akb93 = " + mergeSort(arr) + "ns");
    }
}
