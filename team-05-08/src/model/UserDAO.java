package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import fileHandling.parseJson;

public class UserDAO {
	parseJson myFile;
	public UserDAO() {
		myFile = new parseJson();
	}

	public boolean addNewUser(User newAccount) throws IOException {
		List<User> users = getUserList();
		if (findUser(newAccount)==null) {
			users.add(newAccount);
			JSONArray array = convertToJSONArray(users);
			updateFile(array);
			return true;
		}
		return false;
	}
	
	public boolean resetMasterPassword(String userName, String newPassWord) {
		List<User> users = getUserList();
		int userIndex = findUserIndexByName(userName);
		if (userIndex >= 0) {
			users.get(userIndex).setPassWord(newPassWord);
			JSONArray array = convertToJSONArray(users);
			updateFile(array);
			return true;
		}
		return false;
	}
	public boolean storingKey(String userName, List<ApplicationInfos> listApp) {
		List<User> users = getUserList();
		int userIndex = findUserIndexByName(userName);
		if (userIndex >= 0) {
			users.get(userIndex).setListApp(listApp);
			JSONArray array = convertToJSONArray(users);
			updateFile(array);
			return true;
		}
		
		return false;
	};
	public User findUser(User user) {	
		List<User> userList = getUserList();
		for (User getUser: userList) {
			if (getUser.getUsername().equalsIgnoreCase(user.getUsername())
					|| getUser.getEmail().equalsIgnoreCase(user.getEmail())) {
				return getUser;
			}
		}
		return null;
	}
	public int findUserIndexByName (String username) {	
		List<User> userList = getUserList();
		for (int i = 0; i < userList.size(); i++) {
			if ( userList.get(i).getUsername().equalsIgnoreCase(username)){
				return i;
			}
		}
		return -1;
	}

	public List<User> getUserList()  {
		ObjectMapper mapper = new ObjectMapper();
		try {

			List<User> userList  = mapper.readValue(new File("src/data/userAccountInfo.json"), new TypeReference<List<User>>() {});
			return userList;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.out.println("File empty");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<User>();
	}

	@SuppressWarnings("unchecked")
	public JSONArray convertToJSONArray(List<User> users) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		JSONParser parser = new JSONParser();
		JSONArray array = new JSONArray();
		JSONObject json;
		try {
			for (User user : users) {
				String postJson = mapper.writeValueAsString(user);
				json = (JSONObject) parser.parse(postJson);
				array.add(json);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}

	public boolean updateFile(JSONArray arr) {
		File openFile = myFile.openFile("userAccountInfo.json");
		FileWriter fw;
		try {
			fw = new FileWriter(openFile, false);
			fw.write(arr.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}

