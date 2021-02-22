/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weddingplanner.model;

/**
 * @author laila-elhattab
 */
public class Planner extends User {
    int exYears;
    int salary;
    String photoFile;

    public Planner(String name, String username, char[] password) {
        super(name, username, password);
    }

    public int getExYears() {
        return exYears;
    }

    public void setExYears(int exYears) {
        this.exYears = exYears;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(String photoFile) {
        this.photoFile = photoFile;
    }
}
