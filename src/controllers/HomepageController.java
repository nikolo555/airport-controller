/**
 * @author Nikolas Papadopoulos
 * created on 25/03/2020
 * ECE NTUA
 */
package controllers;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class HomepageController<timer> implements Initializable{

    private Airport airport;

    HashMap<String, Integer> prefixtoCategory = new HashMap<>();
    HashMap<Integer, String> inttoCategory = new HashMap<>();
    HashMap<Integer, String> inttoFlighType = new HashMap<>();
    HashMap<Integer, String> inttoPlaneType = new HashMap<>();
    HashMap<String, Double> servicetoCost = new HashMap<>();
    HashMap<Integer, Double> delaytoCost = new HashMap<>();

    private String AirportScenarioID,  SetupScenarioID;

    @FXML
    private TextArea textAreaConsole;

    @FXML
    private Text parkedFlights, availableParkingSpaces, departingFlights, totalProfit, timeSinceStart;

    @FXML
    private AnchorPane graphicGUI;

    GridPane graphicGrid;

    @FXML
    private MenuItem menuGates;

    @FXML
    private TableView<GateView> gateView;

    @FXML
    private TableView<GateView> flightView;

    private ControlTimer controlTimer = new ControlTimer();

    /*
     * Define an integer to control desirable order of button clicking:
     * LoadButton => StartButton => SubmitButton is the right order.
     * LoadButton => StartButton => Menu/Details Buttons is also right order.
     * So, buttonSerialization is initialized to 0 and when loadButton is clicked
     * is set to 1, then when StartButton is clicked is set to 2.
     */
    private int buttonSerialization = 0;

    // create instance of Random class
    Random rand = new Random();



    public void menuGatesClicked(ActionEvent actionEvent) throws IOException {

        if(buttonSerialization == 2){

            //Create ObservableList to pass as an argument to initData()
            //for initializing GateView

            ObservableList<GateView> gates = FXCollections.observableArrayList();

            for(int i = 1; i <= 7; i++){
                List<ParkingSeat> aList;
                aList = airport.getParking().get(i);
                for (ParkingSeat seat : aList) {
                    String fID = "", depTime = "";

                    int categoryNumber = prefixtoCategory.get(seat.getParkingSeatID().substring(0,1));

                    //find which flight corresponds to this parking seat
                    for(Flight f : airport.getFlights()){
                        if(f.getParkingID().equals(seat.getParkingSeatID())){
                            fID = f.getFlightID();
                            depTime = Integer.toString(f.getDepartureTime());
                        }
                    }

                    gates.add(new GateView(seat.getParkingSeatID(),seat.getStatus(), inttoCategory.get(categoryNumber), fID, depTime));
                }
            }


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../resources/gateView.fxml"));
            Parent root = loader.load();

            Scene tableViewScene = new Scene(root);

            //access the controller and call a method
            GateViewController controller = loader.getController();


            controller.initData(gates);

            //This line gets the Stage information
            Stage stage = new Stage();
            stage.setTitle("Gates Table");
            stage.setScene(tableViewScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(menuGates.getParentPopup().getScene().getWindow());

            stage.show();

        }
        else System.out.println("\n\nPlease, load a scenario first\n\n");

    }

    public void menuFlightsClicked(ActionEvent actionEvent) throws IOException {

        if(buttonSerialization == 2){

            //Create ObservableList to pass as an argument to initData()
            //for initializing FlightView

            ObservableList<FlightView> flights = FXCollections.observableArrayList();

            for(Flight f : airport.getFlights()){

                String flightType = inttoFlighType.get(f.getFlightType());
                String planeType = inttoPlaneType.get(f.getPlaneType());

                flights.add(new FlightView(f.getFlightID(), f.getCity(), flightType, planeType, f.getStatus(), Integer.toString(f.getDepartureTime())));
            }


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../resources/flightView.fxml"));
            Parent root = loader.load();

            Scene tableViewScene = new Scene(root);

            //access the controller and call a method
            FlightViewController controller = loader.getController();


            controller.initData(flights);

            //This line gets the Stage information
            Stage stage = new Stage();
            stage.setTitle("Flights Table");
            stage.setScene(tableViewScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(menuGates.getParentPopup().getScene().getWindow());

            stage.show();

        }
        else System.out.println("\n\nPlease, load a scenario first\n\n");

    }

    public void menuDelayedFlightsClicked(ActionEvent actionEvent) throws IOException {

        if(buttonSerialization == 2){

            //Create ObservableList to pass as an argument to initData()
            //for initializing FlightView

            ObservableList<FlightView> flights = FXCollections.observableArrayList();

            for(Flight f : airport.getFlights()){

                if(f.getDelay() == 0){
                    String flightType = inttoFlighType.get(f.getFlightType());
                    String planeType = inttoPlaneType.get(f.getPlaneType());

                    flights.add(new FlightView(f.getFlightID(), f.getCity(), flightType, planeType, f.getStatus(), Integer.toString(f.getDepartureTime())));
                }
            }


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../resources/flightView.fxml"));
            Parent root = loader.load();

            Scene tableViewScene = new Scene(root);

            //access the controller and call a method
            FlightViewController controller = loader.getController();


            controller.initData(flights);

            //This line gets the Stage information
            Stage stage = new Stage();
            stage.setTitle("Delayed Flights Table");
            stage.setScene(tableViewScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(menuGates.getParentPopup().getScene().getWindow());

            stage.show();

        }
        else System.out.println("\n\nPlease, load a scenario first\n\n");

    }

    public void menuHoldingFlightsClicked(ActionEvent actionEvent) throws IOException {

        if(buttonSerialization == 2){

            //Create ObservableList to pass as an argument to initData()
            //for initializing FlightView

            ObservableList<FlightView> flights = FXCollections.observableArrayList();

            for(Flight f : airport.getFlights()){

                if (f.getStatus().equals("holding")){
                    String flightType = inttoFlighType.get(f.getFlightType());
                    String planeType = inttoPlaneType.get(f.getPlaneType());

                    flights.add(new FlightView(f.getFlightID(), f.getCity(), flightType, planeType, f.getStatus(), Integer.toString(f.getDepartureTime())));
                }


            }


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../resources/flightView.fxml"));
            Parent root = loader.load();

            Scene tableViewScene = new Scene(root);

            //access the controller and call a method
            FlightViewController controller = loader.getController();


            controller.initData(flights);

            //This line gets the Stage information
            Stage stage = new Stage();
            stage.setTitle("Holding Flights Table");
            stage.setScene(tableViewScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(menuGates.getParentPopup().getScene().getWindow());

            stage.show();

        }
        else System.out.println("\n\nPlease, load a scenario first\n\n");
    }

    public void menuNextDeparturesClicked(ActionEvent actionEvent) throws IOException {

        if(buttonSerialization == 2){
            //Create ObservableList to pass as an argument to initData()
            //for initializing FlightView

            ObservableList<FlightView> flights = FXCollections.observableArrayList();

            for(Flight f : airport.getFlights()){

                if (f.getDepartureTime() <= 10){
                    String flightType = inttoFlighType.get(f.getFlightType());
                    String planeType = inttoPlaneType.get(f.getPlaneType());

                    flights.add(new FlightView(f.getFlightID(), f.getCity(), flightType, planeType, f.getStatus(), Integer.toString(f.getDepartureTime())));
                }


            }


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../resources/flightView.fxml"));
            Parent root = loader.load();

            Scene tableViewScene = new Scene(root);

            //access the controller and call a method
            FlightViewController controller = loader.getController();


            controller.initData(flights);

            //This line gets the Stage information
            Stage stage = new Stage();
            stage.setTitle("Next Departures Table");
            stage.setScene(tableViewScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(menuGates.getParentPopup().getScene().getWindow());

            stage.show();
        }
        else System.out.println("\n\nPlease, load a scenario first\n\n");
    }


    public class Console extends OutputStream {
        private TextArea console;

        public Console(TextArea console) {
            this.console = console;
            this.console.setEditable(false); //make log screen read-only
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }

        public void write(int b) throws IOException {
            appendText(String.valueOf((char)b));
        }
    }

    //=============================================================
    //======================= TIMERS ==============================
    //=============================================================

    //Set general Timer for Application
    private class myTimer extends AnimationTimer {

        long startTime;

        @Override
        public void start() {
            startTime = System.currentTimeMillis();
            super.start();
        }

        @Override
        public void stop() {
            super.stop();
        }

        @Override
        public void handle(long timestamp) {
            //in the app every 5 sec is 1min=60sec, so every real sec is: 60/5 = 12sec in app-time
            long app_millis = (System.currentTimeMillis() - startTime)*12;

            timeSinceStart.setText(String.format("%02d hour(s) : %02d minutes",
                    TimeUnit.MILLISECONDS.toHours(app_millis),
                    TimeUnit.MILLISECONDS.toMinutes(app_millis) - TimeUnit.MILLISECONDS.toHours(app_millis) * 60));
        }
    }

    //Set timer for general control of flights
    public class ControlTimer{
        Timer timer = new Timer();


        public void cancelControlTimer(){
            timer.cancel();
        }

        public void startControlTimer(){
            timer.scheduleAtFixedRate(new controlTimerTask(), 1000, 5000);
        }

        class controlTimerTask extends TimerTask{
            @Override
            public void run() {

                    int currentFights = 0;

                    for(Flight f : airport.getFlights()){
                        if(  ! ( f.getStatus().equals("departed") || f.getStatus().equals("rejected") )){
                            currentFights++;
                        }

                    }

                    System.out.println("Total number of flights right now: " + currentFights);

                    reduceDepartureTime();

                    parkedFlights.setText(Integer.toString(airport.countParkedFlights()));

                    availableParkingSpaces.setText(Integer.toString(airport.countAvailableParkingSpaces()));

                    int counter = 0, temp;
                    for(Flight f : airport.getFlights()){
                        if (f.getDepartureTime() <=10){
                            counter++;
                        }
                    }
                    departingFlights.setText(Integer.toString(counter));


                    double cost, percentage;

                    for(Flight f : airport.getFlights()){
                        if(f.getStatus().equals("departed")){
                            temp = prefixtoCategory.get(f.getParkingID().substring(0,1));
                            percentage = servicetoCost.get(f.getServices()) + delaytoCost.get(f.getDelay()); //cost of services and delay discount/penalty
                            cost = airport.getTotalProfit() + (1.0 + percentage) * airport.getParking().get(temp).get(0).getCost();
                            airport.setTotalProfit(cost);
                        }
                    }
                    totalProfit.setText(Double.toString(airport.getTotalProfit()));

                    airport.getFlights().removeIf(flight -> flight.getStatus().equals("departed"));

                    checkforHolding();

            }
        }


        //Methods for controlTimer

        private void checkforHolding() {
            for(Flight f : airport.getFlights()) {
                if (f.getStatus().equals("holding")) {
                    checkParkingAvailabilityAfter(f);
                }
            }
        }

        private void reduceDepartureTime(){
            for(Flight f : airport.getFlights()){
                if(!f.getStatus().equals("holding")){
                    int departureTime = f.getDepartureTime();
                    if(departureTime == 0){
                        f.setStatus("departed");

                        //change status of corresponding parking place
                        int parkingCategory = prefixtoCategory.get(f.getParkingID().substring(0,1));
                        int parkingNumber = Integer.parseInt(f.getParkingID().substring(1,2));
                        double cost = airport.getTotalProfit() + airport.getParking().get(parkingCategory).get(0).getCost();
                        airport.getParking().get(parkingCategory).get(parkingNumber-1).setStatus("free");
                    }
                    else {
                        departureTime--;
                        f.setDepartureTime(departureTime);
                    }
                }
            }
        }


    }


    Timeline gridGuiTimer = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            //============= graphicGrid ================

            graphicGUI.getChildren().clear();
            graphicGrid = new GridPane();

            String available;

            graphicGrid.setPadding(new Insets(2, 2, 2, 2));

            //Setting the vertical and horizontal gaps between the columns
            graphicGrid.setVgap(5);
            graphicGrid.setHgap(5);

            graphicGrid.setAlignment(Pos.CENTER_LEFT);

            //Set Column Constraints
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(14);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(16);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(14);
            ColumnConstraints col4 = new ColumnConstraints();
            col4.setPercentWidth(14);
            ColumnConstraints col5 = new ColumnConstraints();
            col5.setPercentWidth(14);
            ColumnConstraints col6 = new ColumnConstraints();
            col6.setPercentWidth(14);
            ColumnConstraints col7 = new ColumnConstraints();
            col7.setPercentWidth(14);
            graphicGrid.getColumnConstraints().addAll(col1, col2, col3, col4, col5, col6, col7);

            //Set Row Constraints
            int maxRow = 0;
            for(int i = 0; i < 7; i++) {
                if (airport.getParking().get(i + 1) != null) {
                    if( airport.getParking().get(i+1).size() > maxRow){
                        maxRow = airport.getParking().get(i+1).size();
                    }
                }
            }

            for(int i = 0; i <= maxRow; i++){
                RowConstraints r = new RowConstraints();
                r.setPercentHeight(graphicGUI.getHeight() / maxRow);
                graphicGrid.getRowConstraints().add(r);

            }

            //Set 1st Line with the names of parking categories
            Label label = new Label("Gate");
            label.setStyle("-fx-font-weight: bold;");
            graphicGrid.add(label, 0, 0);
            GridPane.setHalignment(label, HPos.CENTER);

            label = new Label("PassGate");
            label.setStyle("-fx-font-weight: bold;");
            graphicGrid.add(label, 1, 0);
            GridPane.setHalignment(label, HPos.CENTER);

            label = new Label("Zone A");
            label.setStyle("-fx-font-weight: bold;");
            graphicGrid.add(label, 2, 0);
            GridPane.setHalignment(label, HPos.CENTER);

            label = new Label("Zone B");
            label.setStyle("-fx-font-weight: bold;");
            graphicGrid.add(label, 3, 0);
            GridPane.setHalignment(label, HPos.CENTER);

            label = new Label("Zone C");
            label.setStyle("-fx-font-weight: bold;");
            graphicGrid.add(label, 4, 0);
            GridPane.setHalignment(label, HPos.CENTER);

            label = new Label("General");
            label.setStyle("-fx-font-weight: bold;");
            graphicGrid.add(label, 5, 0);
            GridPane.setHalignment(label, HPos.CENTER);

            label = new Label("Long");
            label.setStyle("-fx-font-weight: bold;");
            graphicGrid.add(label, 6, 0);
            GridPane.setHalignment(label, HPos.CENTER);

            for(int i = 0; i < 7; i++) {

                if( airport.getParking().get(i+1) != null){

                    int row = airport.getParking().get(i+1).size();
                    for (int j = 1; j <= row; j++) {

                        if(airport.getParking().get(i + 1).get(j - 1).getStatus().equals("parked")){
                            available = "-fx-background-color: red";
                        }
                        else{
                            available = "-fx-background-color: green";
                        }

                        VBox vbox = new VBox();
                        vbox.setStyle(available);
                        vbox.setAlignment(Pos.CENTER);


                        Text text = new Text("  " + airport.getParking().get(i+1).get(j-1).getParkingSeatID()+ "  ");

                        vbox.getChildren().add(text);

                        graphicGrid.add(vbox, i, j); //  (child, columnIndex, rowIndex)


                    }

                }

            }

            graphicGrid.setMinWidth(480);
            graphicGrid.setMaxWidth(480);
            graphicGrid.setMinHeight(286);
            graphicGrid.setMaxHeight(286);





            graphicGUI.getChildren().add(graphicGrid);

        }
    }));

    // Check availability for initial flights
    public void checkParkingAvailabilityInitial(Flight flight){
        int flightType = flight.getFlightType();
        int planeType = flight.getPlaneType();
        int departureTime = flight.getDepartureTime();

        List<ParkingSeat> aList;

        int parkingCategory = 0;
        boolean flag = true;

        if (flightType == 1 && (planeType == 2 || planeType == 3) && departureTime < 45) {
            parkingCategory = 1;
            aList = airport.getParking().get(parkingCategory);
            if(aList == null){
                parkingCategory = -1;
                System.out.println("Airport has no type '" + inttoCategory.get(1) + "'parking space");
            }
            else{
                for (ParkingSeat seat : aList) {
                    if (seat.getStatus().equals("free")) {
                        seat.setStatus("parked");
                        flight.setStatus("parked");
                        flight.setParkingID(seat.getParkingSeatID());
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag && flightType == 2 && (planeType == 2 || planeType == 3) && departureTime < 90) {
            parkingCategory = 2;
            aList = airport.getParking().get(parkingCategory);
            if(aList == null){
                parkingCategory = -1;
                System.out.println("Airport has no type '" + inttoCategory.get(2) + "'parking space");
            }
            else{
                for (ParkingSeat seat : aList) {
                    if (seat.getStatus().equals("free")) {
                        seat.setStatus("parked");
                        flight.setStatus("parked");
                        flight.setParkingID(seat.getParkingSeatID());
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag && flightType == 1 && (planeType == 2 || planeType == 3) && departureTime < 90) {
            parkingCategory = 3;
            aList = airport.getParking().get(parkingCategory);
            if(aList == null){
                parkingCategory = -1;
                System.out.println("Airport has no type '" + inttoCategory.get(3) + "'parking space");
            }
            else{
                for (ParkingSeat seat : aList) {
                    if (seat.getStatus().equals("free")) {
                        seat.setStatus("parked");
                        flight.setStatus("parked");
                        flight.setParkingID(seat.getParkingSeatID());
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag && (planeType == 2 || planeType == 3) && departureTime < 120) {
            parkingCategory = 4;
            aList = airport.getParking().get(parkingCategory);
            if(aList == null){
                parkingCategory = -1;
                System.out.println("Airport has no type '" + inttoCategory.get(4) + "'parking space");
            }
            else{
                for (ParkingSeat seat : aList) {
                    if (seat.getStatus().equals("free")) {
                        seat.setStatus("parked");
                        flight.setStatus("parked");
                        flight.setParkingID(seat.getParkingSeatID());
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag && planeType == 1 && departureTime < 180) {
            parkingCategory = 5;
            aList = airport.getParking().get(parkingCategory);
            if(aList == null){
                parkingCategory = -1;
                System.out.println("Airport has no type '" + inttoCategory.get(5) + "'parking space");
            }
            else{
                for (ParkingSeat seat : aList) {
                    if (seat.getStatus().equals("free")) {
                        seat.setStatus("parked");
                        flight.setStatus("parked");
                        flight.setParkingID(seat.getParkingSeatID());
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag && departureTime < 240) {
            parkingCategory = 6;
            aList = airport.getParking().get(parkingCategory);
            if(aList == null){
                parkingCategory = -1;
                System.out.println("Airport has no type '" + inttoCategory.get(6) + "'parking space");
            }
            else{
                for (ParkingSeat seat : aList) {
                    if (seat.getStatus().equals("free")) {
                        seat.setStatus("parked");
                        flight.setStatus("parked");
                        flight.setParkingID(seat.getParkingSeatID());
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag && (flightType == 2 || flightType == 3) && departureTime <= 600) {
            parkingCategory = 7;
            aList = airport.getParking().get(parkingCategory);
            if(aList == null){
                parkingCategory = -1;
                System.out.println("Airport has no type '" + inttoCategory.get(7) + "'parking space");
            }
            else{
                for (ParkingSeat seat : aList) {
                    if (seat.getStatus().equals("free")) {
                        seat.setStatus("parked");
                        flight.setStatus("parked");
                        flight.setParkingID(seat.getParkingSeatID());
                        flag = false;
                        break;
                    }
                }
            }
        }

        //Non available parking category
        if (parkingCategory == 0) {
            flight.setStatus("ignored");
            System.out.println("\n Non available parking category \n");
        }
        else if(parkingCategory == -1){
            flight.setStatus("rejected");
            System.out.println("\n No matching parking category for this flight, so rejected! \n");
        }
    }

    // Check availability for flights after initialization
    public void checkParkingAvailabilityAfter(Flight flight){
        int flightType = flight.getFlightType();
        int planeType = flight.getPlaneType();
        int departureTime = flight.getDepartureTime();

        List<ParkingSeat> aList;

        int parkingCategory = 0;
        boolean flag = true;

        if (flightType == 1 && (planeType == 2 || planeType == 3) && departureTime < 45) {

            aList = airport.getParking().get(1);
            if(aList == null){
                parkingCategory = -1;
                System.out.println("Airport has no type '" + inttoCategory.get(1) + "'parking space");
            }
            else{
                for (ParkingSeat seat : aList) {
                    if (seat.getStatus().equals("free")) {
                        parkingCategory = 1;
                        seat.setStatus("parked");
                        flight.setStatus("landing");
                        flight.setParkingID(seat.getParkingSeatID());
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag && flightType == 2 && (planeType == 2 || planeType == 3) && departureTime < 90) {

            aList = airport.getParking().get(2);
            if(aList == null){
                parkingCategory = -1;
                System.out.println("Airport has no type '" + inttoCategory.get(2) + "'parking space");
            }
            else{
                for (ParkingSeat seat : aList) {
                    if (seat.getStatus().equals("free")) {
                        parkingCategory = 2;
                        seat.setStatus("parked");
                        flight.setStatus("landing");
                        flight.setParkingID(seat.getParkingSeatID());
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag && flightType == 1 && (planeType == 2 || planeType == 3) && departureTime < 90) {

            aList = airport.getParking().get(3);
            if(aList == null){
                parkingCategory = -1;
                System.out.println("Airport has no type '" + inttoCategory.get(3) + "'parking space");
            }
            else{
                for (ParkingSeat seat : aList) {
                    if (seat.getStatus().equals("free")) {
                        parkingCategory = 3;
                        seat.setStatus("parked");
                        flight.setStatus("landing");
                        flight.setParkingID(seat.getParkingSeatID());
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag && (planeType == 2 || planeType == 3) && departureTime < 120) {

            aList = airport.getParking().get(4);
            if(aList == null){
                parkingCategory = -1;
                System.out.println("Airport has no type '" + inttoCategory.get(4) + "'parking space");
            }
            else{
                for (ParkingSeat seat : aList) {
                    if (seat.getStatus().equals("free")) {
                        parkingCategory = 4;
                        seat.setStatus("parked");
                        flight.setStatus("landing");
                        flight.setParkingID(seat.getParkingSeatID());
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag && planeType == 1 && departureTime < 180) {

            aList = airport.getParking().get(5);
            if(aList == null){
                parkingCategory = -1;
                System.out.println("Airport has no type '" + inttoCategory.get(5) + "'parking space");
            }
            else{
                for (ParkingSeat seat : aList) {
                    if (seat.getStatus().equals("free")) {
                        parkingCategory = 5;
                        seat.setStatus("parked");
                        flight.setStatus("landing");
                        flight.setParkingID(seat.getParkingSeatID());
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag && departureTime < 240) {

            aList = airport.getParking().get(6);
            if(aList == null){
                parkingCategory = -1;
                System.out.println("Airport has no type '" + inttoCategory.get(6) + "'parking space");
            }
            else{
                for (ParkingSeat seat : aList) {
                    if (seat.getStatus().equals("free")) {
                        parkingCategory = 6;
                        seat.setStatus("parked");
                        flight.setStatus("landing");
                        flight.setParkingID(seat.getParkingSeatID());
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag && (flightType == 2 || flightType == 3) && departureTime < 600) {

            aList = airport.getParking().get(7);
            if(aList == null){
                parkingCategory = -1;
                System.out.println("Airport has no type '" + inttoCategory.get(7) + "'parking space");
            }
            else{
                for (ParkingSeat seat : aList) {
                    if (seat.getStatus().equals("free")) {
                        parkingCategory = 7;
                        seat.setStatus("parked");
                        flight.setStatus("landing");
                        flight.setParkingID(seat.getParkingSeatID());
                        flag = false;
                        break;
                    }
                }
            }
        }

        //Non available parking category
        if (parkingCategory == 0) {
            flight.setStatus("holding");
            System.out.println("\n Non available parking category at this moment \n");
        }
        else if(parkingCategory == -1){
            flight.setStatus("rejected");
            System.out.println("\n No matching parking category for this flight, so rejected! \n");
        }
    }


    public void loadButtonClicked(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../resources/loadScenario.fxml"));
        Parent root = loader.load();

        Scene tableViewScene = new Scene(root);

        //access the controller and call a method
        LoadScenarioController controller = loader.getController();

        //This line gets the Stage information
        Stage stage = new Stage();

        controller.setStage(stage);

        stage.setTitle("Scenario ID");
        stage.setScene(tableViewScene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        String[] result;

        try{
            result = controller.getResult().split(",");

            if(result.length == 2) {
                AirportScenarioID = result[0];
                SetupScenarioID = result[1];
            }
            else{
                AirportScenarioID = "";
                SetupScenarioID = "";
            }
        }
        catch (NullPointerException ignored){

        };

        //Set button serialization
        //(1st) LoadButton
        //(2nd) StartButton
        //(3rd) SubmitButton
        buttonSerialization = 1;

    }

    public void startButtonClicked(ActionEvent actionEvent) {

        if(buttonSerialization == 1){

            //=============================================
            //============== Initializations ==============
            //=============================================

            //Initialize Timer
            AnimationTimer timer = new myTimer();
            timer.stop();

            controlTimer.cancelControlTimer();
            controlTimer = new ControlTimer();

            gridGuiTimer.stop(); //.pause();
            graphicGUI.getChildren().clear();


            // Initialize new airport
            airport = new Airport();

            System.out.println("\n\n========================================");
            System.out.println("============ STARTS A NEW SESSION =============");
            System.out.println("========================================\n\n");

            //set directory of SCENARIOS
            String airportDirectory = "src/medialab/airport_SCENARIO-" + AirportScenarioID + ".txt";
            String setupDirectory = "src/medialab/setup_SCENARIO-" + SetupScenarioID + ".txt";

            if(Files.exists(Paths.get(airportDirectory))){
                if(Files.exists(Paths.get(setupDirectory))){

                    try {
                        File myObj = new File(airportDirectory);
                        Scanner myReader = new Scanner(myObj);

                        if(!myReader.hasNextLine()){
                            System.out.println("No initial state! Please, enter a non empty airport scenario");
                        }
                        else{
                            HashMap<Integer, List<ParkingSeat>> tempParking = new HashMap<>();

                            while (myReader.hasNextLine()) {
                                String data = myReader.nextLine();
                                String[] splitData = data.split(",\\s*");

                                List<ParkingSeat> parkingList = new ArrayList<ParkingSeat>();

                                for(int i = 1; i <= Integer.parseInt(splitData[1]); i++){
                                    ParkingSeat seat = new ParkingSeat();

                                    seat.setCategory(Integer.parseInt(splitData[0]));
                                    seat.setCost(Integer.parseInt(splitData[2]));
                                    seat.setParkingSeatID(splitData[3].concat(Integer.toString(i)));
                                    seat.setStatus("free");

                                    prefixtoCategory.put(splitData[3], Integer.parseInt(splitData[0]));

                                    parkingList.add(seat);
                                }

                                tempParking.put(Integer.parseInt(splitData[0]), parkingList);

                            }
                            myReader.close();

                            airport.setParking(tempParking);

                            //Read setup Scenario
                            try {
                                File myObj2 = new File(setupDirectory);
                                Scanner myReader2 = new Scanner(myObj2);

                                if(!myReader2.hasNextLine()){
                                    System.out.println("No initial state! Please, enter a non empty setup scenario");
                                }
                                else{
                                    List<Flight> flights = new ArrayList<>();

                                    while (myReader2.hasNextLine()) {
                                        String data = myReader2.nextLine();
                                        String[] splitData = data.split(",\\s*");

                                        Flight aFlight = new Flight();

                                        aFlight.setFlightID(splitData[0]);
                                        aFlight.setCity(splitData[1]);
                                        aFlight.setFlightType(Integer.parseInt(splitData[2]));
                                        aFlight.setPlaneType(Integer.parseInt(splitData[3]));
                                        aFlight.setDepartureTime(Integer.parseInt(splitData[4]));
                                        aFlight.setServices("");
                                        aFlight.setDelay(rand.nextInt(4));

                                        flights.add(aFlight);

                                        //System.out.println(data);
                                    }
                                    myReader2.close();

                                    airport.setFlights(flights);


                                    //Initialize airport profit
                                    airport.setTotalProfit(0);

                                    //Initialize Timer
                                    //AnimationTimer timer = new myTimer();
                                    timer.start();

                                    controlTimer.startControlTimer();

                                    for (int i = 0; i < airport.getFlights().size(); i++) {
                                        checkParkingAvailabilityInitial(airport.getFlights().get(i));
                                    }

                                    //============= graphicGrid ================

                                    graphicGrid = new GridPane();

                                    String available;

                                    graphicGrid.setPadding(new Insets(2, 2, 2, 2));

                                    //Setting the vertical and horizontal gaps between the columns
                                    graphicGrid.setVgap(5);
                                    graphicGrid.setHgap(5);

                                    graphicGrid.setAlignment(Pos.CENTER_LEFT);

                                    //Set Column Constraints
                                    ColumnConstraints col1 = new ColumnConstraints();
                                    col1.setPercentWidth(14);
                                    ColumnConstraints col2 = new ColumnConstraints();
                                    col2.setPercentWidth(16);
                                    ColumnConstraints col3 = new ColumnConstraints();
                                    col3.setPercentWidth(14);
                                    ColumnConstraints col4 = new ColumnConstraints();
                                    col4.setPercentWidth(14);
                                    ColumnConstraints col5 = new ColumnConstraints();
                                    col5.setPercentWidth(14);
                                    ColumnConstraints col6 = new ColumnConstraints();
                                    col6.setPercentWidth(14);
                                    ColumnConstraints col7 = new ColumnConstraints();
                                    col7.setPercentWidth(14);
                                    graphicGrid.getColumnConstraints().addAll(col1, col2, col3, col4, col5, col6, col7);

                                    //Set Row Constraints
                                    int maxRow = 0;
                                    for(int i = 0; i < 7; i++) {
                                        if (airport.getParking().get(i + 1) != null) {
                                            if( airport.getParking().get(i+1).size() > maxRow){
                                                maxRow = airport.getParking().get(i+1).size();
                                            }
                                        }
                                    }

                                    for(int i = 0; i <= maxRow; i++){
                                        RowConstraints r = new RowConstraints();
                                        r.setPercentHeight(graphicGUI.getHeight() / maxRow);
                                        graphicGrid.getRowConstraints().add(r);

                                    }

                                    //Set 1st Line with the names of parking categories
                                    Label label = new Label("Gate");
                                    label.setStyle("-fx-font-weight: bold;");
                                    graphicGrid.add(label, 0, 0);
                                    GridPane.setHalignment(label, HPos.CENTER);

                                    label = new Label("PassGate");
                                    label.setStyle("-fx-font-weight: bold;");
                                    graphicGrid.add(label, 1, 0);
                                    GridPane.setHalignment(label, HPos.CENTER);

                                    label = new Label("Zone A");
                                    label.setStyle("-fx-font-weight: bold;");
                                    graphicGrid.add(label, 2, 0);
                                    GridPane.setHalignment(label, HPos.CENTER);

                                    label = new Label("Zone B");
                                    label.setStyle("-fx-font-weight: bold;");
                                    graphicGrid.add(label, 3, 0);
                                    GridPane.setHalignment(label, HPos.CENTER);

                                    label = new Label("Zone C");
                                    label.setStyle("-fx-font-weight: bold;");
                                    graphicGrid.add(label, 4, 0);
                                    GridPane.setHalignment(label, HPos.CENTER);

                                    label = new Label("General");
                                    label.setStyle("-fx-font-weight: bold;");
                                    graphicGrid.add(label, 5, 0);
                                    GridPane.setHalignment(label, HPos.CENTER);

                                    label = new Label("Long");
                                    label.setStyle("-fx-font-weight: bold;");
                                    graphicGrid.add(label, 6, 0);
                                    GridPane.setHalignment(label, HPos.CENTER);

                                    for(int i = 0; i < 7; i++) {

                                        if( airport.getParking().get(i+1) != null){

                                            int row = airport.getParking().get(i+1).size();
                                            for (int j = 1; j <= row; j++) {

                                                if(airport.getParking().get(i + 1).get(j - 1).getStatus().equals("parked")){
                                                    available = "-fx-background-color: red";
                                                }
                                                else{
                                                    available = "-fx-background-color: green";
                                                }

                                                VBox vbox = new VBox();
                                                vbox.setStyle(available);
                                                vbox.setAlignment(Pos.CENTER);


                                                Text text = new Text("  " + airport.getParking().get(i+1).get(j-1).getParkingSeatID()+ "  ");

                                                vbox.getChildren().add(text);

                                                graphicGrid.add(vbox, i, j); //  (child, columnIndex, rowIndex)


                                            }

                                        }

                                    }

                                    graphicGrid.setMinWidth(480);
                                    graphicGrid.setMaxWidth(480);
                                    graphicGrid.setMinHeight(286);
                                    graphicGrid.setMaxHeight(286);


                                    graphicGUI.getChildren().add(graphicGrid);


                                    gridGuiTimer.setCycleCount(Timeline.INDEFINITE);
                                    gridGuiTimer.play();

                                }
                            } catch (FileNotFoundException e) {
                                System.out.println("An error occurred.");
                                e.printStackTrace();
                            }

                        }


                    } catch (FileNotFoundException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }

                    //Set button serialization
                    //(1st) LoadButton
                    //(2nd) StartButton
                    //(3rd) SubmitButton
                    buttonSerialization = 2;

                }
                else{
                    System.out.println("Setup Scenario directory does not exist. Please, enter another ID");
                }
            }
            else{
                System.out.println("Airport Scenario directory does not exist. Please, enter another ID");
            }

        }
        else System.out.println("\n\nPlease, load a scenario before starting application\n\n");

    }


    @FXML
    private ChoiceBox<String> flightTypeChoiceBox, planeTypeChoiceBox, servicesChoiceBox;
    @FXML
    private TextField flightID, city, time;

    //These items are for the ChoiceBoxes
    ObservableList<String> flightTypeList = FXCollections.observableArrayList("Passenger", "Freight", "Private");
    ObservableList<String> planeTypeList = FXCollections.observableArrayList("Single Motor", "Turboprop", "Jet");
    ObservableList<String> servicesList = FXCollections.observableArrayList("Refueling", "Cleaning", "Passenger Transportation", "Load / Unload");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PrintStream ps = new PrintStream(new Console(textAreaConsole));
        System.setOut(ps);
        System.setErr(ps);

        inttoCategory.put(1, "Gate");
        inttoCategory.put(2, "Passenger Gate");
        inttoCategory.put(3, "Zone A");
        inttoCategory.put(4, "Zone B");
        inttoCategory.put(5, "Zone C");
        inttoCategory.put(6, "General Parking");
        inttoCategory.put(7, "LongTerm Parking");

        inttoFlighType.put(1, "Passenger");
        inttoFlighType.put(2, "Freight");
        inttoFlighType.put(3, "Private");

        inttoPlaneType.put(1, "Single Motor");
        inttoPlaneType.put(2, "Turboprop");
        inttoPlaneType.put(3, "Jet");

        servicetoCost.put("Refueling", 0.25);
        servicetoCost.put("Cleaning", 0.02);
        servicetoCost.put("Passenger Transportation", 0.02);
        servicetoCost.put("Load / Unload", 0.05);
        servicetoCost.put("", 0.0);

        delaytoCost.put(0, 1.0);  //0 = delayed => penalty, double cost
        delaytoCost.put(1, -0.1); //1 = 10-20 mins later => 10% discount
        delaytoCost.put(2, -0.2); //2 = 25 mins earlier => 20% discount
        delaytoCost.put(3, 0.0); //3 = no delay => no discount, no penalty



        //Initial message
        System.out.println("Please, load your scenarios and then click 'Start'");



        parkedFlights.setText("");
        availableParkingSpaces.setText("");
        departingFlights.setText("");
        totalProfit.setText("");
        timeSinceStart.setText("");


        flightTypeChoiceBox.getItems().addAll(flightTypeList);
        planeTypeChoiceBox.getItems().addAll(planeTypeList);
        servicesChoiceBox.getItems().addAll(servicesList);
    }


    public void submitButtonClicked(ActionEvent actionEvent) throws IOException {

        if(buttonSerialization == 2){

            Flight aFlight = new Flight();


            //Before submitting a new flight check for departed
            airport.checkforDepartedFlights();


            boolean b1 = time.getText().equals("");
            boolean b2 = city.getText().equals("");
            boolean b3 = flightID.getText().equals("");
            boolean b4 = servicesChoiceBox.getValue() == null;
            boolean b5 = flightTypeChoiceBox.getValue() == null;
            boolean b6 = planeTypeChoiceBox.getValue() == null;

            if (!b1 && !b2 && !b3 && !b4 && !b5 && !b6){



                // b7 =  check that time should be no longer than 10 hours
                // b8 = check if time has integer value
                boolean b7 = true, b8 = true;

                try {
                    b7 = Integer.parseInt(time.getText()) <= 600;
                } catch(NumberFormatException e) {
                    b8 = false;
                }

                if(b8){ // if time has integer value
                    if(b7){ //if time is no longer than 10 hours
                        aFlight.setDepartureTime(Integer.parseInt(time.getText()));
                        aFlight.setCity(city.getText());
                        aFlight.setFlightID(flightID.getText());
                        aFlight.setServices(servicesChoiceBox.getValue());
                        aFlight.setDelay(rand.nextInt(4));

                        String flightType = flightTypeChoiceBox.getValue();
                        if (flightType.equals("Passenger")) aFlight.setFlightType(1);
                        else if (flightType.equals("Freight")) aFlight.setFlightType(2);
                        else aFlight.setFlightType(3);

                        String planeType = planeTypeChoiceBox.getValue();
                        if (planeType.equals("Single Motor")) aFlight.setPlaneType(1);
                        else if (planeType.equals("Turboprop")) aFlight.setPlaneType(2);
                        else aFlight.setPlaneType(3);

                        checkParkingAvailabilityAfter(aFlight);


                        if(aFlight.getStatus().equals("landing")){
                            aFlight.setParkingTimer();
                        }
                        else if(aFlight.getStatus().equals("rejected")){
                            System.out.println("\n Flight with ID: " + aFlight.getFlightID() + " rejected form this airport! \n");
                        }
                        else{
                            System.out.println("\n Flight with ID: " + aFlight.getFlightID() + " is in 'holding' state! \n");
                        }

                        airport.addFlight(aFlight);

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../resources/submittedAnswersView.fxml"));
                        Parent root = loader.load();

                        Scene submittedAnswersScene = new Scene(root);

                        //access the controller and call a method
                        SubmittedAnswersController controller = loader.getController();

                        //This line gets the Stage information
                        Stage stage = new Stage();

                        controller.setStage(stage);
                        controller.initData(flightID.getText(), city.getText(), flightTypeChoiceBox.getValue(), planeTypeChoiceBox.getValue(), time.getText(), servicesChoiceBox.getValue());

                        stage.setTitle("Submitted Answers");
                        stage.setScene(submittedAnswersScene);
                        stage.initModality(Modality.APPLICATION_MODAL);

                        stage.show();
                    }
                    else {
                        //System.out.println("Please, time should be should be no longer than 10 hours");
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../resources/submittedWrongAnswersView.fxml"));
                        Parent root = loader.load();

                        Scene submittedEmptyAnswersScene = new Scene(root);

                        //access the controller and call a method
                        SubmittedWrongAnswersController controller = loader.getController();
                        controller.setTextMessage("Please, time should be should be no longer than 600 minutes");

                        //This line gets the Stage information
                        Stage stage = new Stage();

                        stage.setTitle("Submitted Answers");
                        stage.setScene(submittedEmptyAnswersScene);
                        stage.initModality(Modality.APPLICATION_MODAL);

                        stage.show();
                    }
                }
                else{
                    //System.out.println("Please, time should be given in minutes as a number");
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../resources/submittedWrongAnswersView.fxml"));
                    Parent root = loader.load();

                    Scene submittedEmptyAnswersScene = new Scene(root);

                    //access the controller and call a method
                    SubmittedWrongAnswersController controller = loader.getController();
                    controller.setTextMessage("Please, time should be given in minutes as a number");

                    //This line gets the Stage information
                    Stage stage = new Stage();

                    stage.setTitle("Submitted Answers");
                    stage.setScene(submittedEmptyAnswersScene);
                    stage.initModality(Modality.APPLICATION_MODAL);

                    stage.show();
                }
            }
            else{
                //System.out.println("Please, enter all flight's information ");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../resources/submittedWrongAnswersView.fxml"));
                Parent root = loader.load();

                Scene submittedEmptyAnswersScene = new Scene(root);

                //access the controller and call a method
                SubmittedWrongAnswersController controller = loader.getController();
                controller.setTextMessage("Please, enter all flight's information ");

                //This line gets the Stage information
                Stage stage = new Stage();

                stage.setTitle("Submitted Answers");
                stage.setScene(submittedEmptyAnswersScene);
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.show();
            }

        }
        else System.out.println("\n\nPlease, start application before submitting a new flight\n\n");
        
    }


    public void exitButtonClicked(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }



}
