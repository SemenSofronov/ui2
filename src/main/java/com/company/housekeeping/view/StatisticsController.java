package com.company.housekeeping.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.company.housekeeping.model.Operation;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class StatisticsController {

    @FXML
    private BarChart<String, Integer> revenueBarChart;
    @FXML
    private BarChart<String, Integer> expensesBarChart;

    @FXML
    private CategoryAxis revenueXAxis;
    @FXML
    private CategoryAxis expensesXAxis;

    private ObservableList<String> revenueMonthNames = FXCollections.observableArrayList();
    private ObservableList<String> expensesMonthNames = FXCollections.observableArrayList();

    @FXML
    private Button helpBtn;
    @FXML
    private Tooltip tooltipHelpBtn;

    @FXML
    private void initialize() {
        String[] revenueMonths = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        revenueMonthNames.addAll(Arrays.asList(revenueMonths));
        revenueXAxis.setCategories(revenueMonthNames);

        String[] expensesMonths = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        expensesMonthNames.addAll(Arrays.asList(expensesMonths));
        expensesXAxis.setCategories(expensesMonthNames);

        Image image = new Image(getClass().getResourceAsStream("/question-mark.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        helpBtn.setGraphic(imageView);
//        helpBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> tooltipHelpBtn.getOnShown());
//        helpBtn.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> tooltipHelpBtn.getOnHidden());
    }


    public void setOperationData(List<Operation> revenueOperations, List<Operation> expensesOperations) {
        int[] revenueMonthCounter = new int[12];
        for (Operation operation : revenueOperations) {
            int month = operation.getDateOperation().getMonthValue() - 1;
            revenueMonthCounter[month]++;
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        for (int i = 0; i < revenueMonthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(revenueMonthNames.get(i), revenueMonthCounter[i]));
        }

        revenueBarChart.getData().add(series);

        int[] expensesMonthCounter = new int[12];
        for (Operation operation : expensesOperations) {
            int month = operation.getDateOperation().getMonthValue() - 1;
            expensesMonthCounter[month]++;
        }

        series = new XYChart.Series<>();

        for (int i = 0; i < expensesMonthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(expensesMonthNames.get(i), expensesMonthCounter[i]));
        }

        expensesBarChart.getData().add(series);
    }

}
