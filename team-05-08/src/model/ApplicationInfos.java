package model;

public class ApplicationInfos {
	private String name;
	private String userName;
	private String passWord;
	private String date;
	
	public ApplicationInfos() {
	}

	public ApplicationInfos(String name, String userName, String passWord, String date) {
		this.name = name;
		this.userName = userName;
		this.passWord = passWord;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	

}
