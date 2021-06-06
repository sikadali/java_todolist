package todolist.taskComponent;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;

public class taskControl extends BorderPane {
    @FXML
    private Label lblTask;
    @FXML
    private ImageView imgCheck;
    @FXML
    private Button btnDone;

    private boolean isDone = false;

    public taskControl() {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("task.fxml"));
        fxmlloader.setRoot(this);
        fxmlloader.setController(this);
        try{
            fxmlloader.load();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public StringProperty textProperty(){
        return lblTask.textProperty();
    }

    public String getText(){
        return textProperty().get();
    }

    public void setText(String text){
        textProperty().set(text);
    }

    public boolean taskState(){
        return this.isDone;
    }

    @FXML
    private void taskDone(ActionEvent event){
        if (!isDone){
            //Change icon and button text when done
            File file = new File("src/assets/icon_check.jpg");
            Image image = new Image(file.toURI().toString());
            imgCheck.setImage(image);
            btnDone.setText("Undone");
            isDone = true;
        } else {
            File file = new File("src/assets/icon_no_check.png");
            Image image = new Image(file.toURI().toString());
            imgCheck.setImage(image);
            btnDone.setText("Done");
            isDone = false;
        }

    }
}
