package todolist.taskComponent;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import todolist.Task;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class taskControl extends BorderPane {
    @FXML
    private Label lblTask;
    @FXML
    private ImageView imgCheck;
    @FXML
    private Button btnDone;
    @FXML
    private Label lblId;

    private Task task;

    public taskControl(Task task) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("task.fxml"));
        fxmlloader.setRoot(this);
        fxmlloader.setController(this);
        try {
            fxmlloader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.task = task;

        //task view appearance
        this.lblId.setText("" + task.getId());
        this.setText(task.getTask());
        componentState();
    }

    private void componentState() {
        if (!this.task.isDone()) {
            //Change icon and button text when done
            File file = new File("src/assets/icon_no_check.png");
            Image image = new Image(file.toURI().toString());
            imgCheck.setImage(image);
            btnDone.setText("Done");
        } else {
            File file = new File("src/assets/icon_check.jpg");
            Image image = new Image(file.toURI().toString());
            imgCheck.setImage(image);
            btnDone.setText("Undone");
        }
    }

    public StringProperty textProperty() {
        return lblTask.textProperty();
    }

    public String getText() {
        return textProperty().get();
    }

    public void setText(String text) {
        textProperty().set(text);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean taskState() {
        return this.task.isDone();
    }


    @FXML
    private void taskDone(ActionEvent event) {
        if (!this.task.isDone()) {
            //Change icon and button text when done
            File file = new File("src/assets/icon_check.jpg");
            Image image = new Image(file.toURI().toString());
            imgCheck.setImage(image);
            btnDone.setText("Undone");
            this.task.setDone(true);
            taskDoneSQL();
        } else {
            File file = new File("src/assets/icon_no_check.png");
            Image image = new Image(file.toURI().toString());
            imgCheck.setImage(image);
            btnDone.setText("Done");
            this.task.setDone(false);
            taskDoneSQL();
        }
    }

    private void taskDoneSQL() {
        int isDone = 0;
        if(this.task.isDone()){
            isDone = 1;
        }
        String query = "UPDATE `tasks` SET `isDone`='" + isDone + "' WHERE `id`='" + this.task.getId() + "'";
        executeQuery(query);
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