package controllers;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/** Represents a Flight
 *
 * @author Nikolas Papadopoulos
 *
 * @version 1.0
 */
public class Flight {


    private String flightID;
    private String city;
    private String parkingID;
    private String services;
    private String status;
    private int flightType;
    private int planeType;
    private int departureTime;
    private int parkingTime;
    /**Represents flight's delay
     * 0 = delayed
     * 1 = 10-20 mins earlier
     * 2 = 25 mins earlier
     * 3 = no delay
     */
    private int delay;


    /**This method activates a timer. When this timer is activated,
     * it means that the flight is in 'landing' status
     * and we count until the time to park. ParkingTime
     * depends on planeType:
     *  - Jet = 2 mins
     *  - Turboprop = 4 mins
     *  - Single Motor = 6 mins
     *
     */
    public void setParkingTimer() {
        //new timer for changing status from "landing" to "parking"
        Timer parkingTimer = new Timer();

        TimerTask parkingTimerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("setParkingTimer is activated!");
                status = "parked";
            }
        };

        parkingTimer.schedule(parkingTimerTask, parkingTime * 5000);
    }



    /**Gets the flight's parkingID
     * @return a String representing flight's parkingID
     */
    public String getParkingID() {
        return parkingID;
    }

    /**Sets the flight's parkingID
     * @param parkingID a String representing flight's parkingID
     */
    public void setParkingID(String parkingID) {
        this.parkingID = parkingID;
    }


    /**Gets the flight's ID
     * @return a String representing flight's ID
     */
    public String getFlightID() {
        return flightID;
    }

    /**Sets the flight's ID
     * @param flightID a String representing flight's ID
     */
    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    /**Gets the flight's city
     * @return a String representing flight's city (both origin and destination)
     */
    public String getCity() {
        return city;
    }

    /**Sets the flight's city
     * @param city a String representing flight's city (both origin and destination)
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**Gets the flight's type (1 = Single Motor, 2 = Turboprop, 3 = Jet)
     * @return an Integer representing flight type
     */
    public int getFlightType() {
        return flightType;
    }

    /**Sets the flight's type (1 = Single Motor, 2 = Turboprop, 3 = Jet)
     * and parking time at the same time, because they are correlated
     * @param flightType salary to set (in cents)
     */
    public void setFlightType(int flightType) {
        this.flightType = flightType;
        if(flightType == 1) this.parkingTime = 6;        //single motor = 6 mins
        else if(flightType == 2) this.parkingTime = 4;   //turbo = 4 mins
        else this.parkingTime = 2;                       //jet = 2 mins
    }

    /**Gets the flight's plane type (1 = Passenger, 2 = Freight, 3 = Private)
     * @return an Integer representing flight's plane type
     */
    public int getPlaneType() {
        return planeType;
    }

    /**Sets the flight's plane type (1 = Passenger, 2 = Freight, 3 = Private)
     * @param planeType an Integer representing flight's plane type
     */
    public void setPlaneType(int planeType) {
        this.planeType = planeType;
    }

    /**Gets the flight's status ("parked", "holding", "landing", "departed", "ignored", "rejected")
     * @return a String representing flight's status
     */
    public String getStatus() {
        return status;
    }

    /**Sets the flight's status ("parked", "holding", "landing", "departed", "ignored", "rejected")
     * @param status a String representing flight's status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**Gets the flight's departure time
     * @return an Integer representing flight's departure time in minutes
     */
    public int getDepartureTime() {
        return departureTime;
    }

    /**Sets the flight's departure time
     * @param departureTime an Integer representing flight's departure time in minutes
     */
    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    /**Gets the flight's service ("Refueling", "Cleaning", "Passenger Transportation", "Load / Unload")
     * @return a String representing flight's service
     */
    public String getServices() {
        return services;
    }

    /**Sets the flight's service ("Refueling", "Cleaning", "Passenger Transportation", "Load / Unload")
     * @param services a String representing flight's service
     */
    public void setServices(String services) {
        this.services = services;
    }

    /**Gets the flight's delay
     * @return an Integer representing flight's delay
     */
    public int getDelay() {
        return delay;
    }

    /**Sets the flight's delay
     * @param delay an Integer representing flight's delay
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }
}