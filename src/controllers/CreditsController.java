package controllers;

import constants.SCENES;
import core.events.EventCollection;
import core.events.SceneEvent;
import javafx.event.Event;
import views.components.ButtonCpnt;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CreditsController extends AController implements Initializable{

    @FXML
    ButtonCpnt menu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        menu.setOnAction(e -> {
            Event eventGame = new SceneEvent(EventCollection.SCENE_CHANGE, SCENES.MENUSCENE);
            menu.fireEvent(eventGame);
        });

    }

    @Override
    public void refresh() {
        //
    }
}

