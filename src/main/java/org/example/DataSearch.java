package org.example;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataSearch {
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormat format1 = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
    private final DateFormat format2 = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.ENGLISH);
    private final String[] daysOfWeek = {"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};


    private String startDate;
    private String endDate;
    private int period;
    private Calendar calendar;
    private String[] crutch;

    public DataSearch(String start, String end) {

        this.startDate = start;
        this.endDate = end;

    }

    public List<String> search(int dayOfMonth) {
        refresh();
        List<String> result = new LinkedList<>();
        for (int i = 0; i < period; i++) {
            if (calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
                result.add(format1.format(calendar.getTime())
                        + "      " +
                        format2.format(calendar.getTime()));
            }
            calendar.add(Calendar.DATE, 1);
        }
        return result;
    }

    public List<String> search(String dayOfWeek) {
        int weekDay = Arrays.asList(daysOfWeek).indexOf(dayOfWeek);
        refresh();
        List<String> result = new LinkedList<>();
        for (int i = 0; i < period; i++) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == weekDay) {
                result.add(format1.format(calendar.getTime())
                        + "      " +
                        format2.format(calendar.getTime()));
            }
            calendar.add(Calendar.DATE, 1);
        }
        return result;
    }

    public List<String> search(String dayOfWeek, int dayOfMonth) {
        int weekDay = Arrays.asList(daysOfWeek).indexOf(dayOfWeek);
        refresh();
        List<String> result = new LinkedList<>();
        for (int i = 0; i < period; i++) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == weekDay && calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
                result.add(format1.format(calendar.getTime())
                        + "      " +
                        format2.format(calendar.getTime()));
            }
            calendar.add(Calendar.DATE, 1);
        }
        return result;
    }

    private void refresh() {
        this.crutch = startDate.split("-");
        this.calendar = new GregorianCalendar(Integer.parseInt(crutch[0]),
                Integer.parseInt(crutch[1]) - 1, Integer.parseInt(crutch[2]));
        try {
            this.period = (int) ((format.parse(endDate).getTime() -
                    format.parse(startDate).getTime()) / (24 * 60 * 60 * 1000));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStartDate(String startDate) {
        refresh();
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        refresh();
        this.endDate = endDate;
    }
}
