/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weddingplanner.managers;

import weddingplanner.model.Event;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;


public class EventManager extends AbstractDataManager<Event> {

    private static File file = new File("data/events");

    private static EventManager instance;

    private EventManager(){
    }

    //Returns file name
    @Override
    protected File getStoreFile() {
        return file;
    }

    //Checks if it is initialized
    public static EventManager getInstance(){
        if(instance == null){
            instance = new EventManager();
        }
        return instance;
    }
}
