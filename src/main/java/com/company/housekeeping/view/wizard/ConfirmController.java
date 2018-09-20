package com.company.housekeeping.view.wizard;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import com.company.housekeeping.model.Operation;
import com.company.housekeeping.util.DateUtil;

public class ConfirmController {

    @FXML
    private TextField inputBillField1;
    @FXML
    private TextField outputBillField2;
    @FXML
    private TextField categoryField3;
    @FXML
    private TextField valueField4;
    @FXML
    private TextField typeField5;
    @FXML
    private TextField descriptionField6;
    @FXML
    private TextField dateOperationField7;

    @Inject
    private Operation operation;

//    public void setOperation(Operation operation) {
//        this.operation = operation;
//        if (operation != null) {
//            inputBillField1.setText(operation.getOutputBill());
//            outputBillField2.setText(operation.getInputBill());
//            categoryField3.setText(operation.getCategory());
//            valueField4.setText(Integer.toString(operation.getValue()));
//            typeField5.setText(operation.getType());
//            descriptionField6.setText(operation.getDescription());
//            dateOperationField7.setText(DateUtil.format(operation.getDateOperation()));
//            dateOperationField7.setPromptText("dd.mm.yyyy");
//        }
//    }

    @FXML
    public void initialize() {
        inputBillField1.textProperty().bind(operation.inputBillProperty());
        outputBillField2.textProperty().bind(operation.outputBillProperty());
        categoryField3.textProperty().bind(operation.categoryProperty());
        valueField4.textProperty().bind(operation.valueProperty().asString());
        typeField5.textProperty().bind(operation.typeProperty());
        descriptionField6.textProperty().bind(operation.descriptionProperty());
        dateOperationField7.textProperty().bind(operation.dateOperationProperty().asString());
    }

    @Submit
    public void submit() throws Exception {
        operation.setOutputBill(inputBillField1.getText());
        operation.setInputBill(outputBillField2.getText());
        operation.setCategory(categoryField3.getText());
        operation.setValue(Integer.parseInt(valueField4.getText()));
        operation.setType(typeField5.getText());
        operation.setDescription(descriptionField6.getText());
        operation.setDateOperation(DateUtil.parse(dateOperationField7.getText()));

    }

    @Finish
    public Operation getOperation() {
        return operation;
    }
}

