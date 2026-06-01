package org.Darwyi.courseProject.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

final class SerializationUtil {
    private SerializationUtil(){}

    static byte[] toBytes(Serializable obj){
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)){
            oos.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException e){
            throw new RuntimeException("Помилка серіалізації: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    static <T> T fromBytes(byte[] data){
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))){
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException("Помилка десеріалізації: " + e.getMessage(), e);
        }
    }

    static <T extends Serializable> T deepCopy(T obj){
        return fromBytes(toBytes(obj));
    }
}
