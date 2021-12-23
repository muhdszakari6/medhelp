package com.salim.medhelp.pojo;

public class Pres_pojo {

    long id,userid;
    String name,treatment,dose,firstdosetime,firstdosedate;
    int number;
    int status;
    byte[] image;
    String message;
    long trig;


    public Pres_pojo() {
    }

    public Pres_pojo(long id, long userid, String name, String treatment, int number, String dose, String firstdosetime, String firstdosedate, int status, byte[] image,long trig, String message) {
        this.id = id;
        this.userid = userid;
        this.name = name;
        this.treatment = treatment;
        this.number = number;
        this.dose = dose;
        this.firstdosetime = firstdosetime;
        this.firstdosedate = firstdosedate;
        this.status = status;
        this.image = image;
        this.trig = trig;
        this.message = message;
    }

    public long getId() {
        return id;
    }
    public long getTrig() {
        return trig;
    }

    public void setTrig(long trig) {
        this.trig = trig;
    }


    public void setId(long id) {
        this.id = id;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getFirstdosetime() {
        return firstdosetime;
    }

    public void setFirstdosetime(String firstdosetime) {
        this.firstdosetime = firstdosetime;
    }

    public String getFirstdosedate() {
        return firstdosedate;
    }

    public void setFirstdosedate(String firstdosedate) {
        this.firstdosedate = firstdosedate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

