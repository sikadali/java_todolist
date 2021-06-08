package todolist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import todolist.taskComponent.taskControl;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private ObservableList<Task> listTask = FXCollections.observableArrayList();

    @FXML
    private Button btnCreate;

    @FXML
    private TextField tfTask;

    @FXML
    private TextField tfId;

    @FXML
    private VBox vbTask;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnEdit;

    @FXML
    private void handleButtonAction(ActionEvent event){

        if(event.getSource() == btnCreate) {
            insertTask(tfId.getText(), tfTask.getText());
        }
        else if(event.getSource() == btnEdit) {
            updateTask();
        }
        else if(event.getSource() == btnClear) {
            deleteTask();
        }
    }

    private void deleteTask(){
        String query = "DELETE FROM `tasks` WHERE `isDone`='1'";
        executeQuery(query);
        showTasks();
    }

    private void updateTask() {
        if(tfId.getText() == "" || tfTask.getText() == ""){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("WARNING");
            alert.setHeaderText("How to edit a task");
            String s ="You must define the ID of the task, and the modified task in the text fields, and then click on \"Edit\" button.";
            alert.setContentText(s);
            alert.show();
        }else{
            String query = "UPDATE `tasks` SET `task`='" + tfTask.getText() + "' WHERE `id`='"+ tfId.getText() +"'";
            executeQuery(query);
            showTasks();
            resetDisplay();
        }
    }

    private void insertTask(String id, String task) {
        if(id != "" && task != ""){
            String query = "INSERT INTO `tasks`(`id`, `task`, `isDone`) VALUES ('" + id + "','" + task + "','0')";
            executeQuery(query);
            showTasks();
            resetDisplay();
        }
    }

    private void updateVB() {
        vbTask.getChildren().clear();
        for(Task t : listTask) {
            vbTask.getChildren().add(new taskControl(t));
        }
    }
    private void resetDisplay(){
        tfTask.setText("");
        tfId.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       //TODO
        showTasks();
    }


    private void showTasks(){
        listTask = getTaskList();
        updateVB();
    }

    private ObservableList<Task> getTaskList() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM tasks";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Task task;
            while(rs.next()){
                task = new Task(rs.getInt("id"), rs.getString("task"));
                task.setDone(rs.getBoolean("isDone"));
                tasks.add(task);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return tasks;
    }

    public Connection getConnection(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/simple_todolist_db", "root", "");
            return conn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void executeQuery(String query){
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}