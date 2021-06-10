package testing;

import static org.junit.Assert.*;
import dataType.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;


import development.jdbcConnection;

public class testUserLogin {

	@Test
	public void test() {
		int output;
		UserInfo userInfo = new UserInfo("dodo", "870326");
		String testUserAccount = userInfo.getUserAccount();
		String testUserPassword = userInfo.getUserPassword();
		Connection myConnection = jdbcConnection.dataBaseConnection();
		boolean status = jdbcConnection.userLogin(userInfo);
		
		try {
			Statement myStatement = myConnection.createStatement();
			ResultSet checkLoginStatus = myStatement.executeQuery("select count(*) from userInfo where userAccount = '"+ testUserAccount +"' and userPassword = '"+ testUserPassword +"'");
			checkLoginStatus.next();
			output = checkLoginStatus.getInt("count(*)");
			
			if(status) {
				assertEquals(1, output);
			}else {
				assertEquals(0, output);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
