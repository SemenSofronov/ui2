package com.company.housekeeping.view.wizard;

import com.company.housekeeping.model.Operation;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class Step3Controller {

    @FXML
    private TextField typeField5;
    @FXML
    private TextField descriptionField6;
    @FXML
    private TextField dateOperationField7;

    @Inject
    private Operation operation;

//    public Operation getOperation() {
//        return operation;
//    }

//    public Step3Controller setOperation(Operation operation) {
//        this.operation = operation;
//        return this;
//    }

    @FXML
    public void initialize() {
        typeField5.textProperty().bindBidirectional(operation.typeProperty());
        descriptionField6.textProperty().bindBidirectional(operation.descriptionProperty());
        dateOperationField7.textProperty().bind(operation.dateOperationProperty().asString());
    }

    @Validate
    public boolean validate() throws Exception {
        if (typeField5.getText() == null || typeField5.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Step 3");
            alert.setHeaderText("Missing Field");
            alert.setContentText("Field 5 is required.");
            alert.showAndWait();
            return false;
        }

        if (descriptionField6.getText() == null || descriptionField6.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Step 3");
            alert.setHeaderText("Missing Field");
            alert.setContentText("Field 6 is required.");
            alert.showAndWait();
            return false;
        }

        if (dateOperationField7.getText() == null || dateOperationField7.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Step 3");
            alert.setHeaderText("Missing Field");
            alert.setContentText("Field 7 is required.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @Submit
    public void submit() throws Exception {
//        operation.setType(typeField5.getText());
//        operation.setDescription(descriptionField6.getText());
//        operation.setDateOperation(DateUtil.parse(dateOperationField7.getText()));

    }
}
