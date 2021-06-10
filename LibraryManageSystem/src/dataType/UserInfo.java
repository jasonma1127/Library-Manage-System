package dataType;

public class UserInfo {
	private String userAccount;
	private String userPassword;
	
	public UserInfo(String _userAccount, String _userPassword) {
		this.userAccount = _userAccount;
		this.userPassword = _userPassword;
	}
	
	public String getUserAccount() {
		return userAccount;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
}
