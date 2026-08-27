package com.example.smartbus.models;

public class Stop {
    private String name;
    private String arrivalTime;
    private boolean isPassed;

    public Stop(String name, String arrivalTime, boolean isPassed) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.isPassed = isPassed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }
}
