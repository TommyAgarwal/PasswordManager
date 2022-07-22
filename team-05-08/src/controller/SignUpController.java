package controller;

import java.io.IOException;

import edu.sjsu.yazdankhah.crypto.util.PassUtil;
import helperClass.convertToString;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import model.UserDAO;

public class SignUpController {
	ObservableList<String> questionList = FXCollections.observableArrayList("What is your first pet name?", "Where you was born?");
    @FXML
    private PasswordField confirmPassword;

    @FXML
    private DatePicker dob;

    @FXML
    private TextField email;

    @FXML
    private RadioButton female;

    @FXML
    private TextField userName;

    @FXML
    private Button homeBtn;

    @FXML
    private RadioButton male;

    @FXML
    private TextField sercurityAnswer;

    @FXML
    private PasswordField password;

    @FXML
    private Button signUpBtn;
    
    convertToString convert = new convertToString();
    
	@FXML
    private ChoiceBox<String> sercurityQuestion;

    @FXML
    void getFormData(ActionEvent event) throws IOException {
    	PassUtil pass = new PassUtil();
    	User user = new User(userName.getText(), email.getText() , convert.convertLocalDateToString(dob.getValue()), 
    						pass.encrypt(password.getText()), (String) sercurityQuestion.getValue(), 
    						sercurityAnswer.getText());
    	UserDAO signUp = new UserDAO();
    	if (userName.getText().length()==0 || email.getText().length()==0 || 
    			dob.getValue() == null || password.getText().length()==0|| 
    			sercurityQuestion.getValue() == null || sercurityAnswer.getText() == null) {
    		alert("Please filled all empty blanks");
    	}
    	else if (signUp.addNewUser(user)) {
    		Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        	Stage window = (Stage) homeBtn.getScene().getWindow();
        	window.setScene(new Scene(root, 600, 400));
        	alert("Account create successfull");
    	}else {
    		alert("Username or Email has been used");
    	}
    	

    }
    @FXML
    void redirectLoginPage(ActionEvent event) throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
    	Stage window = (Stage) homeBtn.getScene().getWindow();
    	window.setScene(new Scene(root, 600, 400));
    }

    public void alert(String msg) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setContentText(msg);
    	alert.show(); 	
    }
    
	public void initialize() {
       sercurityQuestion.setValue(questionList.get(0));
       sercurityQuestion.setItems(questionList);
    }
}

