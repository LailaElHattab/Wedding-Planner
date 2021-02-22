package weddingplanner.managers;

import weddingplanner.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author laila-elhattab
 */

public abstract class AbstractDataManager<T> implements DataManager<T>{

    //Read from a file
    @Override
    public List<T> loadAll() {
        List<T> objects;
        try {
            ObjectInputStream obin = new ObjectInputStream(new FileInputStream(getStoreFile()));
            objects = (List<T>) obin.readObject();
            obin.close();
        } catch (Exception ex) {
            objects = new ArrayList<>();
            System.out.println("Error occurred: " + ex.getMessage());
        }
        return objects;
    }

    //add an object whether its an event or a user
    @Override
    public void add(T obj) throws Exception {
        List<T> objects = loadAll();
        objects.add(obj);
        ObjectOutputStream obout = new ObjectOutputStream(new FileOutputStream(getStoreFile()));
        obout.writeObject(objects);
        obout.close();
    }

    //edits an object whether its an event or a user
    @Override
    public void edit(T obj) throws Exception {
        List<T> objects = loadAll();
        for (int i = 0; i < objects.size(); i++) {
            if(objects.get(i).equals(obj)){
                objects.set(i, obj);
                break;
            }
        }
        ObjectOutputStream obout = new ObjectOutputStream(new FileOutputStream(getStoreFile()));
        obout.writeObject(objects);
        obout.close();
    }

    //Returns fileName
    protected abstract File getStoreFile();
}
