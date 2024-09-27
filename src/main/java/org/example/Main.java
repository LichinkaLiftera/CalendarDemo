package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
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

        VBox dateBox = new VBox(5, new Label("Начальная дата"), startPicker,
                new Label("Конечная дата"), endPicker);

        //Выбор необходимых дней для выборки и их повторов
        Spinner<Integer> sunday = new Spinner<>(0, 99, 0);
        Spinner<Integer> monday = new Spinner<>(0, 99, 0);
        Spinner<Integer> tuesday = new Spinner<>(0, 99, 0);
        Spinner<Integer> wednesday = new Spinner<>(0, 99, 0);
        Spinner<Integer> thursday = new Spinner<>(0, 99, 0);
        Spinner<Integer> friday = new Spinner<>(0, 99, 0);
        Spinner<Integer> saturday = new Spinner<>(0, 99, 0);
        CheckBox actWeekDays = new CheckBox();
        HBox actLabelBox = new HBox(10, new Label("День недели"), actWeekDays);
        VBox days = new VBox(5, monday, tuesday, wednesday, thursday, friday, saturday, sunday, actLabelBox);
        days.setMaxWidth(100);
        //Выбор необходимых дней для выборки и их повторов

        //Выбор необходимой даты
        Spinner<Integer> sundayDate = new Spinner<>(0, 31, 0);
        Spinner<Integer> mondayDate = new Spinner<>(0, 31, 0);
        Spinner<Integer> tuesdayDate = new Spinner<>(0, 31, 0);
        Spinner<Integer> wednesdayDate = new Spinner<>(0, 31, 0);
        Spinner<Integer> thursdayDate = new Spinner<>(0, 31, 0);
        Spinner<Integer> fridayDate = new Spinner<>(0, 31, 0);
        Spinner<Integer> saturdayDate = new Spinner<>(0, 31, 0);
        CheckBox actDate = new CheckBox();
        HBox dateLabelBox = new HBox(5, actDate, new Label("Число"));
        VBox daysDateBox = new VBox(5, mondayDate, tuesdayDate, wednesdayDate,
                thursdayDate, fridayDate, saturdayDate, sundayDate, dateLabelBox);
        daysDateBox.setMaxWidth(100);
        //Выбор необходимой даты

        //Лэйблы
        VBox daysLabels = new VBox(13, new Label("Понедельник"), new Label("Вторник"), new Label("Среда")
                , new Label("Четверг"), new Label("Пятница"), new Label("Суббота"), new Label("Воскресенье"));
        //Лэйблы

        //Блок вью день-дата
        HBox daysBox = new HBox(5, days, daysLabels, daysDateBox);
        //Блок вью день-дата

        ListView<String> resultView = new ListView<>();

        Button searchButton = new Button("Поиск");
        searchButton.setMaxWidth(150);

        searchButton.setOnAction(actionEvent -> {
            if (actWeekDays.isSelected() && actDate.isSelected()) {
                int[] dayOfWeek = new int[]{0, sunday.getValue(), monday.getValue(), tuesday.getValue(),
                        wednesday.getValue(), thursday.getValue(), friday.getValue(), saturday.getValue()};
                int[] dayOfMonth = new int[]{0, sundayDate.getValue(), mondayDate.getValue(), tuesdayDate.getValue(),
                        wednesdayDate.getValue(), thursdayDate.getValue(), fridayDate.getValue(), saturdayDate.getValue()};
                resultView.setItems(FXCollections.observableArrayList(
                        dataSearch.search(dayOfWeek, dayOfMonth)));
            } else if (!actWeekDays.isSelected() && actDate.isSelected()) {
                int[] dayOfMonth = new int[]{sundayDate.getValue(), mondayDate.getValue(), tuesdayDate.getValue(),
                        wednesdayDate.getValue(), thursdayDate.getValue(), fridayDate.getValue(), saturdayDate.getValue()};
                resultView.setItems(FXCollections.observableArrayList(dataSearch.searchDate(dayOfMonth)));
            } else if (actWeekDays.isSelected() && !actDate.isSelected()) {
                int[] dayOfWeek = new int[]{0, sunday.getValue(), monday.getValue(), tuesday.getValue(),
                        wednesday.getValue(), thursday.getValue(), friday.getValue(), saturday.getValue()};
                resultView.setItems(FXCollections.observableArrayList(dataSearch.searchDay(dayOfWeek)));
            }
        });

        VBox selectBox = new VBox(50, dateBox, daysBox, searchButton);

        HBox mainBox = new HBox(selectBox, resultView);
        mainBox.setSpacing(50);
        HBox.setHgrow(selectBox, Priority.ALWAYS);
        HBox.setHgrow(resultView, Priority.ALWAYS);

        Scene scene = new Scene(mainBox, 700, 500);
        stage.setTitle("Days search engine");
        stage.setScene(scene);
        stage.show();
    }
}

