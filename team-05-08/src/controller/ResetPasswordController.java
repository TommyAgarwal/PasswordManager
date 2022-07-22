package controller;

import edu.sjsu.yazdankhah.crypto.util.PassUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;
import model.UserDAO;

public class ResetPasswordController {
	ObservableList<String> questionList = FXCollections.observableArrayList("What is your first pet name?",
			"Where you was born?");
	@FXML
	private Button cancelBtn;

	@FXML
	private TextField confirmNewPassword;

	@FXML
	private TextField newPassword;

	@FXML
	private Button resetBtn;

	@FXML
	private TextField sercurityAnswer;

	@FXML
	private ChoiceBox<String> sercurityQuestion;

	@FXML
	private TextField username;

	@FXML
	void cancelRedirectLoginPage(ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
		Stage window = (Stage) cancelBtn.getScene().getWindow();
		window.setScene(new Scene(root, 600, 400));

	}

	@FXML
	void resetRedirectLoginPage(ActionEvent event) throws Exception {
		User user = new User();
		UserDAO dao = new UserDAO();
		PassUtil pass = new PassUtil();
		int userIndex = dao.findUserIndexByName(username.getText());
		if (userIndex == -1) {
			alert("Account doesn't existed!!!!");
		} else {
			user = dao.getUserList().get(userIndex);
			if (user.getSercurityQuestion().equalsIgnoreCase(sercurityQuestion.getValue())
					&& user.getSerCurityAnswer().equalsIgnoreCase(sercurityAnswer.getText())) {
				if (newPassword.getText().equalsIgnoreCase(confirmNewPassword.getText())) {
					dao.resetMasterPassword(username.getText(),pass.encrypt(newPassword.getText()));
					Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
					Stage window = (Stage) resetBtn.getScene().getWindow();
					window.setScene(new Scene(root, 600, 400));
					alert("Reset Password Successful");
				} else {
					alert("Password and Confirm Password doesn't match");
				}

			} else {
				alert("Sercurity question and answer doesn't match on our record.!!!!");
			}
		}
	}

	public void initialize() {
		sercurityQuestion.setValue(questionList.get(0));
		sercurityQuestion.setItems(questionList);
	}

	void alert(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText(msg);
		alert.show();
	}

}
