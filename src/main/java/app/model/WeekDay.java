package app.model;


public enum WeekDay {

    SUNDAY(0), MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6);

    public final int id;

    WeekDay(int id) {
        this.id = id;
    }
}
