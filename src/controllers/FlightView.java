/**
 * @author Nikolas Papadopoulos
 * created on 25/03/2020
 * ECE NTUA
 */
package controllers;

public class FlightView {
    private String flightID;
    private String city;
    private String status;
    private String flightType;
    private String planeType;
    private String departureTime;


    //Constructor

    public FlightView(String flightID, String city, String status, String flightType, String planeType, String departureTime) {
        this.flightID = flightID;
        this.city = city;
        this.status = status;
        this.flightType = flightType;
        this.planeType = planeType;
        this.departureTime = departureTime;
    }


    // Getters & Setters


    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getPlaneType() {
        return planeType;
    }

    public void setPlaneType(String planeType) {
        this.planeType = planeType;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
}