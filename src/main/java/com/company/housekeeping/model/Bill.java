package com.company.housekeeping.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Bill {
    private final StringProperty name;
    private final StringProperty description;
    private final IntegerProperty balance;

    public Bill() {
        this(null, null);
    }

    public Bill(String name, String description) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.balance = new SimpleIntegerProperty(12);

    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public int getBalance() {
        return balance.get();
    }

    public IntegerProperty balanceProperty() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance.set(balance);
    }
}
