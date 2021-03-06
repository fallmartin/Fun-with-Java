package listing_23_5;

/**
 * Sample Output:
 * List: 2 3 2 5 6 1 -2 3 14 12
 *
 * Sorting using merge sort...
 * List: -2 1 2 2 3 3 5 6 12 14
 *
 * @author martinfall
 */
public class Sort {

    public static void sort(int[] list, char algorithmInitial) {
        switch (algorithmInitial) {
            case 'S': // Selection sort
                System.out.println("\nSorting using selection sort...");
                selectionSort(list);
                break;
            case 'B': // Bubble sort
                System.out.println("\nSorting using bubble sort...");
                bubbleSort(list);
                break;
            case 'I': // Insertion sort
                System.out.println("\nSorting using insertion sort...");
                insertionSort(list);
                break;
            case 'M': // Merge sort
                System.out.println("\nSorting using merge sort...");
                mergeSort(list);
                break;
            case 'Q': // Quick sort
                System.out.println("\nSorting using quick sort...");
                mergeSort(list);
                break;
        }
    }

    /**
     * Selection sort.
     *
     * @param list
     */
    public static void selectionSort(int[] list) {
        for (int i = 0; i < list.length - 1; i++) {
            int currentMinIndex = i;
            for (int j = i + 1; j < list.length; j++) {
                if (list[currentMinIndex] > list[j]) {
                    currentMinIndex = j;
                }
            }

            if (currentMinIndex != i) {
                swap(currentMinIndex, i, list);
            }
        }
    }

    /**
     * Convenience helper method for swapping two values at indices i and j.
     *
     * @param i
     * @param j
     * @param list
     */
    public static void swap(int i, int j, int[] list) {
        int temp = list[j];
        list[j] = list[i];
        list[i] = temp;
    }

    /**
     * Improved bubble sort.
     *
     * @param list
     */
    public static void bubbleSort(int[] list) {
        boolean nextPassNeeded = true;
        for (int i = 0; i < list.length - 1 && nextPassNeeded; i++) {
            nextPassNeeded = false;
            for (int j = 0; j < list.length - i - 1; j++) {
                if (list[j] > list[j + 1]) {
                    swap(j, j + 1, list);
                    nextPassNeeded = true;
                }
            }
        }
    }

    /**
     *
     * @param list
     */
    public static void insertionSort(int[] list) {
        for (int i = 1; i < list.length; i++) {
            int currentElement = list[i];
            int k;
            for (k = i - 1; k >= 0 && currentElement < list[k]; k--) {
                list[k + 1] = list[k];
            }
            list[k + 1] = currentElement;
        }
    }

    /**
     *
     * @param list
     */
    public static void mergeSort(int[] list) {
        if (list.length > 1) {
            // Merge sort the first half
            int[] subList1 = new int[list.length / 2];
            System.arraycopy(list, 0, subList1, 0, list.length / 2);
            mergeSort(subList1);

            // MergeSort the second half
            int subList2Length = list.length - (list.length / 2);
            int[] subList2 = new int[subList2Length];
            System.arraycopy(list, list.length / 2, subList2, 0, subList2Length);
            mergeSort(subList2);

            // Merge subList1 and subList2 into list
            merge(subList1, subList2, list);
        }
    }

    /**
     * Merges two sorted sub lists into one sorted list.
     *
     * @param list1
     * @param list2
     * @param list
     */
    public static void merge(int[] list1, int[] list2, int[] list) {
        int indexList1 = 0;
        int indexList2 = 0;
        int indexList = 0;

        while (indexList1 < list1.length && indexList2 < list2.length) {
            if (list1[indexList1] < list2[indexList2]) {
                list[indexList++] = list1[indexList1++];
            } else {
                list[indexList++] = list2[indexList2++];
            }
        }

        while (indexList1 < list1.length) {
            list[indexList++] = list1[indexList1++];
        }

        while (indexList2 < list2.length) {
            list[indexList++] = list2[indexList2++];
        }
    }

    public static void quickSort(int[] list) {
        quickSort(list, 0, list.length - 1);
    }

    public static void quickSort(int[] list, int first, int last) {
        if (last > first) {
            int pivotIndex = partition(list, first, last);
            quickSort(list, 0, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, last);
        }
    }

    /**
     * Partition the array list[first..last].
     *
     * @param list
     * @param first
     * @param last
     * @return
     */
    public static int partition(int[] list, int first, int last) {
        int pivot = list[first]; // Choose the first element as the pivot
        int low = first + 1; // Index for forward search
        int high = last; // Index for backward search

        while (high > low) {
            // Search forward for the left
            while (low <= high && list[low] <= pivot) {
                low++;
            }

            // Search backward from the right
            while (low <= high && list[high] > pivot) {
                high--;
            }

            // Swap two elements in the list
            if (high > low) {
                int temp = list[high];
                list[high] = list[low];
                list[low] = temp;
            }
        }

        while (high > first && list[high] >= pivot) {
            high--;
        }

        // Swap pivot with list high
        if (pivot > list[high]) {
            list[first] = list[high];
            list[high] = pivot;
            return high;
        } else {
            return first;
        }
    }

    public static void printList(int[] list) {
        System.out.print("List: ");
        for (int i = 0; i < list.length; i++) {
            System.out.print(list[i] + " ");
        }
        System.out.println();
    }

    /**
     * Main method with driver code.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] list = {2, 3, 2, 5, 6, 1, -2, 3, 14, 12};
        printList(list);

        sort(list, 'Q');
        printList(list);
    }

}
