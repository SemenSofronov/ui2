package com.company.housekeeping.view.wizard;

import com.company.housekeeping.MainApp;
import com.company.housekeeping.model.Operation;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.beans.binding.When;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WizardController {
    private final int INDICATOR_RADIUS = 10;

    private final String CONTROLLER_KEY = "controller";

    @FXML
    VBox contentPanel;

    @FXML
    HBox hboxIndicators;

    @FXML
    Button btnNext, btnBack, btnCancel, btnFinish;

    private final List<Parent> steps = new ArrayList<>();
    private final IntegerProperty currentStep = new SimpleIntegerProperty(-1);

    private Stage dialogStage;

    @Inject
    Injector injector;

    @Inject
    Operation operation;
    private boolean okClicked = false;
    private MainApp mainApp;
    private boolean isRevenue;

    public WizardController() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainApp(MainApp mainApp, boolean isRevenue) {
        this.isRevenue = isRevenue;
        this.mainApp = mainApp;
    }


    public boolean isOkClicked() {
        return okClicked;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
    public Operation getOperation() {
        return operation;
    }


    @FXML
    public void initialize() throws Exception {

        buildSteps();

        initButtons();

        buildIndicatorCircles();

        setInitialContent();
    }

    private void initButtons() {
        btnBack.disableProperty().bind(currentStep.lessThanOrEqualTo(0));
        btnNext.disableProperty().bind(currentStep.greaterThanOrEqualTo(steps.size() - 1));

//        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
//            @Override public void handle(ActionEvent e) {
//                okClicked = true;
//                dialogStage.close();
//            }
//        });

        btnCancel.textProperty().bind(
                new When(currentStep.lessThan(steps.size() - 1))
                        .then("Cancel")
                        .otherwise("Start Over")
        );

    }

    private void setInitialContent() {
        currentStep.set(0);  // first element
        contentPanel.getChildren().add(steps.get(currentStep.get()));
    }

    private void buildIndicatorCircles() {
        for (int i = 0; i < steps.size(); i++) {
            hboxIndicators.getChildren().add(createIndicatorCircle(i));
        }
    }

    private void buildSteps() throws java.io.IOException {

        final JavaFXBuilderFactory bf = new JavaFXBuilderFactory();


        final Callback<Class<?>, Object> cb = (clazz) -> injector.getInstance(clazz);
        FXMLLoader fxmlLoaderStep1 = new FXMLLoader(WizardController.class.getResource("/wizard/Step1.fxml"), null, bf, cb);
        Parent step1 = fxmlLoaderStep1.load();
        step1.getProperties().put(CONTROLLER_KEY, fxmlLoaderStep1.getController());


        FXMLLoader fxmlLoaderStep2 = new FXMLLoader(WizardController.class.getResource("/wizard/Step2.fxml"), null, bf,cb);
        Parent step2 = fxmlLoaderStep2.load();
        step2.getProperties().put(CONTROLLER_KEY, fxmlLoaderStep2.getController());


        FXMLLoader fxmlLoaderStep3 = new FXMLLoader(WizardController.class.getResource("/wizard/Step3.fxml"), null, bf,cb);
        Parent step3 = fxmlLoaderStep3.load();
        step3.getProperties().put(CONTROLLER_KEY, fxmlLoaderStep3.getController());


        FXMLLoader fxmlLoaderConfirm = new FXMLLoader(WizardController.class.getResource("/wizard/Confirm.fxml"), null, bf,cb);
        Parent confirm = fxmlLoaderConfirm.load();
        confirm.getProperties().put(CONTROLLER_KEY, fxmlLoaderConfirm.getController());


        FXMLLoader fxmlLoaderCompleted = new FXMLLoader(WizardController.class.getResource("/wizard/Completed.fxml"), null, bf,cb);
        Parent completed = fxmlLoaderCompleted.load();
        completed.getProperties().put(CONTROLLER_KEY, fxmlLoaderCompleted.getController());

        steps.addAll(Arrays.asList(
                step1, step2, step3, confirm, completed
        ));
    }

    private Circle createIndicatorCircle(int i) {

        Circle circle = new Circle(INDICATOR_RADIUS, Color.WHITE);
        circle.setStroke(Color.BLACK);

        circle.fillProperty().bind(
                new When(
                        currentStep.greaterThanOrEqualTo(i))
                        .then(Color.DODGERBLUE)
                        .otherwise(Color.WHITE));

        return circle;
    }

    @FXML
    public void next() {

        Parent p = steps.get(currentStep.get());
        Object controller = p.getProperties().get(CONTROLLER_KEY);

        if(steps.size() - 1 == currentStep.get()){
            okClicked = true;
        }

        // validate
        Method v = getMethod(Validate.class, controller);
        if (v != null) {
            try {
                Object retval = v.invoke(controller);
                if (retval != null && ((Boolean) retval) == false) {
                    return;
                }

            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }


        // submit
        Method sub = getMethod(Submit.class, controller);
        if (sub != null) {
            try {
                sub.invoke(controller);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }


        // get operation
        Method fin = getMethod(Finish.class, controller);
        if (fin != null) {
            try {
               operation = (Operation)fin.invoke(controller);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        if (currentStep.get() < (steps.size() - 1)) {
            contentPanel.getChildren().remove(steps.get(currentStep.get()));
            currentStep.set(currentStep.get() + 1);
            contentPanel.getChildren().add(steps.get(currentStep.get()));
        }
    }

    @FXML
    public void back() {

        if (currentStep.get() > 0) {
            contentPanel.getChildren().remove(steps.get(currentStep.get()));
            currentStep.set(currentStep.get() - 1);
            contentPanel.getChildren().add(steps.get(currentStep.get()));
        }
    }

    @FXML
    public void cancel() {

        contentPanel.getChildren().remove(steps.get(currentStep.get()));
        currentStep.set(0);  // first screen
        contentPanel.getChildren().add(steps.get(currentStep.get()));

//        operation.reset();
    }

    @FXML
    public void finish(){
        if (isRevenue){
            mainApp.getRevenueOperationData().add(operation);
        }else{
            mainApp.getExpensesOperationData().add(operation);
        }

        okClicked = true;
        dialogStage.close();
    }

    private Method getMethod(Class<? extends Annotation> an, Object obj) {

        if (an == null) {
            return null;
        }
        if (obj == null) {
            return null;
        }

        Method[] methods = obj.getClass().getMethods();

        if (methods != null && methods.length > 0) {
            for (Method m : methods) {
                if (m.isAnnotationPresent(an)) {
                    return m;
                }
            }
        }
        return null;
    }
}
