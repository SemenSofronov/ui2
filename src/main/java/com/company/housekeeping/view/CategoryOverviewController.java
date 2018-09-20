package com.company.housekeeping.view;

import com.company.housekeeping.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import com.company.housekeeping.model.Category;

public class CategoryOverviewController {
    @FXML
    private ListView<Category> revenueCategoryList;
    @FXML
    private ListView<Category> expensesCategoryList;

    @FXML
    private Label nameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label primaryCategoryLabel;

    private MainApp mainApp;
    private boolean isRevenueSelected = true;

    @FXML
    private void initialize() {
        showCategoryDetails(null);

        revenueCategoryList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCategoryDetails(newValue));

        expensesCategoryList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCategoryDetails(newValue));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        revenueCategoryList.setItems(mainApp.getRevenueCategoryData());
        revenueCategoryList.setCellFactory(param -> new ListCell<Category>() {
            @Override
            protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        expensesCategoryList.setItems(mainApp.getExpensesCategoryData());
        expensesCategoryList.setCellFactory(param -> new ListCell<Category>() {
            @Override
            protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }

    private void showCategoryDetails(Category category) {
        if (category != null) {
            nameLabel.setText(category.getName());
            descriptionLabel.setText(category.getDescription());
            primaryCategoryLabel.setText("Детали категории " + category.getPrimaryCategory() + ":");
            isRevenueSelected = category.getPrimaryCategory().equals("доходы");
        } else {
            nameLabel.setText("");
            descriptionLabel.setText("");
            primaryCategoryLabel.setText("Детали категории:");
        }
    }


    @FXML
    private void handleDeleteCategory() {
        int selectedIndex;
        if(isRevenueSelected){
            selectedIndex = revenueCategoryList.getSelectionModel().getSelectedIndex();
        }else{
            selectedIndex = expensesCategoryList.getSelectionModel().getSelectedIndex();
        }
        if (selectedIndex >= 0) {
            if (isRevenueSelected) {
                revenueCategoryList.getItems().remove(selectedIndex);
            } else {
                expensesCategoryList.getItems().remove(selectedIndex);
            }
        } else {
            showErrorMessage();
        }
    }

    @FXML
    private void handleNewCategory() {
        Category newCategory = new Category();
        boolean okClicked = mainApp.showCategoryEditDialog(newCategory);
        if (okClicked) {
            if (isRevenueSelected) {
                mainApp.getRevenueCategoryData().add(newCategory);
            } else {
                mainApp.getExpensesCategoryData().add(newCategory);

            }
        }
    }

    @FXML
    private void handleEditCategory() {
        Category selectedCategory;

        if(isRevenueSelected){
            selectedCategory = revenueCategoryList.getSelectionModel().getSelectedItem();
        }else{
            selectedCategory = expensesCategoryList.getSelectionModel().getSelectedItem();
        }

        if (selectedCategory != null) {
            boolean okClicked = mainApp.showCategoryEditDialog(selectedCategory);
            if (okClicked) {
                showCategoryDetails(selectedCategory);
            }

        } else {
            showErrorMessage();
        }
    }


    private void showErrorMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Ничего не выбрано");
        alert.setHeaderText("Выберете операцию");
        alert.setContentText("Пожалуйста, выбирете операции в таблице");
        alert.showAndWait();
    }
}
