package br.edu.ifba.inf008.shell;

import java.io.*;
import java.util.ArrayList;

import br.edu.ifba.inf008.interfaces.IIOController;

public class IOController implements IIOController {
    private static final String FILE_PATH = "lms.bin";

    public static LibraryDatabase loadData() {
        System.out.println("Loading data...");
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            System.out.println("No saved data found. Starting fresh.");
            return new LibraryDatabase(new ArrayList<>(), new ArrayList<>());
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            LibraryDatabase loadedData = (LibraryDatabase) ois.readObject();
            System.out.println("Data loaded successfully from " + FILE_PATH);
            return loadedData;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load data, starting with empty database.");
            e.printStackTrace();
        }
        
        return new LibraryDatabase(new ArrayList<>(), new ArrayList<>());
    }

    public static void saveData(LibraryDatabase data) {
        System.out.println("Saving data...");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(data);
            System.out.println("Data saved successfully to " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Failed to save data.");
            e.printStackTrace();
        }
    }
}