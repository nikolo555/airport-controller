/**
 * @author Nikolas Papadopoulos
 * created on 25/03/2020
 * ECE NTUA
 */
package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class FlightViewController implements Initializable {

    //configure the table
    @FXML
    private TableView<FlightView> flightView;
    @FXML
    private TableColumn<FlightView, String> flightIDColumn;
    @FXML
    private TableColumn<FlightView, String> cityColumn;
    @FXML
    private TableColumn<FlightView, String> flightTypeColumn;
    @FXML
    private TableColumn<FlightView, String> planeTypeColumn;
    @FXML
    private TableColumn<FlightView, String> statusColumn;
    @FXML
    private TableColumn<FlightView, String> departureTimeColumn;



    public void initData(ObservableList<FlightView> flightsInfo)
    {
        //set up the columns in the table
        flightIDColumn.setCellValueFactory(new PropertyValueFactory<FlightView, String>("flightID"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<FlightView, String>("city"));
        flightTypeColumn.setCellValueFactory(new PropertyValueFactory<FlightView, String>("flightType"));
        planeTypeColumn.setCellValueFactory(new PropertyValueFactory<FlightView, String>("planeType"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<FlightView, String>("status"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<FlightView, String>("departureTime"));

        flightView.setItems(flightsInfo);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO

    }

}
