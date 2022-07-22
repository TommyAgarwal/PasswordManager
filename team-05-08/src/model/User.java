package model;


import java.util.List;

public class User {
	private String username;
	private String email;
	private String dob;
	private String passWord;
	private String sercurityQuestion;
	private String sercurityAnswer;
	private List<ApplicationInfos> listApp;
	
	
	
	public User() {
	}
	public User(String username) {
		this.username = username;
	}
	public User(String username, String passWord) {
		this.username = username;
		this.passWord = passWord;
	}

	public User(String username, String email, String localDate, String passWord, String sercurityQuestion,
			String sercurityAnswer) {
		this.username = username;
		this.email = email;
		this.dob = localDate;
		this.passWord = passWord;
		this.sercurityQuestion = sercurityQuestion;
		this.sercurityAnswer = sercurityAnswer;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getSercurityQuestion() {
		return sercurityQuestion;
	}

	public void setSercurityQuestion(String sercurityQuestion) {
		this.sercurityQuestion = sercurityQuestion;
	}

	public String getSerCurityAnswer() {
		return sercurityAnswer;
	}

	public void setSerCurityAnswer(String serCurityAnswer) {
		this.sercurityAnswer = serCurityAnswer;
	}

	public List<ApplicationInfos> getListApp() {
		return listApp;
	}

	public void setListApp(List<ApplicationInfos> listApp) {
		this.listApp = listApp;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}
}
