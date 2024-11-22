import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * This class conducts sorting experiments on various algorithms, array sizes, 
 * and data types, measuring the execution time and storing results in CSV files.
 */
public class SortExperiment {

    private static final int[] SIZES = {100, 1000, 10000};
    private static final int REPEATS = 10;
    private static final String DIRECTORY_NAME = "data_csv";

    /**
     * Main method to set up and run sorting experiments for each algorithm,
     * array size, and data type.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        createDirectory();

        Sorter<Integer>[] algorithms = new Sorter[]{
                new BubbleSortUntilNoChange<>(),
                new BubbleSortWhileNeeded<>(),
                new QuickSortGPT<>(),
                new SelectionSortGPT<>()
        };

        String[] dataTypes = {"Random", "Sorted", "ReverseSorted", "PartiallySorted", "DuplicateValues"};

        for (Sorter<Integer> algorithm : algorithms) {
            for (int size : SIZES) {
                for (String dataType : dataTypes) {
                    runExperiment(algorithm, size, dataType);
                }
            }
        }
    }

    /**
     * Runs a sorting experiment for a given algorithm, array size, and data type.
     * Measures the average execution time over a number of repetitions.
     *
     * @param algorithm The sorting algorithm to test.
     * @param size The size of the array to be sorted.
     * @param dataType The type of data in the array (e.g., "Random", "Sorted").
     */
    private static void runExperiment(Sorter<Integer> algorithm, int size, String dataType) {
        double totalTime = 0;

        for (int i = 0; i < REPEATS; i++) {
            Integer[] array = generateArray(size, dataType);

            long startTime = System.nanoTime();
            algorithm.sort(array);
            long endTime = System.nanoTime();

            double execTimeInMilliSeconds = (endTime - startTime) / 1_000_000.0;
            totalTime += execTimeInMilliSeconds;

            saveResultsToCSV(algorithm.getClass().getSimpleName(), size, dataType, execTimeInMilliSeconds, i + 1);
        }

        System.out.printf("Algorithm: %s, Size: %d, Data Type: %s, Avg Time: %.9f ms%n",
                algorithm.getClass().getSimpleName(), size, dataType, totalTime / REPEATS);
    }

    /**
     * Generates an array of integers of a given size and data type.
     *
     * @param size The size of the array to generate.
     * @param dataType The type of data distribution in the array (e.g., "Random", "Sorted").
     * @return The generated array of integers.
     */
    private static Integer[] generateArray(int size, String dataType) {
        Random random = new Random();
        Integer[] array = new Integer[size];

        switch (dataType) {
            case "Random":
                for (int i = 0; i < size; i++) {
                    array[i] = random.nextInt();
                }
                break;
            case "Sorted":
                for (int i = 0; i < size; i++) {
                    array[i] = i;
                }
                break;
            case "ReverseSorted":
                for (int i = 0; i < size; i++) {
                    array[i] = size - i;
                }
                break;
            case "PartiallySorted":
                for (int i = 0; i < size; i++) {
                    array[i] = i;
                }
                for (int i = 0; i < size / 10; i++) {
                    int index1 = random.nextInt(size);
                    int index2 = random.nextInt(size);
                    int temp = array[index1];
                    array[index1] = array[index2];
                    array[index2] = temp;
                }
                break;
            case "DuplicateValues":
                int value = random.nextInt();
                for (int i = 0; i < size; i++) {
                    array[i] = value;
                }
                break;
        }
        return array;
    }

    /**
     * Saves the results of a sorting experiment to a CSV file.
     *
     * @param algorithmName The name of the sorting algorithm.
     * @param size The size of the data set.
     * @param dataType The type of data in the array.
     * @param execTime The execution time in milliseconds.
     * @param index The current repetition index.
     */
    private static void saveResultsToCSV(String algorithmName, int size, String dataType, double execTime, int index) {
        try {
            File directory = new File(DIRECTORY_NAME);
            if (!directory.exists()) {
                directory.mkdir();
            }

            String fileName = String.format("%s/results.csv", DIRECTORY_NAME);
            File file = new File(fileName);
            boolean writeHeader = !file.exists();

            try (FileWriter writer = new FileWriter(file, true)) {
                if (writeHeader) {
                    writer.append("Index,SortingAlgo,SortingType,DataType,DataSize,ExeTime\n");
                }
                writer.append(String.format("%d,%s,%s,%s,%d,%.9f\n", index, algorithmName, "Ascending", dataType, size,
                        execTime));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a directory for storing CSV files if it does not already exist.
     */
    private static void createDirectory() {
        File directory = new File(DIRECTORY_NAME);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
