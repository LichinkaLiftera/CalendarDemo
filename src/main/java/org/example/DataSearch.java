package org.example;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataSearch {
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
    private final DateFormat format2 = new SimpleDateFormat("EEEE dd MMMM yyyy");


    private String startDate;
    private String endDate;
    private int period;
    private Calendar calendar;

    public DataSearch(String start, String end) {

        this.startDate = start;
        this.endDate = end;

    }

    public List<String> searchDate(int[] dayOfMonth) {
        refresh();

        List<String> result = new LinkedList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < period; i++) {
            Arrays.stream(dayOfMonth).forEach(day ->
            {
                if (day != 0 && calendar.get(Calendar.DAY_OF_MONTH) == day) {
                    result.add(format1.format(calendar.getTime())
                            + "      " +
                            format2.format(calendar.getTime()));
                }
            });
            calendar.add(Calendar.DATE, 1);
        }
        System.out.println(System.currentTimeMillis()-start);
        return result;


    }


    public List<String> searchDay(int[] dayOfWeek) {
        refresh();
        List<String> result = new LinkedList<>();
        for (int i = 0; i < period; i++) {
            for (int y = 1; y < dayOfWeek.length; y++) {
                if (dayOfWeek[y] != 0 && calendar.get(Calendar.DAY_OF_WEEK) == y) {
                    for (int x = 0; x < dayOfWeek[y]; x++) {
                        result.add(format1.format(calendar.getTime())
                                + "      " +
                                format2.format(calendar.getTime()));
                    }//Oh no! Anyway
                }

            }
            calendar.add(Calendar.DATE, 1);
        }
        return result;
    }


    public List<String> search(int[] dayOfWeek, int[] dayOfMonth) {
        refresh();
        List<String> result = new LinkedList<>();
        for (int i = 0; i < period; i++) {
            for (int x = 0; x < dayOfWeek.length; x++) {
                if (dayOfWeek[x] != 0) {
                    for (int k : dayOfMonth) {
                        if (k != 0) {
                            if (calendar.get(Calendar.DAY_OF_WEEK) == x &&
                                    calendar.get(Calendar.DAY_OF_MONTH) == k) {
                                for (int y = 0; y < dayOfWeek[x]; y++) {
                                    result.add(format1.format(calendar.getTime())
                                            + "      " +
                                            format2.format(calendar.getTime()));

                                } //No!God!Please!No! Anyway
                            }
                        }
                    }
                }
            }
            calendar.add(Calendar.DATE, 1);
        }
        return result;
    } //Don't reveal!

    private void refresh() {
        String[] crutch;
        crutch = startDate.split("-");
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
