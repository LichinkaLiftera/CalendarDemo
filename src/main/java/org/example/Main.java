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

        //Выбор начальной даты
        DatePicker startPicker = new DatePicker(LocalDate.now());
        startPicker.setMaxWidth(150);
        startPicker.setShowWeekNumbers(true);
        startPicker.setOnAction(actionEvent -> {
            dataSearch.setStartDate(startPicker.getValue().toString());
        });
        //Выбор начальной даты

        //Выбор конечной даты
        DatePicker endPicker = new DatePicker(LocalDate.now());
        endPicker.setMaxWidth(150);
        endPicker.setShowWeekNumbers(true);
        endPicker.setOnAction(actionEvent -> {
            dataSearch.setEndDate(endPicker.getValue().toString());
        });
        //Выбор конечной даты

        VBox dateBox = new VBox(5, new Label("Start date"), startPicker, new Label("End date"), endPicker);

        ObservableList<String> weekDays = FXCollections.observableArrayList
                ("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        Spinner<String> weekDaysSelect = new Spinner<>(weekDays);

//        HBox weekDaysHbox = new HBox(10, weekDaysSelect, actWeekDays);
//        VBox weekDaysVbox = new VBox(weekDaysHbox, new Label("Day of week"));

        Spinner<Integer> dateSpin = new Spinner<>(1, 31, 1);
//        CheckBox actDate = new CheckBox();
//        HBox dateHbox = new HBox(10, dateSpin, actDate);
//        VBox dateVbox = new VBox(new Label("Date"), dateHbox);


        //Выбор необходимых дней для выборки и их повторов
        Spinner<Integer> monday = new Spinner<>(0, 99, 0);
        Spinner<Integer> tuesday = new Spinner<>(0, 99, 0);
        Spinner<Integer> wednesday = new Spinner<>(0, 99, 0);
        Spinner<Integer> thursday = new Spinner<>(0, 99, 0);
        Spinner<Integer> friday = new Spinner<>(0, 99, 0);
        Spinner<Integer> saturday = new Spinner<>(0, 99, 0);
        Spinner<Integer> sunday = new Spinner<>(0, 99, 0);
        CheckBox actWeekDays = new CheckBox();
        HBox actLabelBox = new HBox(10, new Label("Days of Week"), actWeekDays);
        VBox days = new VBox(5, monday, tuesday, wednesday, thursday, friday, saturday, sunday,actLabelBox);
        days.setMaxWidth(100);
        //Выбор необходимых дней для выборки и их повторов

        //Выбор необходимой даты
        Spinner<Integer> mondayDate = new Spinner<>(1, 31, 1);
        Spinner<Integer> tuesdayDate = new Spinner<>(1, 31, 1);
        Spinner<Integer> wednesdayDate = new Spinner<>(1, 31, 1);
        Spinner<Integer> thursdayDate = new Spinner<>(1, 31, 1);
        Spinner<Integer> fridayDate = new Spinner<>(1, 31, 1);
        Spinner<Integer> saturdayDate = new Spinner<>(1, 31, 1);
        Spinner<Integer> sundayDate = new Spinner<>(1, 31, 1);
        CheckBox actDate = new CheckBox();
        HBox dateLabelBox = new HBox(5,actDate, new Label("Date"));
        VBox daysDateBox = new VBox(5, mondayDate, tuesdayDate, wednesdayDate, thursdayDate, fridayDate, saturdayDate, sundayDate,dateLabelBox);
        daysDateBox.setMaxWidth(100);
        //Выбор необходимой даты

        //Лэйблы
        VBox daysLabels = new VBox(13, new Label("Monday"), new Label("Tuesday"), new Label("Wednesday")
                , new Label("Thursday"), new Label("Friday"), new Label("Saturday"), new Label("Sunday"));
        //Лэйблы

        HBox daysBox = new HBox(5, days, daysLabels, daysDateBox);


        ListView <String> resultView = new ListView<>();

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

        VBox selectBox = new VBox(50, dateBox, daysBox, searchButton);

        HBox mainBox = new HBox(selectBox, resultView);
        mainBox.setSpacing(50);
        HBox.setHgrow(selectBox, Priority.ALWAYS);
        HBox.setHgrow(resultView, Priority.ALWAYS);

        Scene scene = new Scene(mainBox, 600, 500);
        stage.setTitle("Days search engine");
        stage.setScene(scene);
        stage.show();
    }
}

