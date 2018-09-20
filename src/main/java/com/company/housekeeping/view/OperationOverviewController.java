package com.company.housekeeping.view;

import com.company.housekeeping.MainApp;
import com.company.housekeeping.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.company.housekeeping.model.Operation;

public class OperationOverviewController {

    @FXML
    private TableView<Operation> operationTable;
    @FXML
    private TableColumn<Operation, String> outputBillColumn;
    @FXML
    private TableColumn<Operation, String> inputBillColumn;

    @FXML
    private Label outputBillLabel;
    @FXML
    private Label inputBillLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label valueCodeLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label dateOperationLabel;

    @FXML
    private Label nameSceneLabel;

    private MainApp mainApp;
    private boolean isRevenue;

    public OperationOverviewController() {
    }

    @FXML
    private void initialize() {
        valueCodeLabel.setTooltip(new Tooltip("в рублях"));
        outputBillColumn.setCellValueFactory(cellData -> cellData.getValue().outputBillProperty());
        inputBillColumn.setCellValueFactory(cellData -> cellData.getValue().inputBillProperty());

        showOperationDetails(null);

        operationTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showOperationDetails(newValue));
    }

    public void setMainApp(MainApp mainApp, boolean isRevenue) {

        this.mainApp = mainApp;
        this.isRevenue = isRevenue;
        operationTable.setItems(isRevenue ? mainApp.getRevenueOperationData() : mainApp.getExpensesOperationData());
        nameSceneLabel.setText(isRevenue ? "ДОХОДЫ" : "РАСХОДЫ");
    }

    private void showOperationDetails(Operation operation) {
        if (operation != null) {
            outputBillLabel.setText(operation.getOutputBill());
            inputBillLabel.setText(operation.getInputBill());
            categoryLabel.setText(operation.getCategory());
            valueCodeLabel.setText(Integer.toString(operation.getValue()));
            typeLabel.setText(operation.getType());
            descriptionLabel.setText(operation.getDescription());
            dateOperationLabel.setText(DateUtil.format(operation.getDateOperation()));
        } else {
            outputBillLabel.setText("");
            inputBillLabel.setText("");
            categoryLabel.setText("");
            valueCodeLabel.setText("");
            typeLabel.setText("");
            descriptionLabel.setText("");
            dateOperationLabel.setText("");
        }
    }

    @FXML
    private void handleDeleteOperation() {
        int selectedIndex = operationTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            operationTable.getItems().remove(selectedIndex);
        } else {
            showErrorMessage();
        }
    }

    @FXML
    private void handleNewOperation() {
        Operation tempOperation = new Operation();
        mainApp.showOperationEditDialogWizard(tempOperation, isRevenue);
    }

    @FXML
    private void handleEditOperation() {
        Operation selectedOperation = operationTable.getSelectionModel().getSelectedItem();
        if (selectedOperation != null) {
            boolean okClicked = mainApp.showOperationEditDialog(selectedOperation);
            if (okClicked) {
                showOperationDetails(selectedOperation);
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
