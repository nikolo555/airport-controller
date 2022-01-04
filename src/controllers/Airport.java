package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Airport {
    private HashMap<Integer, List<ParkingSeat>> parking;
    private List<Flight> flights;
    private double totalProfit;


    // Methods

    //add element to List of Flights
    public void addFlight(Flight flight) {
        if (this.flights == null){
            List<Flight> tempFlightList = new ArrayList<>();
            tempFlightList.add(flight);
            this.setFlights(tempFlightList);
        }
        else this.flights.add(flight);
    }

    //checks for departed flights  and removes them
    public void checkforDepartedFlights(){
        if (this.flights == null){
            System.out.println("There is no flight to depart");
        }
        else {
            //System.out.println("number of flights" + flights.size());
            flights.removeIf(f -> f.getStatus().equals("departed"));
        }
    }

    //count flights in landing status
    public int countParkedFlights(){
        int counter = 0;
        if (this.flights == null){
            System.out.println("There is no flight to be parked");
        }
        else {
            for(Flight f : flights){
                if(f.getStatus().equals("parked")) counter++;
            }
        }
        return counter;
    }

    //count available parking places
    public int countAvailableParkingSpaces(){
        int counter = 0, sumofParkingPlaces = 0;
        List<ParkingSeat> aList;

        for(int i = 1; i <= parking.size(); i++){
            if(parking.get(i) != null){
                sumofParkingPlaces += parking.get(i).size();
            }
        }

        for(Flight f : flights){
            if(f.getStatus().equals("parked")) counter++;
        }

        //System.out.println(sumofParkingPlaces-counter);

        return sumofParkingPlaces - counter;

    }


    // Getters & Setters

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public HashMap<Integer, List<ParkingSeat>> getParking() {
        return parking;
    }

    public void setParking(HashMap<Integer, List<ParkingSeat>> parking) {
        this.parking = parking;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }


}

