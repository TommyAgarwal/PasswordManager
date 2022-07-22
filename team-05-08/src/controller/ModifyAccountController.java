package controller;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ApplicationInfos;
import model.User;
import model.UserDAO;

public class ModifyAccountController {
	List<ApplicationInfos> listApp;
	int appIndex;
	User user;

	@FXML
	private Label oldExpiredDate;

	@FXML
	private Label oldPassword;

	@FXML
	private Label oldUsername;

	@FXML
	private Label accountName;

	@FXML
	private TextField usernameTxt;

	@FXML
	private TextField passwordTxt;

	@FXML
	private DatePicker dateTxt;

	@FXML
	private Button backBtn;

	@FXML
	private Button deleteBtn;

	@FXML
	private Button saveBtn;
	
	   @FXML
	    void deleteAccount(ActionEvent event) throws Exception {
	    	UserDAO dao = new UserDAO();
	    	listApp.remove(appIndex);
	    	dao.storingKey(user.getUsername(), listApp);
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Dashboard.fxml"));
			Parent root = loader.load();
			DashBoardController dashboardController = loader.getController();
			dashboardController.transferObject(user);

			Stage window = (Stage) deleteBtn.getScene().getWindow();
			window.setTitle("Password management - Dashboard");
			window.setScene(new Scene(root, 735, 442));
	    }

	public void transferObject(User user, String name) {
		this.user = user;
		listApp = this.user.getListApp();
		for (int i = 0; i < listApp.size(); i++) {
			if (listApp.get(i).getName().equalsIgnoreCase(name)) {
				appIndex = i;
				break;
			}
		}
		accountName.setText(name);
		oldUsername.setText("( " + listApp.get(appIndex).getUserName() + " )");
		oldPassword.setText("( " + listApp.get(appIndex).getPassWord() + " )");
		oldExpiredDate.setText("( " + listApp.get(appIndex).getDate() + " )");

	}

	
}
