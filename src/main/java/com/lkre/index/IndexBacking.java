package com.lkre.index;

import javax.faces.bean.ManagedBean;
import com.lkre.index.services.DatabaseService;

@ManagedBean
public class IndexBacking {

    private String firstName = "John";
    private String lastName = "Doe";
    private DatabaseService databaseService = new DatabaseService();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String showGreeting() {
        return "Hello " + firstName + " " + lastName + "!";
    }

    public String readTemperatures() {

        return "brak pliku";
    }
}