package com.company.housekeeping.view;

import com.company.housekeeping.MainApp;
import com.company.housekeeping.model.Bill;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BillOverviewController {
    @FXML
    private TableView<Bill> billTable;
    @FXML
    private TableColumn<Bill, String> nameColumn;
    @FXML
    private TableColumn<Bill, String> descriptionColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label balanceLabel;

    private MainApp mainApp;


    public BillOverviewController() {
    }

    @FXML
    private void initialize() {
        balanceLabel.setTooltip(new Tooltip("в рублях"));
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        showBillDetails(null);

        billTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showBillDetails(newValue));
    }


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        billTable.setItems(mainApp.getBillData());
    }

    private void showBillDetails(Bill bill) {
        if (bill != null) {
            nameLabel.setText(bill.getName());
            descriptionLabel.setText(bill.getDescription());
            balanceLabel.setText(Integer.toString(bill.getBalance()));
        } else {
            nameLabel.setText("");
            descriptionLabel.setText("");
            balanceLabel.setText("");
        }
    }

    @FXML
    private void handleDeleteBill() {
        int selectedIndex = billTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            billTable.getItems().remove(selectedIndex);
        } else {
            showErrorMessage();
        }
    }

    @FXML
    private void handleNewBill() {
        Bill tempBill = new Bill();
        boolean okClicked = mainApp.showBillEditDialog(tempBill);
        if (okClicked) {
            mainApp.getBillData().add(tempBill);
        }
    }

    @FXML
    private void handleEditBill() {
        Bill selectedBill = billTable.getSelectionModel().getSelectedItem();
        if (selectedBill != null) {
            boolean okClicked = mainApp.showBillEditDialog(selectedBill);
            if (okClicked) {
                showBillDetails(selectedBill);
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
