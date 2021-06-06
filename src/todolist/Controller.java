package todolist;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import todolist.taskComponent.taskControl;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //List of tasks; to put it in SQL later
    private ArrayList<taskControl> listTask = new ArrayList();

    @FXML
    private Button btnCreate;

    @FXML
    private TextField tfTask;

    @FXML
    private VBox vbTask;

    @FXML
    private Button btnClear;

    @FXML
    private void handleButtonAction(ActionEvent event){

        if(event.getSource() == btnCreate){
            if (tfTask.getText() != "") {
                createTask(tfTask.getText());
                updateVB();
            }
        }

        else if(event.getSource() == btnClear){
            for(taskControl i : listTask){
                if(i.taskState()){
                    listTask.remove(i);
                    updateVB();
                }
            }
        }

    }

    private void updateVB() {
        vbTask.getChildren().clear();
        vbTask.getChildren().addAll(listTask);
        tfTask.setText("");
    }

    private void createTask(String task) {
        taskControl taskCreated = new taskControl();
        taskCreated.setText(task);
        listTask.add(taskCreated);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       //TODO
    }
}