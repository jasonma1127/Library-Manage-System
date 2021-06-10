package testing;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import dataType.*;
import development.jdbcConnection;

public class testShowBooksBorrowedByUser {

	@Test
	public void test() {
		Connection myConnection = jdbcConnection.dataBaseConnection();
		UserInfo userInfo = new UserInfo("dodo", "870326");
		String userAccount = userInfo.getUserAccount();
		String userPassword = userInfo.getUserPassword();
		int userId = 0;
		Statement myStatement;
		ResultSet myResultSet;
		
		try {
			myStatement = myConnection.createStatement();
			myResultSet = myStatement.executeQuery("select userId from userInfo where userAccount = '"+ userAccount +"' and userPassword = '"+ userPassword +"'");
			myResultSet.next();
			userId = myResultSet.getInt("userId");
		} catch (SQLException e) {
			e.printStackTrace();
		};
		
		int count = jdbcConnection.showBooksBorrowedByUser(userInfo);
		
		try {
			myStatement = myConnection.createStatement();
			myResultSet = myStatement.executeQuery("select count(*) from loan where uId = " + userId);
			myResultSet.next();
			assertEquals(myResultSet.getInt("count(*)"), count);
		} catch (SQLException e) {
			e.printStackTrace();
		};
		
	}

}
