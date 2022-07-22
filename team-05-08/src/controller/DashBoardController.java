package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import helperClass.DateFormatConvert;
import helperClass.PasswordGenerator;
import helperClass.convertToString;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import model.ApplicationInfos;
import model.User;
import model.UserDAO;

public class DashBoardController {
	User user;
	String appEditName;
	List<ApplicationInfos> listApp;
	convertToString convert = new convertToString();

	@FXML
    private Button editBtn;
	
	@FXML
	private Button addBtn;

	@FXML
	private TableView<ApplicationInfos> tableView;

	@FXML
	private TableColumn<ApplicationInfos, String> appNameCol;

	@FXML
	private TableColumn<ApplicationInfos, String> passwordCol;

	@FXML
	private TableColumn<ApplicationInfos, String> userNameCol;

	@FXML
	private TableColumn<ApplicationInfos, String> newPassWordExpiredCol;

	@FXML
	private TextField newAppName;

	@FXML
	private TextField newAppPassword;

	@FXML
	private TextField newAppUserName;

	@FXML
	private DatePicker expiredDate;
	
	@FXML
    private TextField searching;
	
	@FXML
	private Button logoutBtn;
	
	 @FXML
	 private Button modifyAccountBtn;
	

    @FXML
    void copyToClipboard(ActionEvent event) {
			if (tableView.getSelectionModel().getSelectedItem()==null ) {
				alert("Please seclect account for copy.");
			}else {
				ClipboardContent content = new ClipboardContent();
				content.putString(tableView.getSelectionModel().getSelectedItem().getPassWord());
				Clipboard.getSystemClipboard().setContent(content);
			}
    }

    @FXML
    void logout(ActionEvent event) throws Exception {
    	user = null;
    	Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
		Stage window = (Stage) logoutBtn.getScene().getWindow();
		window.setScene(new Scene(root, 600, 400));
    }
    @FXML
    void deletedAccount(ActionEvent event) throws Exception {
    	UserDAO dao = new UserDAO();
    	if (tableView.getSelectionModel().getSelectedItem()==null ) {
			alert("Please seclect account for delete.");
		}else {
			String name = tableView.getSelectionModel().getSelectedItem().getName();
			for(int i = 0; i < listApp.size(); i++) {
				if (listApp.get(i).getName().equalsIgnoreCase(name)) {
					listApp.remove(i);
				}
			}	
		}	
    	dao.storingKey(user.getUsername(), listApp);
    	displayTableView(user.getListApp());
    }
    
    @FXML
    void editAccount(ActionEvent event) {
    	UserDAO dao = new UserDAO();
    	if (tableView.getSelectionModel().getSelectedItem()==null ) {
			alert("Please seclect account for modify.");
		}else {
			int tempAlert = 0;
			for (int i = 0; i < listApp.size(); i++) {
				if (newAppName.getText().equalsIgnoreCase(listApp.get(i).getName())) {
					tempAlert++;
					if (newAppUserName.getText().equalsIgnoreCase(listApp.get(i).getName()) || newAppPassword.getText().equalsIgnoreCase(listApp.get(i).getPassWord())) {
						alert("New Username and Password shoudn't use before!!!!");	
						break;
					}else {
						LocalDate todayDate = LocalDate.now();
						LocalDate temp = (expiredDate.getValue() == null) ? todayDate : expiredDate.getValue();
						listApp.get(i).setUserName(newAppUserName.getText());
						listApp.get(i).setPassWord(newAppPassword.getText());
						listApp.get(i).setDate(convert.convertLocalDateToString(temp));
						break;
					}
				}
			}
			if (tempAlert == 0) {
				alert("Website/Application name should match our record for edit option!!!");
			}
		}
    	dao.storingKey(user.getUsername(), listApp);
    	displayTableView(listApp);

    }

    @FXML
    void passWordGenerator(ActionEvent event) {
    	PasswordGenerator pass = new PasswordGenerator();
    	newAppPassword.setText(pass.getRandomPassword());
    }

