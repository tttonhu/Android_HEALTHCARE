package com.example.miniproject.mode;

public class cure_daily {
    public cure_daily(int id_cure_daily, int cure_id, String cure_date, boolean cure_daily_condition) {
        this.id_cure_daily = id_cure_daily;
        this.cure_id = cure_id;
        this.cure_date = cure_date;
        this.cure_daily_condition = cure_daily_condition;
    }

    private int id_cure_daily;
    private int cure_id;
    private String cure_date;
    private boolean cure_daily_condition;

    public int getId_cure_daily() {
        return id_cure_daily;
    }

    public void setId_cure_daily(int id_cure_daily) {
        this.id_cure_daily = id_cure_daily;
    }

    public int getCure_id() {
        return cure_id;
    }

    public void setCure_id(int cure_id) {
        this.cure_id = cure_id;
    }

    public String getCure_date() {
        return cure_date;
    }

    public void setCure_date(String cure_date) {
        this.cure_date = cure_date;
    }

    public boolean isCure_daily_condition() {
        return cure_daily_condition;
    }

    public void setCure_daily_condition(boolean cure_daily_condition) {
        this.cure_daily_condition = cure_daily_condition;
    }
}
