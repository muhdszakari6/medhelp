package com.salim.medhelp.pojo;

public class Ap_pojo {
    long id;
    String location,complain,date,time;
    double lat,lon;
    long user;
    long trig;
    String message;

    public Ap_pojo(long id, String location, String complain, String date, String time, double lat, double lon, long user, long trig, String message) {
        this.id = id;
        this.location = location;
        this.complain = complain;
        this.date = date;
        this.time = time;
        this.lat = lat;
        this.lon = lon;
        this.user = user;
        this.trig = trig;
        this.message = message;
    }

    public long getTrig() {
        return trig;
    }

    public void setTrig(long trig) {
        this.trig = trig;
    }



    public Ap_pojo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
