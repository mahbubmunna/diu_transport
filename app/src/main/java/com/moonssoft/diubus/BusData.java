package com.moonssoft.diubus;

/**
 * Created by MoonS on 25-01-17.
 */

public class BusData {
    private String BusName;
    private String BusTime;

    private int mImageResource;


    public BusData(String mBusName, String mBusTime, int mImageResource) {
        this.BusName = BusName;
        this.BusTime = BusTime;
        this.mImageResource = mImageResource;
    }

    public BusData() {
    }

    public BusData(String busName, String busTime) {
        BusName = busName;
        BusTime = busTime;
    }

    public String getBusName() {
        return BusName;
    }

    public void setBusName(String mBusName) {
        this.BusName = mBusName;
    }

    public String getBusTime() {
        return BusTime;
    }

    public void setBusTime(String mBusTime) {
        this.BusTime = mBusTime;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }
}
