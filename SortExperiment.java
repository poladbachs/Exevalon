import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SortExperiment {

    private static final int[] SIZES = {100, 1000, 10000}; // Data sizes
    private static final int REPEATS = 10; // Number of repeats for each config
    private static final String DIRECTORY_NAME = "data_csv"; // CSV directory

    public static void main(String[] args) {
        // Create CSV directory
        createDirectory();

        Sorter<Integer>[] algorithms = new Sorter[]{
                new BubbleSortUntilNoChange<>(),
                new BubbleSortWhileNeeded<>(),
                new QuickSortGPT<>(),
                new SelectionSortGPT<>()
        };

        String[] dataTypes = {"Random", "Sorted", "ReverseSorted", "PartiallySorted", "DuplicateValues"};

        // Run experiments
        for (Sorter<Integer> algorithm : algorithms) {
            for (int size : SIZES) {
                for (String dataType : dataTypes) {
                    runExperiment(algorithm, size, dataType);
                }
            }
        }
    }

    private static void runExperiment(Sorter<Integer> algorithm, int size, String dataType) {
        double totalTime = 0;

        for (int i = 0; i < REPEATS; i++) {
            Integer[] array = generateArray(size, dataType);

            long startTime = System.nanoTime();
            algorithm.sort(array);
            long endTime = System.nanoTime();

            // Convert execution time to seconds
            double execTimeInMilliSeconds = (endTime - startTime) / 1_000_000.0; // Convert to seconds
            totalTime += execTimeInMilliSeconds;

            // Save results to CSV in seconds
            saveResultsToCSV(algorithm.getClass().getSimpleName(), size, dataType, execTimeInMilliSeconds, i + 1);
        }

        System.out.printf("Algorithm: %s, Size: %d, Data Type: %s, Avg Time: %.9f ms%n",
                algorithm.getClass().getSimpleName(), size, dataType, totalTime / REPEATS);
    }

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
                // Randomly swap 10% of the array
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

    private static void createDirectory() {
        File directory = new File(DIRECTORY_NAME);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
