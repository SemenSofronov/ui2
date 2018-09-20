package com.company.housekeeping.view;

import com.company.housekeeping.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import com.company.housekeeping.model.Operation;

public class OperationEditDialogController {

    @FXML
    private TextField outputBillField;
    @FXML
    private TextField inputBillField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField valueCodeField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField dateOperationField;

    private Stage dialogStage;
    private Operation operation;
    private boolean okClicked = false;


    @FXML
    private void initialize() {
        valueCodeField.setTooltip(new Tooltip("в рублях"));
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setOperation(Operation operation) {
        this.operation = operation;

        outputBillField.setText(operation.getOutputBill());
        inputBillField.setText(operation.getInputBill());
        categoryField.setText(operation.getCategory());
        valueCodeField.setText(Integer.toString(operation.getValue()));
        typeField.setText(operation.getType());
        descriptionField.setText(operation.getDescription());
        dateOperationField.setText(DateUtil.format(operation.getDateOperation()));
        dateOperationField.setPromptText("dd.mm.yyyy");
    }


    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            operation.setOutputBill(outputBillField.getText());
            operation.setInputBill(inputBillField.getText());
            operation.setCategory(categoryField.getText());
            operation.setValue(Integer.parseInt(valueCodeField.getText()));
            operation.setType(typeField.getText());
            operation.setDescription(descriptionField.getText());
            operation.setDateOperation(DateUtil.parse(dateOperationField.getText()));

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

        if (outputBillField.getText() == null || outputBillField.getText().length() == 0) {
            errorMessage += "Не правильный счет!\n";
        }
        if (inputBillField.getText() == null || inputBillField.getText().length() == 0) {
            errorMessage += "Не правильный счет!\n";
        }
        if (categoryField.getText() == null || categoryField.getText().length() == 0) {
            errorMessage += "Не правильная категория!\n";
        }

        if (valueCodeField.getText() == null || valueCodeField.getText().length() == 0) {
            errorMessage += "Не правильное значение!\n";
        } else {
            try {
                if (Integer.parseInt(valueCodeField.getText()) < 0) {
                    errorMessage += "Не правильное значение (должно быть положительным)!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "Не правильное значение (должно быть Integer)!\n";
            }
        }

        if (typeField.getText() == null || typeField.getText().length() == 0) {
            errorMessage += "Не правильный тип!\n";
        }

        if (descriptionField.getText() == null || descriptionField.getText().length() == 0) {
            errorMessage += "Не правильное описание!\n";
        }

        if (dateOperationField.getText() == null || dateOperationField.getText().length() == 0) {
            errorMessage += "Не правильная дата операции!\n";
        } else {
            if (!DateUtil.validDate(dateOperationField.getText())) {
                errorMessage += "не правильная дата операции. Используйте формат dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Не правильные поля");
            alert.setHeaderText("Пожалуйста, заполните поля правильно!");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
