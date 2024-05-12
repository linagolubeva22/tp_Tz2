import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/** Класс для тестирования основных функций {@link Main} */
class MainTest {

  @TempDir Path tempDir;

  private Path testFile;

  private final List<Integer> normalData = List.of(1, 2, 3, 4);
  private final List<Integer> singleElementData = List.of(4);
  private final List<Integer> emptyData = new ArrayList<>();

  private final Random random = new Random();

  @BeforeEach
  void setUp() throws IOException {
    testFile = tempDir.resolve("testFile.txt");
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(testFile))) {
      out.println("1 4 2 3");
    }
  }

  /**
   * Функция для тестирования считывания данных их файла, когда там есть данные {@link
   * Main#readNumbersFromFile(String filename)}
   */
  @Test
  void testReadNumbersFromFile() throws IOException {
    List<Integer> numbers = Main.readNumbersFromFile(testFile.toString());
    assertEquals(List.of(1, 4, 2, 3), numbers);
  }

  /**
   * Функция для тестирования считывания данных их файла, когда файл пустой {@link
   * Main#readNumbersFromFile(String filename)}
   */
  @Test
  void testReadNumbersFromFileEmpty() throws IOException {
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(testFile))) {
      out.println("");
    }
    List<Integer> numbers = Main.readNumbersFromFile(testFile.toString());
    assertTrue(numbers.isEmpty());
  }

  /**
   * Функция для тестирования считывания данных их файла, когда средли чисел есть буква {@link
   * Main#readNumbersFromFile(String filename)}
   */
  @Test
  void testReadNumbersFromFileWithLetter() throws IOException {
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(testFile))) {
      out.println("1 2 3 a");
    }
    assertThrows(NumberFormatException.class, () -> Main.readNumbersFromFile(testFile.toString()));
  }

  /**
   * Функция для тестирования считывания данных их файла, когда файла не существует {@link
   * Main#readNumbersFromFile(String filename)}
   */
  @Test
  void testReadNumbersFromFileNotExists() {
    assertThrows(IOException.class, () -> Main.readNumbersFromFile("notExists.txt"));
  }

  /**
   * Функция для тестирования поиска минимума, когда более одного числа в списке {@link
   * Main#_min(List)}
   */
  @Test
  void _minWithNormalData() {
    assertEquals(1, Main._min(normalData));
  }

  /**
   * Функция для тестирования поиска максимума, когда более одного числа в списке {@link
   * Main#_max(List)}
   */
  @Test
  void _maxWithNormalData() {
    assertEquals(4, Main._max(normalData));
  }

  /**
   * Функция для тестирования поиска суммы, когда более одного числа в списке {@link
   * Main#_sum(List)}
   */
  @Test
  void _sumWithNormalData() {
    assertEquals(10, Main._sum(normalData));
  }

  /**
   * Функция для тестирования поиска произведения, когда более одного числа в списке {@link
   * Main#_mult(List)}
   */
  @Test
  void _multWithNormalData() {
    assertEquals(24, Main._mult(normalData));
  }

  /** Функция для тестирования поиска минимума, когда одно числа в списке {@link Main#_min(List)} */
  @Test
  void _minSingleElementData() {
    assertEquals(4, Main._min(singleElementData));
  }

  /**
   * Функция для тестирования поиска максимума, когда одно числа в списке {@link Main#_max(List)}
   */
  @Test
  void _maxSingleElementData() {
    assertEquals(4, Main._max(singleElementData));
  }

  /** Функция для тестирования поиска суммы, когда одно числа в списке {@link Main#_sum(List)} */
  @Test
  void _sumSingleElementData() {
    assertEquals(4, Main._sum(singleElementData));
  }

  /**
   * Функция для тестирования поиска произведения, когда одно числа в списке {@link
   * Main#_mult(List)}
   */
  @Test
  void _multSingleElementData() {
    assertEquals(4, Main._mult(singleElementData));
  }

  /** Функция для тестирования поиска минимума, когда нет чисел в списке {@link Main#_min(List)} */
  @Test
  void _minEmptyData() {
    assertThrows(NoSuchElementException.class, () -> Main._min(emptyData));
  }

  /** Функция для тестирования поиска максимума, когда нет чисел в списке {@link Main#_max(List)} */
  @Test
  void _maxEmptyData() {
    assertThrows(NoSuchElementException.class, () -> Main._max(emptyData));
  }

  /** Функция для тестирования поиска суммы, когда нет чисел в списке {@link Main#_sum(List)} */
  @Test
  void _sumEmptyData() {
    assertThrows(NoSuchElementException.class, () -> Main._sum(emptyData));
  }

  /**
   * Функция для тестирования поиска произведения, когда нет чисел в списке {@link Main#_mult(List)}
   */
  @Test
  void _multEmptyData() {
    assertThrows(NoSuchElementException.class, () -> Main._mult(emptyData));
  }

  /** Функция для тестирования производительности поиска минимума {@link Main#_min(List)} */
  @Test
  void performanceTestForMin() {
    for (int size = 1000; size <= 100000000; size *= 10) {
      List<Integer> numbers = generateRandomList(size);
      long startTime = System.nanoTime();
      Main._min(numbers);
      long endTime = System.nanoTime();
      System.out.println(
          "Время выполнения _min для " + size + " элементов: " + (endTime - startTime) + " нс");
    }
  }

  /** Функция для тестирования производительности поиска максимума {@link Main#_max(List)} */
  @Test
  void performanceTestForMax() {
    for (int size = 1000; size <= 100000000; size *= 10) {
      List<Integer> numbers = generateRandomList(size);
      long startTime = System.nanoTime();
      Main._max(numbers);
      long endTime = System.nanoTime();
      System.out.println(
          "Время выполнения _max для " + size + " элементов: " + (endTime - startTime) + " нс");
    }
  }

  /** Функция для тестирования производительности поиска суммы {@link Main#_sum(List)} */
  @Test
  void performanceTestForSum() {
    for (int size = 1000; size <= 100000000; size *= 10) {
      List<Integer> numbers = generateRandomList(size);
      long startTime = System.nanoTime();
      Main._sum(numbers);
      long endTime = System.nanoTime();
      System.out.println(
          "Время выполнения _sum для " + size + " элементов: " + (endTime - startTime) + " нс");
    }
  }

  /** Функция для тестирования производительности поиска произведения {@link Main#_mult(List)} */
  @Test
  void performanceTestForMult() {
    for (int size = 1000; size <= 100000000; size *= 10) {
      List<Integer> numbers = generateRandomList(size);
      long startTime = System.nanoTime();
      Main._mult(numbers);
      long endTime = System.nanoTime();
      System.out.println(
          "Время выполнения _mult для " + size + " элементов: " + (endTime - startTime) + " нс");
    }
  }

  /** Функция для генерации случайного списка */
  private List<Integer> generateRandomList(int size) {
    List<Integer> list = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      list.add(random.nextInt(100) + 1); // Генерация случайных чисел от 1 до 100
    }
    return list;
  }
}
