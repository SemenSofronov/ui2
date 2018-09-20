package com.company.housekeeping.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.company.housekeeping.model.Category;

public class CategoryEditDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private ChoiceBox primaryCategoryChoiceBox;

    private Stage dialogStage;
    private Category category;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
        ObservableList<String> categories //
                = FXCollections.observableArrayList("доходы", "расходы");

        primaryCategoryChoiceBox.setItems(categories);
        primaryCategoryChoiceBox.setValue("доходы");

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCategory(Category category) {
        this.category = category;

        nameField.setText(category.getName());
        descriptionField.setText(category.getDescription());
        primaryCategoryChoiceBox.setValue(category.getPrimaryCategory());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            category.setName(nameField.getText());
            category.setDescription(descriptionField.getText());
            category.setPrimaryCategory(primaryCategoryChoiceBox.getSelectionModel().getSelectedItem().toString());
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }


    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Не правильное название!\n";
        }
        if (descriptionField.getText() == null || descriptionField.getText().length() == 0) {
            errorMessage += "Не правильное описание!\n";
        }
//        if (primaryCategoryChoiceBox.getText() == null || primaryCategoryChoiceBox.getText().length() == 0) {
//            errorMessage += "Не правильное категория!\n";
//        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Не правильные поля");
            alert.setHeaderText("Пожалуйста, заполните поля правильно!");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
