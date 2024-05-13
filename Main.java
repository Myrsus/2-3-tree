import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Random rnd = new Random();
        TwoThreeTree tree = new TwoThreeTree();

        // Шаг 2: Генерация массива из 10000 случайных чисел
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            numbers.add(rnd.nextInt());
        }

        // Шаг 3: Добавление чисел в 2-3 дерево
        long totalInsertTime = 0;
        for (int number : numbers) {
            long start = System.nanoTime();
            tree.insert(number);
            long end = System.nanoTime();
            totalInsertTime += (end - start);
        }
        // Шаг 4: Поиск 100 случайных чисел в 2-3 дереве
        long totalSearchTime = 0;
        Set<Integer> searchNumbers = new HashSet<>();
        while (searchNumbers.size() < 100) {
            searchNumbers.add(numbers.get(rnd.nextInt(numbers.size())));
        }
        for (int number : searchNumbers) {
            long start = System.nanoTime();
            tree.find(number);
            long end = System.nanoTime();
            totalSearchTime += (end - start);
        }

        // Шаг 5: Удаление 1000 случайных чисел из 2-3 дерева
        long totalDeleteTime = 0;
        Set<Integer> deleteNumbers = new HashSet<>();
        while (deleteNumbers.size() < 1000) {
            deleteNumbers.add(numbers.get(rnd.nextInt(numbers.size())));
        }
        for (int number : deleteNumbers) {
            long start = System.nanoTime();
            tree.delete(number);
            long end = System.nanoTime();
            totalDeleteTime += (end - start);
        }

        // Шаг 6: Расчет средних времен операций
        double averageInsertTime = totalInsertTime / 10000.0;
        double averageSearchTime = totalSearchTime / 100.0;
        double averageDeleteTime = totalDeleteTime / 10000.0;

        System.out.println("Среднее время вставки: " + averageInsertTime + " наносекунд");
        System.out.println("Среднее время поиска: " + averageSearchTime + " наносекунд");
        System.out.println("Среднее время удаления: " + averageDeleteTime + " наносекунд");
    }
}