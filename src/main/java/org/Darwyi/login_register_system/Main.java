package org.Darwyi.login_register_system;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        UserRegistry registry = new UserRegistry();
        Scanner scanner = new Scanner(System.in);

        if (askYesNo(scanner, "Відновити базу користувачів з файлу?")) {
            System.out.print("Шлях до файлу: ");
            String path = scanner.nextLine().trim();
            try {
                registry.loadFromFile(path);
                System.out.println("Базу відновлено. Користувачів: " + registry.size());
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Не вдалося завантажити: " + e.getMessage());
                fillDemoUsers(registry);
            }
        } else {
            fillDemoUsers(registry);
        }
        System.out.println();

        boolean running = true;
        int currentUserId = 0;

        while (running) {
            printMenu();
            System.out.print("Ваш вибір: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    System.out.print("Логін: ");
                    String regLogin = scanner.nextLine().trim();
                    System.out.print("Пароль: ");
                    String regPass = scanner.nextLine().trim();
                    registry.registerUser(regLogin, regPass);
                    break;
                case "2":
                    System.out.print("Логін: ");
                    String loginName = scanner.nextLine().trim();
                    System.out.print("Пароль: ");
                    String loginPass = scanner.nextLine().trim();
                    int newUser = registry.loginUser(loginName, loginPass);
                    if (newUser != 0) currentUserId = newUser;
                    break;
                case "3":
                    registry.logoutUser(currentUserId);
                    break;
                case "4":
                    System.out.print("Логін для перевірки: ");
                    registry.printRegisteredStatus(scanner.nextLine().trim());
                    break;
                case "5":
                    System.out.print("ID для видалення: ");
                    try {
                        registry.removeUser(Integer.parseInt(scanner.nextLine().trim()));
                    } catch (NumberFormatException e) {
                        System.out.println("Невірний формат ID");
                    }
                    break;
                case "6":
                    registry.printTotalUniqueUsers();
                    break;
                case "7":
                    registry.displayAllUsers();
                    break;
                case "8":
                    showUserList(registry);
                    break;
                case "9":
                    showOrdered(registry, scanner);
                    break;
                case "10":
                    showFiltered(registry, scanner);
                    break;
                case "11":
                    saveDb(registry, scanner);
                    break;
                case "12":
                    loadDb(registry, scanner);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Невідомий пункт меню");
            }
            System.out.println();
        }

        if (askYesNo(scanner, "Зберегти базу користувачів у файл перед виходом?")) {
            saveDb(registry, scanner);
        }
        System.out.println("До побачення!");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println(" 1.  Зареєструвати користувача");
        System.out.println(" 2.  Увійти в систему (login)");
        System.out.println(" 3.  Вийти з системи (logout)");
        System.out.println(" 4.  Перевірити реєстрацію");
        System.out.println(" 5.  Видалити користувача");
        System.out.println(" 6.  Кількість унікальних користувачів");
        System.out.println(" 7.  Показати всіх користувачів");
        System.out.println(" 8.  Перелік користувачів (getUserList)");
        System.out.println(" 9.  Сортований перелік (getInOrder)");
        System.out.println(" 10. Фільтрований перелік (getFiltered)");
        System.out.println(" 11. Зберегти базу у файл");
        System.out.println(" 12. Завантажити базу з файлу");
        System.out.println(" 0.  Вихід");
    }

    private static boolean askYesNo(Scanner sc, String question) {
        System.out.print(question + " (y/n): ");
        String resp = sc.nextLine().trim().toLowerCase();
        return resp.equals("y") || resp.equals("yes") || resp.equals("так") || resp.equals("т");
    }

    private static void fillDemoUsers(UserRegistry registry) {
        System.out.println("Початкове заповнення тестовими користувачами:");
        registry.registerUser("alice", "pass1234");
        registry.registerUser("john", "securePass");
        registry.registerUser("jane", "qwerty123");
        registry.loginUser("alice", "pass1234");
        registry.loginUser("john", "securePass");
    }

    private static void saveDb(UserRegistry registry, Scanner sc) {
        System.out.print("Шлях до файлу: ");
        String path = sc.nextLine().trim();
        try {
            registry.saveToFile(path);
            System.out.println("Збережено: " + registry.size() + " користувачів у " + path);
        } catch (IOException e) {
            System.err.println("Помилка збереження: " + e.getMessage());
        }
    }

    private static void loadDb(UserRegistry registry, Scanner sc) {
        System.out.print("Шлях до файлу: ");
        String path = sc.nextLine().trim();
        try {
            registry.loadFromFile(path);
            System.out.println("Завантажено: " + registry.size() + " користувачів з " + path);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Помилка завантаження: " + e.getMessage());
        }
    }

    private static void showUserList(UserRegistry registry) {
        LinkedList<User> list = registry.getUserList();
        if (list.isEmpty()) { System.out.println("Список порожній"); return; }
        System.out.println("Усі користувачі (getUserList):");
        for (User u : list) System.out.println("  " + u);
    }

    private static void showOrdered(UserRegistry registry, Scanner scanner) {
        System.out.println("Оберіть порядок сортування:");
        System.out.println("  1) За id (зростання)");
        System.out.println("  2) За іменем (алфавітний)");
        System.out.println("  3) За датою останнього входу (спадання)");
        System.out.print("Ваш вибір: ");
        String choice = scanner.nextLine().trim();

        Comparator<User> cmp;
        switch (choice) {
            case "1": cmp = (a, b) -> Integer.compare(a.getId(), b.getId()); break;
            case "2": cmp = (a, b) -> a.getName().compareToIgnoreCase(b.getName()); break;
            case "3":
                cmp = (a, b) -> {
                    LocalDateTime ta = a.getLastLoginDate();
                    LocalDateTime tb = b.getLastLoginDate();
                    if (ta == null && tb == null) return 0;
                    if (ta == null) return 1;
                    if (tb == null) return -1;
                    return tb.compareTo(ta);
                };
                break;
            default: System.out.println("Невідомий варіант"); return;
        }

        LinkedList<User> sorted = registry.getInOrder(cmp);
        System.out.println("Відсортовано (getInOrder):");
        for (User u : sorted) System.out.println("  " + u);
    }

    private static void showFiltered(UserRegistry registry, Scanner scanner) {
        System.out.println("Оберіть умову фільтра:");
        System.out.println("  1) Лише авторизовані (isLoggedIn == true)");
        System.out.println("  2) Які жодного разу не входили (lastLoginDate == null)");
        System.out.println("  3) Ім'я починається з заданої літери");
        System.out.print("Ваш вибір: ");
        String choice = scanner.nextLine().trim();

        Predicate<User> pred;
        switch (choice) {
            case "1": pred = User::isLoggedIn; break;
            case "2": pred = u -> u.getLastLoginDate() == null; break;
            case "3":
                System.out.print("Літера: ");
                String letter = scanner.nextLine().trim().toLowerCase();
                pred = u -> !letter.isEmpty() && u.getName().toLowerCase().startsWith(letter);
                break;
            default: System.out.println("Невідомий варіант"); return;
        }

        LinkedList<User> filtered = registry.getFiltered(pred);
        if (filtered.isEmpty()) { System.out.println("Жоден користувач не відповідає умові"); return; }
        System.out.println("Знайдено (getFiltered):");
        for (User u : filtered) System.out.println("  " + u);
    }
}