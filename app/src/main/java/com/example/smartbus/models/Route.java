package com.example.smartbus.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Route implements Serializable {
    private String busNumber;
    private String routeName;
    private ArrayList<String> stopsList;
    private String status;
    
    // Detailed parameters added in Module 5
    private String driverName;
    private String driverContact;
    private int capacity;
    private int occupancy;
    private String startingPoint;
    private String destination;
    private String nextStop;
    private String eta;

    public Route(String busNumber, String routeName, ArrayList<String> stopsList, String status,
                 String driverName, String driverContact, int capacity, int occupancy,
                 String startingPoint, String destination, String nextStop, String eta) {
        this.busNumber = busNumber;
        this.routeName = routeName;
        this.stopsList = stopsList;
        this.status = status;
        this.driverName = driverName;
        this.driverContact = driverContact;
        this.capacity = capacity;
        this.occupancy = occupancy;
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.nextStop = nextStop;
        this.eta = eta;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public ArrayList<String> getStopsList() {
        return stopsList;
    }

    public void setStopsList(ArrayList<String> stopsList) {
        this.stopsList = stopsList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverContact() {
        return driverContact;
    }

    public void setDriverContact(String driverContact) {
        this.driverContact = driverContact;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getNextStop() {
        return nextStop;
    }

    public void setNextStop(String nextStop) {
        this.nextStop = nextStop;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }
}
