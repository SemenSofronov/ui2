package com.company.housekeeping.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Category {

    private final StringProperty name;
    private final StringProperty description;
    private final StringProperty primaryCategory;

    public Category() {
        this(null, null, null);
    }

    public Category(String name, String description, String primaryCategory) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.primaryCategory = new SimpleStringProperty(primaryCategory);

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

    public String getPrimaryCategory() {
        return primaryCategory.get();
    }

    public StringProperty primaryCategoryProperty() {
        return primaryCategory;
    }

    public void setPrimaryCategory(String primaryCategory) {
        this.primaryCategory.set(primaryCategory);
    }
}
