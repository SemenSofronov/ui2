package com.company.housekeeping;

import com.company.housekeeping.view.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.company.housekeeping.model.Bill;
import com.company.housekeeping.model.Category;
import com.company.housekeeping.model.Operation;
import com.company.housekeeping.view.*;
import com.company.housekeeping.view.wizard.WizardController;
import com.company.housekeeping.view.wizard.WizardModule;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Bill> billData = FXCollections.observableArrayList();
    private ObservableList<Category> revenueCategoryData = FXCollections.observableArrayList();
    private ObservableList<Category> expensesCategoryData = FXCollections.observableArrayList();
    private ObservableList<Operation> revenueOperationData = FXCollections.observableArrayList();
    private ObservableList<Operation> expensesOperationData = FXCollections.observableArrayList();

    public MainApp() {
        billData.add(new Bill("Hans", "Muster"));
        billData.add(new Bill("Ruth", "Mueller"));
        billData.add(new Bill("Heinz", "Kurz"));
        billData.add(new Bill("Cornelia", "Meier"));
        billData.add(new Bill("Werner", "Meyer"));
        billData.add(new Bill("Lydia", "Kunz"));
        billData.add(new Bill("Anna", "Best"));
        billData.add(new Bill("Stefan", "Meier"));
        billData.add(new Bill("Martin", "Mueller"));

        revenueOperationData.add(new Operation("Hans", "Muster"));
        revenueOperationData.add(new Operation("Ruth", "Mueller"));
        revenueOperationData.add(new Operation("Heinz", "Kurz"));
        revenueOperationData.add(new Operation("Cornelia", "Meier"));
        revenueOperationData.add(new Operation("Werner", "Meyer"));
        revenueOperationData.add(new Operation("Lydia", "Kunz"));
        revenueOperationData.add(new Operation("Anna", "Best"));
        revenueOperationData.add(new Operation("Stefan", "Meier"));
        revenueOperationData.add(new Operation("Martin", "Mueller"));

        expensesOperationData.add(new Operation("Hans", "Muster"));
        expensesOperationData.add(new Operation("Ruth", "Mueller"));
        expensesOperationData.add(new Operation("Heinz", "Kurz"));
        expensesOperationData.add(new Operation("Cornelia", "Meier"));
        expensesOperationData.add(new Operation("Werner", "Meyer"));

        revenueCategoryData.add(new Category("Зарплата","доход с работы", "доходы"));
        revenueCategoryData.add(new Category("Стипендия","доход с учниверситета", "доходы"));

        expensesCategoryData.add(new Category("Развлечения","траты на развлечения", "расходы"));
        expensesCategoryData.add(new Category("Красота и медецина","траты на себя", "расходы"));
        expensesCategoryData.add(new Category("Еда","траты на еду", "расходы"));
        expensesCategoryData.add(new Category("Покупки","траты в магазине", "расходы"));
    }

    public ObservableList<Bill> getBillData() {
        return billData;
    }

    public ObservableList<Operation> getRevenueOperationData() {
        return revenueOperationData;
    }

    public ObservableList<Operation> getExpensesOperationData() {
        return expensesOperationData;
    }

    public ObservableList<Category> getRevenueCategoryData() {
        return revenueCategoryData;
    }

    public ObservableList<Category> getExpensesCategoryData() {
        return expensesCategoryData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Домашняя бухгалтерия");

        initRootLayout();
        showBillOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showBillOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/BillOverview.fxml"));
            AnchorPane billOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(billOverview);

            BillOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCategoryOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/CategoryOverview.fxml"));
            AnchorPane categoryOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(categoryOverview);

            CategoryOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOperationOverview(boolean isRevenue) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/OperationOverview.fxml"));
            AnchorPane operationOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(operationOverview);

            OperationOverviewController controller = loader.getController();
            controller.setMainApp(this, isRevenue);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showOperationEditDialog(Operation operation) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/OperationEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Операция");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            OperationEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setOperation(operation);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showOperationEditDialogWizard(Operation operation, boolean isRevenue) {
        try {

            final Injector injector = Guice.createInjector( new WizardModule() );
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/wizard/Wizard.fxml"));
            loader.setResources(null);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setControllerFactory((clazz) -> injector.getInstance(clazz));
            VBox page = (VBox) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Мастер добавления операций");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            WizardController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setOperation(operation);
            controller.setMainApp(this, isRevenue);
            dialogStage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showBillEditDialog(Bill bill) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/BillEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Счёт");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            BillEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setBill(bill);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showCategoryEditDialog(Category category) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/CategoryEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("категория");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CategoryEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCategory(category);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showBirthdayStatistics() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Statistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            rootLayout.setCenter(page);

            StatisticsController controller = loader.getController();
            controller.setOperationData(revenueOperationData, expensesOperationData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
