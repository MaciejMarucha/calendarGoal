package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class DialogController {

    private MainFrameController mainFrameController;

    private String buttonId;
    @FXML
    private Button returnBtn;

    @FXML
    public void cancel(ActionEvent actionEvent) throws IOException {
        mainFrameController.cancelColor(buttonId);
        returnBtn.getScene().getWindow().hide();
    }

    public void greenBtnDialogMethod(ActionEvent actionEvent) throws IOException {
        mainFrameController.greenBtn(buttonId);
        returnBtn.getScene().getWindow().hide();
    }

    public void redBtnDialogMethod(ActionEvent actionEvent) throws IOException {
        mainFrameController.redBtn(buttonId);
        returnBtn.getScene().getWindow().hide();
    }

    public void yellowBtnDialogMethod(ActionEvent actionEvent) throws IOException {
        mainFrameController.yellowBtn(buttonId);
        returnBtn.getScene().getWindow().hide();
    }

    public void grayBtnDialogMethod(ActionEvent actionEvent) throws IOException {
        mainFrameController.grayBtn(buttonId);
        returnBtn.getScene().getWindow().hide();
    }

    public void setMainFrameController(MainFrameController mainFrameController) {
        this.mainFrameController = mainFrameController;
    }

    public void setButtonId(String id) {
        this.buttonId = id;
    }


}
