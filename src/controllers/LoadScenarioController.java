/**
 * @author Nikolas Papadopoulos
 * created on 25/03/2020
 * ECE NTUA
 */
package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadScenarioController implements Initializable {

    @FXML
    private TextField IDtext1, IDtext2;
    @FXML
    private Button okButton;

    private Stage stage = null;
    private String result;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        okButton.setOnAction((event)->{
            String temp1, temp2;
            temp1 = IDtext1.getText();
            temp2 = IDtext2.getText();
            result = temp1 + "," + temp2;
            closeStage();
        });

    }

    public String getResult() {
        return this.result;
    }

    /**
     * setting the stage of this view
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Closes the stage of this view
     */
    private void closeStage() {
        if(stage!=null) {
            stage.close();
        }
    }

}
