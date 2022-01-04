/**
 * @author Nikolas Papadopoulos
 * created on 25/03/2020
 * ECE NTUA
 */
package controllers;

public class ParkingSeat {
    private String parkingSeatID;
    private String status;
    private int category;
    private int maxParkingTime;
    private int cost;


    // Getters & Setters

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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getMaxParkingTime() {
        return maxParkingTime;
    }

    public void setMaxParkingTime(int maxParkingTime) {
        this.maxParkingTime = maxParkingTime;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
