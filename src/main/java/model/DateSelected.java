package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

public class DateSelected {
    private YearMonth yearMonth = YearMonth.now();
    private int indentation;
    private Map<Integer, LocalDate> mapOfResults = new HashMap<>();
    private Map<String, LocalDate> mapOfBtnDay = new HashMap<>();

    public DateSelected() {
        setIndentation();
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void increaseMonth() {
        this.yearMonth = this.yearMonth.plusMonths(1);
        setIndentation();
    }

    public void decreaseMonth() {
        this.yearMonth = this.yearMonth.minusMonths(1);
        setIndentation();
    }

    public void setIndentation() {
        LocalDate localDate2 = LocalDate.of(this.yearMonth.getYear(), this.yearMonth.getMonth(), 1);
        DayOfWeek dayOfWeek = localDate2.getDayOfWeek();
        this.indentation = dayOfWeek.getValue();
    }

    public int getIndentation() {
        return indentation;
    }

    public Map<Integer, LocalDate> getMapOfResults() {
        return mapOfResults;
    }

    public Map<String, LocalDate> getMapOfBtnDay() {
        return mapOfBtnDay;
    }

    public void clearMapOfBtnDay() {
        mapOfBtnDay.clear();
    }

    public void addMapOfBtnDay(Map<String, LocalDate> mapOfBtnDay) {
        this.mapOfBtnDay = mapOfBtnDay;
    }
}
