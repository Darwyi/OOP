package org.Darwyi.login_register_system;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Predicate;

public class UserRegistry {
    private HashMap<UserIdentifier, User> users = new HashMap<>();
    private int nextId = 1;

    public void registerUser(String login, String password) {
        if (findUserByName(login) != null) {
            System.out.println("Користувач [" + login + "] вже є у списку");
            return;
        }
        UserIdentifier id = new UserIdentifier(nextId++, login);
        User newUser = new User(id, password);
        users.put(id, newUser);
        System.out.println("Користувача [" + login + "] успішно зареєстровано (id=" + id.getId() + ")");
    }

    public int loginUser(String login, String password) {
        User user = findUserByName(login);
        if (user != null && user.getPassword().equals(password)) {
            user.setLoggedIn(true);
            user.setLastLoginDate(LocalDateTime.now());
            System.out.println("Користувач [" + login + "] успішно увійшов у систему");
            return user.getId();
        }
        System.out.println("Неможливо ідентифікувати або аутентифікувати користувача");
        return 0;
    }

    public void logoutUser(int userId) {
        User user = findUserById(userId);
        if (user == null) {
            System.out.println("Користувача з id=" + userId + " не знайдено");
            return;
        }
        if (!user.isLoggedIn()) {
            System.out.println("Користувач з id=" + userId + " не є авторизованим");
            return;
        }
        user.setLoggedIn(false);
        System.out.println("Користувач [" + user.getName() + "] вийшов із системи");
    }

    public boolean isUserRegistered(String login) {
        return findUserByName(login) != null;
    }

    public void removeUser(int id) {
        UserIdentifier keyToRemove = null;
        for (UserIdentifier key : users.keySet()) {
            if (key.getId() == id) {
                keyToRemove = key;
                break;
            }
        }
        if (keyToRemove != null) {
            User removed = users.remove(keyToRemove);
            System.out.println("Користувача [" + removed.getName() + "] видалено");
        } else {
            System.out.println("Користувача з id=" + id + " не знайдено");
        }
    }

    public void printTotalUniqueUsers() {
        System.out.println("Кількість унікальних користувачів: " + users.size());
    }

    public void displayAllUsers() {
        if (users.isEmpty()) {
            System.out.println("Список користувачів порожній");
            return;
        }
        for (User user : users.values()) {
            System.out.println(user);
        }
    }

    public void printRegisteredStatus(String login) {
        boolean registered = isUserRegistered(login);
        System.out.println("Користувач [" + login + "] "
                + (registered ? "зареєстрований" : "не зареєстрований"));
    }

    public LinkedList<User> getUserList() {
        return new LinkedList<>(users.values());
    }

    public LinkedList<User> getInOrder(Comparator<User> comparator) {
        LinkedList<User> list = getUserList();
        list.sort(comparator);
        return list;
    }

    public LinkedList<User> getFiltered(Predicate<User> predicate) {
        LinkedList<User> result = new LinkedList<>();
        for (User u : users.values()) {
            if (predicate.test(u)) {
                result.add(u);
            }
        }
        return result;
    }

    public int size() {
        return users.size();
    }

    public void saveToFile(String path) throws IOException {
        try (
                FileOutputStream     fos = new FileOutputStream(path);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream   oos = new ObjectOutputStream(bos)
        ) {
            oos.writeObject(users);
            oos.writeInt(nextId);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String path) throws IOException, ClassNotFoundException {
        try (
                FileInputStream     fis = new FileInputStream(path);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream   ois = new ObjectInputStream(bis)
        ) {
            HashMap<UserIdentifier, User> loaded =
                    (HashMap<UserIdentifier, User>) ois.readObject();
            int loadedNextId = ois.readInt();

            users.clear();
            users.putAll(loaded);
            nextId = loadedNextId;
        }
    }

    private User findUserByName(String login) {
        for (Map.Entry<UserIdentifier, User> entry : users.entrySet()) {
            if (entry.getKey().getName().equals(login)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private User findUserById(int id) {
        for (Map.Entry<UserIdentifier, User> entry : users.entrySet()) {
            if (entry.getKey().getId() == id) {
                return entry.getValue();
            }
        }
        return null;
    }
}