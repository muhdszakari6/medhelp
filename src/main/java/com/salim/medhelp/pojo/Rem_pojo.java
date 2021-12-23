package com.salim.medhelp.pojo;

public class Rem_pojo {
    long id;
    String tabletname,tablettreatment;
    long userid;
    String Message;

    public Rem_pojo() {
    }

    public Rem_pojo(long id, String tabletname, String tablettreatment, long userid, String message) {
        this.id = id;
        this.tabletname = tabletname;
        this.tablettreatment = tablettreatment;
        this.userid = userid;
        Message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTabletname() {
        return tabletname;
    }

    public void setTabletname(String tabletname) {
        this.tabletname = tabletname;
    }

    public String getTablettreatment() {
        return tablettreatment;
    }

    public void setTablettreatment(String tablettreatment) {
        this.tablettreatment = tablettreatment;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
