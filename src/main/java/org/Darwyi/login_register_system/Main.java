package org.Darwyi.login_register_system;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserRegistry registry = new UserRegistry();
        Scanner scanner = new Scanner(System.in);

        registry.registerUser("alice", "pass1234");
        registry.registerUser("john", "securePass");
        registry.registerUser("jane", "qwerty123");
        registry.registerUser("alice", "otherPass");
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
                    if (newUser != 0) {
                        currentUserId = newUser;
                    }
                    break;
                case "3":
                    System.out.print("ID користувача: ");
                    try {
                        registry.logoutUser(currentUserId);
                    } catch (NumberFormatException e) {
                        System.out.println("Невірний формат ID");
                    }
                    break;
                case "4":
                    System.out.print("Логін для перевірки: ");
                    String checkLogin = scanner.nextLine().trim();
                    registry.printRegisteredStatus(checkLogin);
                    break;
                case "5":
                    System.out.print("ID для видалення: ");
                    try {
                        int removeId = Integer.parseInt(scanner.nextLine().trim());
                        registry.removeUser(removeId);
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
                case "0":
                    System.out.println("До побачення!");
                    running = false;
                    break;
                default:
                    System.out.println("Невідомий пункт меню");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("1. Зареєструвати користувача");
        System.out.println("2. Увійти в систему (login)");
        System.out.println("3. Вийти з системи (logout)");
        System.out.println("4. Перевірити реєстрацію");
        System.out.println("5. Видалити користувача");
        System.out.println("6. Кількість унікальних користувачів");
        System.out.println("7. Показати всіх користувачів");
        System.out.println("0. Вихід");
    }
}
