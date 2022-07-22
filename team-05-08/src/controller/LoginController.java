package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;
import model.UserDAO;
import edu.sjsu.yazdankhah.crypto.util.PassUtil;
import javafx.event.ActionEvent;

public class LoginController {

	@FXML
	private TextField userName;

	@FXML
	private PasswordField userPassword;

	@FXML
	private Button loginBtn;

	@FXML
	private Button signUpBtn;

	@FXML
	private Hyperlink forgotPassword;

	@FXML
	void login(ActionEvent event) throws Exception {
		User user = new User(userName.getText(), userPassword.getText());
		UserDAO getUser = new UserDAO();
		PassUtil pass = new PassUtil();
		user = getUser.findUser(user);
		if (user != null) {
			if (pass.decrypt(user.getPassWord()).equalsIgnoreCase(userPassword.getText())) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Dashboard.fxml"));
				Parent root = loader.load();
				DashBoardController dashboardController = loader.getController();
				dashboardController.transferObject(user);

				Stage window = (Stage) signUpBtn.getScene().getWindow();
				window.setTitle("Password management - Dashboard");
				window.setScene(new Scene(root, 735, 442));
			} else {
				alert("Username/password incorrect");
			}

		} else {
			alert("Can't find your account");
		}
	}

	@FXML
	void redirectSignUpPage(ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("../view/signup.fxml"));
		Stage window = (Stage) signUpBtn.getScene().getWindow();
		window.setScene(new Scene(root, 600, 400));
	}

	@FXML
	void redirectToForgotPassword(ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("../view/ResetPassword.fxml"));
		Stage window = (Stage) forgotPassword.getScene().getWindow();
		window.setScene(new Scene(root, 600, 400));
	}

	void alert(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText(msg);
		alert.show();
	}

	public void initialize() {

	}
}