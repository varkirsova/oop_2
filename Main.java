package oop_2;

import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите путь до файла (csv/xml) или 0 для выхода:");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                System.out.println("Программа завершена.");
                break;
            }

            Path filePath;
            try {
                filePath = Path.of(input);
            } catch (java.nio.file.InvalidPathException e) {
                System.out.println("Некорректный путь к файлу.");
                continue;
            }

            if (!Files.exists(filePath)) {
                System.out.println("Файл не найден. Попробуйте снова.");
                continue;
            }

            long startTime = System.currentTimeMillis();

            try {
                if (input.toLowerCase().endsWith(".xml")) {
                    XmlProcessor.process(filePath); // передаём Path
                } else if (input.toLowerCase().endsWith(".csv")) {
                    CsvProcessor.process(filePath); // передаём Path
                } else {
                    System.out.println("Неподдерживаемый формат файла.");
                    continue;
                }
            } catch (IOException e) {
                System.out.println("Ошибка чтения файла.");
            } catch (XMLStreamException e) {
                System.out.println("Ошибка при разборе XML-файла.");
            } catch (NumberFormatException e) {
                System.out.println("Ошибка формата данных: ожидалось число этажа.");
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Время обработки: " + (endTime - startTime) + " мс\n");
        }

        scanner.close();
    }
}
