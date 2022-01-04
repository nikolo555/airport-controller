/**
 * @author Nikolas Papadopoulos
 * created on 25/03/2020
 * ECE NTUA
 */
package controllers;

public class GateView {
    private String parkingSeatID;
    private String status;
    private String category;
    private String flightID;
    private String departureTime;

    public GateView(String parkingSeatID, String status, String category, String flightID, String departureTime) {
        this.parkingSeatID = parkingSeatID;
        this.status = status;
        this.category = category;
        this.flightID = flightID;
        this.departureTime = departureTime;
    }



    public String getParkingSeatID() {
        return parkingSeatID;
    }

    public void setParkingSeatID(String parkingSeatID) {
        this.parkingSeatID = parkingSeatID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
}
