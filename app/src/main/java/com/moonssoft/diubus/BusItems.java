package com.moonssoft.diubus;

/**
 * Created by MoonS on 27-01-17.
 */

public class BusItems {
    private String busName;
    private String busTime;
    private String busFrom;
    private String busTo;
    private String toFrom;


    public BusItems() {

    }

    public BusItems(String busName, String busTime, String busFrom, String busTo, String toFrom) {
        this.busName = busName;
        this.busTime = busTime;
        this.busFrom = busFrom;
        this.busTo = busTo;
        this.toFrom = toFrom;
    }


    public String getBusName() {
        return busName;
    }

    public String getBusTime() {
        return busTime;
    }

    public String getBusFrom() {
        return busFrom;
    }

    public String getBusTo() {
        return busTo;
    }

    public String getToFrom() { return toFrom; }
}
