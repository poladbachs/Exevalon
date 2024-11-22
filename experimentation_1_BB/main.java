public class main {
    public static void main(String[] args) {
        final Integer[] items = { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5 };
        final Sorter<Integer> sorter = new QuickSortGPT<>();
        sorter.sort(items);
        for (final Integer item : items) {
            System.out.println(item);
        }
    }
}
