package org.Darwyi.logging;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Ваш вибір: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": generateDemoLog(sc); break;
                case "2": filterLog(sc);       break;
                case "0":
                    System.out.println("До побачення!");
                    running = false;
                    break;
                default:
                    System.out.println("Невідомий пункт меню");
            }
            System.out.println();
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("1. Згенерувати демо-файл логів");
        System.out.println("2. Відфільтрувати лог-файл");
        System.out.println("0. Вихід");
    }

    private static void generateDemoLog(Scanner sc) {
        System.out.print("Шлях до файлу (.log): ");
        String path = sc.nextLine().trim();
        System.out.print("Кількість рядків: ");
        int n;
        try {
            n = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Невірний формат числа");
            return;
        }
        if (n <= 0) {
            System.out.println("Кількість має бути додатньою");
            return;
        }

        long t0 = System.currentTimeMillis();
        try {
            LogGenerator.generate(path, n);
        } catch (IOException e) {
            System.err.println("Помилка генерації: " + e.getMessage());
            return;
        }
        long elapsed = System.currentTimeMillis() - t0;
        System.out.printf("Згенеровано %d рядків у '%s' за %d мс%n", n, path, elapsed);
    }

    private static void filterLog(Scanner sc) {
        System.out.print("Вхідний файл логів: ");
        String src = sc.nextLine().trim();
        System.out.print("Файл для результату: ");
        String dst = sc.nextLine().trim();

        System.out.println("Мінімальний рівень логування для збереження:");
        LogLevel[] levels = LogLevel.values();
        for (int i = 0; i < levels.length; i++) {
            System.out.printf("  %d) %-10s %s%n",
                i + 1, levels[i].getTag(), levels[i].getDisplayName());
        }
        System.out.print("Ваш вибір (1-" + levels.length + "): ");

        int idx;
        try {
            idx = Integer.parseInt(sc.nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Невірний формат числа");
            return;
        }
        if (idx < 0 || idx >= levels.length) {
            System.out.println("Значення поза діапазоном");
            return;
        }

        LogFilter filter = new SevereLogFilter();
        long t0 = System.currentTimeMillis();
        filter.filter(src, dst, levels[idx]);
        long elapsed = System.currentTimeMillis() - t0;
        System.out.printf("Час фільтрації: %d мс%n", elapsed);
    }
}
