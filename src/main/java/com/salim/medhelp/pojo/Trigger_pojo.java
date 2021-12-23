package com.salim.medhelp.pojo;

public class Trigger_pojo {
    long id;
    long initialtrig;
    long trig;
    long alarm_id;
    long rem_id;

    public Trigger_pojo() {
    }

    public Trigger_pojo(long id, long initialtrig, long trig, long alarm_id, long rem_id) {
        this.id = id;
        this.initialtrig = initialtrig;
        this.trig = trig;
        this.alarm_id = alarm_id;
        this.rem_id = rem_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getInitialtrig() {
        return initialtrig;
    }

    public void setInitialtrig(long initialtrig) {
        this.initialtrig = initialtrig;
    }

    public long getTrig() {
        return trig;
    }

    public void setTrig(long trig) {
        this.trig = trig;
    }

    public long getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(long alarm_id) {
        this.alarm_id = alarm_id;
    }

    public long getRem_id() {
        return rem_id;
    }

    public void setRem_id(long rem_id) {
        this.rem_id = rem_id;
    }
}