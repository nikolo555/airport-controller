/**
 * @author Nikolas Papadopoulos
 * created on 25/03/2020
 * ECE NTUA
 */

package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SubmittedWrongAnswersController implements Initializable {


    private Stage stage = null;
    @FXML
    private Text textMessage;


    /**
     * setting the stage of this view
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTextMessage(String text){
        textMessage.setText(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO
    }

}
