package testing;

import static org.junit.Assert.*;
import dataType.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import development.jdbcConnection;

import org.junit.Test;

public class testUserRegister {

	@Test
	public void test() {
		UserInfo userInfo = new UserInfo("willy", "0907");
		Connection myConnection = jdbcConnection.dataBaseConnection();
		String testUserAccount = userInfo.getUserAccount();
		jdbcConnection.userRegister(userInfo);
		try {
			Statement myStatement = myConnection.createStatement();
			
			ResultSet myResultSet = myStatement.executeQuery("select count(*) from userinfo where userAccount = '"+testUserAccount+"'");
			
			myResultSet.next();
			int output = myResultSet.getInt("count(*)");
			assertEquals(1, output);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
