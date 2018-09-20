package com.company.housekeeping.view.wizard;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import com.company.housekeeping.model.Operation;

public class Step2Controller {
    @FXML
    private TextField valueField4;

    @Inject
    Operation operation;

//    public Operation getOperation() {
//        return operation;
//    }
//
//    public Step2Controller setOperation(Operation operation) {
//        this.operation = operation;
//        return this;
//    }

    @FXML
    public void initialize() {
        valueField4.textProperty().bindBidirectional(operation.categoryProperty());
    }
    @Validate
    public boolean validate() throws Exception {

        if( valueField4.getText() == null || valueField4.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Step 1");
            alert.setHeaderText( "Missing Field" );
            alert.setContentText( "Field 1 is required." );
            alert.showAndWait();
            return false;
        }


        return true;
    }

    @Submit
    public void submit() throws Exception {
//        operation.setValue(Integer.parseInt(valueField4.getText()));

    }
}
