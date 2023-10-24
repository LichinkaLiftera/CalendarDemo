package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        DataSearch dataSearch = new DataSearch(LocalDate.now().toString(), LocalDate.now().toString());
        DatePicker startPicker = new DatePicker(LocalDate.now());
        startPicker.setMaxWidth(150);
        startPicker.setShowWeekNumbers(true);
        startPicker.setOnAction(actionEvent -> {
            dataSearch.setStartDate(startPicker.getValue().toString());
        });
        VBox startBox = new VBox(new Label("Start date"), startPicker);

        DatePicker endPicker = new DatePicker(LocalDate.now());
        endPicker.setMaxWidth(150);
        endPicker.setShowWeekNumbers(true);
        endPicker.setOnAction(actionEvent -> {
            dataSearch.setEndDate(endPicker.getValue().toString());
        });
        VBox endBox = new VBox(new Label("End date"), endPicker);

        ObservableList<String> weekDays = FXCollections.observableArrayList
                ("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        Spinner<String> weekDaysSelect = new Spinner<>(weekDays);
        CheckBox actWeekDays = new CheckBox();
        HBox weekDaysHbox = new HBox(10, weekDaysSelect, actWeekDays);
        VBox weekDaysVbox = new VBox(new Label("Day of week"), weekDaysHbox);

        Spinner<Integer> dateSpin = new Spinner<>(1, 31, 1);
        CheckBox actDate = new CheckBox();
        HBox dateHbox = new HBox(10, dateSpin, actDate);
        VBox dateVbox = new VBox(new Label("Date"), dateHbox);

        ListView<String> resultView = new ListView<>();

        Button searchButton = new Button("Search");
        searchButton.setMaxWidth(150);
        searchButton.setOnAction(actionEvent -> {
            if (actWeekDays.isSelected() && actDate.isSelected()) {
                resultView.setItems(FXCollections.observableArrayList(dataSearch.search(weekDaysSelect.getValue(), dateSpin.getValue())));
            } else if (!actWeekDays.isSelected() && actDate.isSelected()) {
                resultView.setItems(FXCollections.observableArrayList(dataSearch.search(dateSpin.getValue())));
            } else if (actWeekDays.isSelected() && !actDate.isSelected()) {
                resultView.setItems(FXCollections.observableArrayList(dataSearch.search(weekDaysSelect.getValue())));
            }
        });

        VBox selectBox = new VBox(startBox, endBox, weekDaysVbox, dateVbox, searchButton);
        selectBox.setSpacing(10);

        HBox mainBox = new HBox(selectBox, resultView);
        mainBox.setSpacing(50);
        HBox.setHgrow(selectBox, Priority.ALWAYS);
        HBox.setHgrow(resultView, Priority.ALWAYS);

        Scene scene = new Scene(mainBox, 600, 400);
        stage.setTitle("Days search engine");
        stage.setScene(scene);
        stage.show();
    }
}

