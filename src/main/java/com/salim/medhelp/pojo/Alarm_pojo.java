package com.salim.medhelp.pojo;

public class Alarm_pojo {
    long id;
    String time,date;
    long trig;
    long user_id;
    long rem_id;
    int dose;
    String freq;


    String message;
    public Alarm_pojo() {

    }

    public Alarm_pojo(long id, String time, String date, long trig, long user_id, long rem_id,int dose,String message) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.trig = trig;
        this.user_id = user_id;
        this.rem_id = rem_id;
        this.dose = dose;
        this.message = message;
    }


    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTrig() {
        return trig;
    }

    public void setTrig(long trig) {
        this.trig = trig;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getRem_id() {
        return rem_id;
    }

    public void setRem_id(long rem_id) {
        this.rem_id = rem_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

}