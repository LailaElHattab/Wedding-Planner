/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weddingplanner.managers;

import weddingplanner.model.Client;
import weddingplanner.model.Planner;
import weddingplanner.model.User;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author laila-elhattab
 */
public class UsersManager extends AbstractDataManager<User> {

    private static File file = new File("data/users");

    private static UsersManager instance;

    private UsersManager() {
    }

    public static UsersManager getInstance() {
        if (instance == null) {
            instance = new UsersManager();
        }
        return instance;
    }

    @Override
    protected File getStoreFile() {
        return file;
    }

    //Username and password checker
    public User login(String email, char[] password) {
        List<User> users = loadAll();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && new String(user.getPassword()).equals(new String(password))) {
                return user;
            }
        }
        return null;
    }

    //Returns the user
    public User getUser(String email) {
        List<User> users = loadAll();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    //Checks if there is an already registered user with the same email
    public boolean check(User userAdded) {
        List<User> users = loadAll();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(userAdded.getEmail())) {
                return false;
            }
        }
        return true;
    }


}
