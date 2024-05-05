package com.example.miniproject.mode;

public class cure {
    private int cure_id;
    private String cure_name;
    private String cure_type;
    private int during_cure;
    private String cure_time_daily;
    private int cure_frequency;

    public cure(int cure_id, String cure_name, String cure_type, int during_cure, String cure_time_daily, int cure_frequency) {
        this.cure_id = cure_id;
        this.cure_name = cure_name;
        this.cure_type = cure_type;
        this.during_cure = during_cure;
        this.cure_time_daily = cure_time_daily;
        this.cure_frequency = cure_frequency;
    }

    public int getCure_id() {
        return cure_id;
    }

    public void setCure_id(int cure_id) {
        this.cure_id = cure_id;
    }

    public String getCure_name() {
        return cure_name;
    }

    public void setCure_name(String cure_name) {
        this.cure_name = cure_name;
    }

    public String getCure_type() {
        return cure_type;
    }

    public void setCure_type(String cure_type) {
        this.cure_type = cure_type;
    }

    public int getDuring_cure() {
        return during_cure;
    }

    public void setDuring_cure(int during_cure) {
        this.during_cure = during_cure;
    }

    public String getCure_time_daily() {
        return cure_time_daily;
    }

    public void setCure_time_daily(String cure_time_daily) {
        this.cure_time_daily = cure_time_daily;
    }

    public int getCure_frequency() {
        return cure_frequency;
    }

    public void setCure_frequency(int cure_frequency) {
        this.cure_frequency = cure_frequency;
    }
}
