package org.Darwyi.login_register_system;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;

public class UserRegistry {
    private HashSet<User> users = new HashSet<>();
    private int nextId = 1;

    public void registerUser(String login, String password) {
        User probe = new User(0, login, password);
        if (users.contains(probe)) {
            System.out.println("Користувач [" + login + "] вже є у списку");
            return;
        }
        User newUser = new User(nextId++, login, password);
        users.add(newUser);
        System.out.println("Користувача [" + login + "] успішно зареєстровано (id=" + newUser.getId() + ")");
    }

    public int loginUser(String login, String password) {
        for (User user : users) {
            if (user.getName().equals(login) && user.getPassword().equals(password)) {
                user.setLoggedIn(true);
                user.setLastLoginDate(LocalDateTime.now());
                System.out.println("Користувач [" + login + "] успішно увійшов у систему");
                return user.getId();
            }
        }
        System.out.println("Неможливо ідентифікувати або аутентифікувати користувача");
        return 0;
    }

    public void logoutUser(int userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                if (!user.isLoggedIn()) {
                    System.out.println("Користувач з id=" + userId + " не є авторизованим");
                    return;
                }
                user.setLoggedIn(false);
                System.out.println("Користувач [" + user.getName() + "] вийшов із системи");
                return;
            }
        }
        System.out.println("Користувача з id=" + userId + " не знайдено");
    }

    public boolean isUserRegistered(String login) {
        User probe = new User(0, login, "");
        return users.contains(probe);
    }

    public void removeUser(int id) {
        Iterator<User> it = users.iterator();
        while (it.hasNext()) {
            User user = it.next();
            if (user.getId() == id) {
                it.remove();
                System.out.println("Користувача [" + user.getName() + "] видалено");
                return;
            }
        }
        System.out.println("Користувача з id=" + id + " не знайдено");
    }

    public void printTotalUniqueUsers() {
        System.out.println("Кількість унікальних користувачів: " + users.size());
    }

    public void displayAllUsers() {
        if (users.isEmpty()) {
            System.out.println("Список користувачів порожній");
            return;
        }
        for (User user : users) {
            System.out.println(user);
        }
    }

    public void printRegisteredStatus(String login) {
        boolean registered = isUserRegistered(login);
        System.out.println("Користувач [" + login + "] "
                + (registered ? "зареєстрований" : "не зареєстрований"));
    }
}
