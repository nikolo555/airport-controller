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

public class GateViewController implements Initializable {

    //configure the table
    @FXML
    private TableView<GateView> gateView;
    @FXML
    private TableColumn<GateView, String> gateIDColumn;
    @FXML
    private TableColumn<GateView, String> categoryColumn;
    @FXML
    private TableColumn<GateView, String> statusColumn;
    @FXML
    private TableColumn<GateView, String> flightIDColumn;
    @FXML
    private TableColumn<GateView, String> departureTimeColumn;



    public void initData(ObservableList<GateView> gatesInfo)
    {
        //set up the columns in the table
        gateIDColumn.setCellValueFactory(new PropertyValueFactory<GateView, String>("parkingSeatID"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<GateView, String>("category"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<GateView, String>("status"));
        flightIDColumn.setCellValueFactory(new PropertyValueFactory<GateView, String>("flightID"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<GateView, String>("departureTime"));

        gateView.setItems(gatesInfo);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO

    }

}
