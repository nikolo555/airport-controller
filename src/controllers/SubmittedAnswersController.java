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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SubmittedAnswersController implements Initializable {

    //configure the table
    @FXML
    Text flightID, city, flightType, planeType, time, services;

    private Stage stage = null;

    /**
     * setting the stage of this view
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initData(String fID, String c, String fType, String pType, String t, String ser)
    {
        flightID.setText(fID);
        city.setText(c);
        flightType.setText(fType);
        planeType.setText(pType);
        time.setText(t);
        services.setText(ser);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO
    }

}