	@FXML
	void storingAccount(ActionEvent event) {
		UserDAO dao = new UserDAO();

		if (newAppName.getText().length() == 0 || newAppUserName.getText().length() == 0
				|| newAppPassword.getText().length() == 0) {
			alert("Please fill the infomation for storing new account");
		} else {
			LocalDate todayDate = LocalDate.now();
			LocalDate temp = (expiredDate.getValue() == null) ? todayDate : expiredDate.getValue();
			ApplicationInfos app = new ApplicationInfos(newAppName.getText(), newAppUserName.getText(),
					newAppPassword.getText(), convert.convertLocalDateToString(temp));
			boolean foundExistedApp = false;
			for (ApplicationInfos apps : listApp) {
				if (apps.getName().equalsIgnoreCase(app.getName())) {
					String mesg = "Found app name: " + apps.getName() + " in your account";
					foundExistedApp = true;
					alert(mesg);
				}
			}
			if (!foundExistedApp) {
				listApp.add(app);
				user.setListApp(listApp);
				dao.storingKey(user.getUsername(), listApp);
				displayTableView(user.getListApp());
			}
		}
	}

	public void alert(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText(msg);
		alert.show();
	}

	public void transferObject(User user) {
		this.user = user;
		if (user.getListApp() == null) {
			listApp = new ArrayList<ApplicationInfos>();
		} else {
			listApp = user.getListApp();
		}
		if (tableView!=null) {
			tableView.setOnMouseClicked(e -> {
				newAppName.setText(tableView.getSelectionModel().getSelectedItem().getName());
				appEditName = tableView.getSelectionModel().getSelectedItem().getName();
			});
		}

		displayTableView(user.getListApp());
		expiredPassReminder();
		searchingApp();

	};
	
	private void searchingApp() {
		searching.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String newValue) {
		    		List<ApplicationInfos> searchingList = new ArrayList<ApplicationInfos>();
		    		searchingList.addAll(listApp);
		    		char [] newValueChar = newValue.toCharArray();
		    		for (int i = 0; i < listApp.size(); i++) {
		    			char[] searchingListChar = listApp.get(i).getName().toCharArray();
		    			char[] usernameListChar = listApp.get(i).getUserName().toCharArray();
		    			if (newValueChar.length > searchingListChar.length && newValueChar.length > usernameListChar.length) {
		    				searchingList.remove(listApp.get(i));
		    			}else {
		    				if (newValueChar.length > searchingListChar.length && newValueChar.length <= usernameListChar.length) {
			    				for (int j = 0; j < newValueChar.length;j++) {
			    					if (newValueChar[j]!= usernameListChar[j]) {
				    						searchingList.remove(listApp.get(i));
				    						break;	
			    					}
			    				}
		    				}else if (newValueChar.length <= searchingListChar.length && newValueChar.length > usernameListChar.length) {
		    					for (int j = 0; j < newValueChar.length;j++) {
			    					if (newValueChar[j]!= searchingListChar[j]) {
				    						searchingList.remove(listApp.get(i));
				    						break;	
			    					}
			    				}
		    				}else {
		    					for (int j = 0; j < newValueChar.length;j++) {
			    					if (newValueChar[j]!= searchingListChar[j] && newValueChar[j]!= usernameListChar[j]) {
				    						searchingList.remove(listApp.get(i));
				    						break;	
			    					}
			    				}
		    				}
		    			}
		    		}
		    		displayTableView(searchingList);
		    }
		});
		
	}

	private void expiredPassReminder() {
		DateFormatConvert df = new DateFormatConvert();
		String result ="";
		for (int i = 0; i < listApp.size(); i++) {
			if (df.getExpiredDate(listApp.get(i).getDate()) == 0) {
				result += "App/Website name: " + listApp.get(i).getName() + " password expired!!!\n";	
			}
		}
		if (result != "") {
			alert(result);
		}
	}
	
	public void displayTableView(List<ApplicationInfos> userList) {
		if (userList != null) {
			tableView.refresh();
			ObservableList<ApplicationInfos> tvObservableList = FXCollections.observableArrayList(userList);
			appNameCol.setCellValueFactory(new PropertyValueFactory<ApplicationInfos, String>("name"));
			userNameCol.setCellValueFactory(new PropertyValueFactory<ApplicationInfos, String>("userName"));
			passwordCol.setCellValueFactory(new PropertyValueFactory<ApplicationInfos, String>("passWord"));
			newPassWordExpiredCol.setCellValueFactory(new PropertyValueFactory<ApplicationInfos, String>("date"));
			tableView.setItems(tvObservableList);
			
	


		}

	}
}
