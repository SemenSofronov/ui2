package com.company.housekeeping.view;

import com.company.housekeeping.model.Bill;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class BillEditDialogController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField balanceField;

    private Stage dialogStage;
    private Bill bill;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
        balanceField.setTooltip(new Tooltip("в рублях"));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setBill(Bill bill) {
        this.bill = bill;

        nameField.setText(bill.getName());
        descriptionField.setText(bill.getDescription());
        balanceField.setText(Integer.toString(bill.getBalance()));
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            bill.setName(nameField.getText());
            bill.setDescription(descriptionField.getText());
            bill.setBalance(Integer.parseInt(balanceField.getText()));
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

        if (balanceField.getText() == null || balanceField.getText().length() == 0) {
            errorMessage += "Не правильный баланс!\n";
        } else {
            try {
                if (Integer.parseInt(balanceField.getText()) < 0) {
                    errorMessage += "Не правильный баланс (должно быть положительным)!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "Не правильный баланс (должно быть Integer)!\n";
            }
        }

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
