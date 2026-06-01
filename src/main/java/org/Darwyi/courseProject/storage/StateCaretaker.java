package org.Darwyi.courseProject.storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayDeque;
import java.util.Deque;

public class StateCaretaker {
    private final Deque<SystemMemento> history = new ArrayDeque<>();

    public void backup(SystemMemento memento){ history.push(memento); }

    public SystemMemento undo(){ return history.isEmpty() ? null : history.pop(); }

    public int historySize(){ return history.size(); }

    public void saveToFile(SystemMemento memento, String path){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))){
            oos.writeObject(memento);
        } catch (IOException e){
            throw new RuntimeException("Не вдалося зберегти стан: " + e.getMessage(), e);
        }
    }

    public SystemMemento loadFromFile(String path){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))){
            return (SystemMemento) ois.readObject();
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException("Не вдалося завантажити стан: " + e.getMessage(), e);
        }
    }
}
