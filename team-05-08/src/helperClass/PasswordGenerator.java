package helperClass;

import java.security.SecureRandom;

public class PasswordGenerator {
	private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
	private static final String NUMERIC = "0123456789";
	private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";
	private static final int PASSWORD_LENGTH = 10;
	private static SecureRandom random = new SecureRandom();
	private String randomPassword;

	public PasswordGenerator() {
		setRandomPassword(PASSWORD_LENGTH, randomPassword, SPECIAL_CHARS + NUMERIC + ALPHA + ALPHA_CAPS);
	}

	public void setRandomPassword(int length, String randomPassword, String password) {
		String result = "";
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(password.length());
			result += password.charAt(index);
		}
		this.randomPassword = result;
	}

	public String getRandomPassword() {
		return randomPassword;
	}

}
