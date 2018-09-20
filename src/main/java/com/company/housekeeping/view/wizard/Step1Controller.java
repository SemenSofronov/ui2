package com.company.housekeeping.view.wizard;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import com.company.housekeeping.model.Operation;

public class Step1Controller {
    @FXML
    private TextField inputBillField1;
    @FXML
    private TextField outputBillField2;
    @FXML
    private TextField categoryField3;

    @Inject
    Operation operation;

//    public Operation getOperation() {
//        return operation;
//    }
//
//    public Step1Controller setOperation(Operation operation) {
//        this.operation = operation;
//        return this;
//    }

    @FXML
    public void initialize() {

        inputBillField1.textProperty().bindBidirectional( operation.inputBillProperty() );
        outputBillField2.textProperty().bindBidirectional( operation.outputBillProperty() );
        categoryField3.textProperty().bindBidirectional( operation.categoryProperty() );

    }

    @Validate
    public boolean validate() throws Exception {

        if( inputBillField1.getText() == null || inputBillField1.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Step 1");
            alert.setHeaderText( "Missing Field" );
            alert.setContentText( "Field 1 is required." );
            alert.showAndWait();
            return false;
        }

        if( outputBillField2.getText() == null || inputBillField1.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Step 1");
            alert.setHeaderText( "Missing Field" );
            alert.setContentText( "Field 2 is required." );
            alert.showAndWait();
            return false;
        }

        if( categoryField3.getText() == null || categoryField3.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Step 3");
            alert.setHeaderText( "Missing Field" );
            alert.setContentText( "Field 3 is required." );
            alert.showAndWait();
            return false;
        }

        return true;
    }

    @Submit
    public void submit() throws Exception {
//        operation.setInputBill(inputBillField1.getText());
//        operation.setOutputBill(outputBillField2.getText());
//        operation.setCategory(categoryField3.getText());
    }
}
