package testing;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import development.jdbcConnection;

public class testShowBookItems {

	@Test
	public void test() {
		Connection myConnection = jdbcConnection.dataBaseConnection();
		int actualCount = jdbcConnection.showBookItems();
		try {
			Statement myStatement = myConnection.createStatement();
			ResultSet myResultSet = myStatement.executeQuery("select count(*) from bookItem");
			myResultSet.next();
			int expectedCount = myResultSet.getInt("count(*)");
			
			assertEquals(expectedCount, actualCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

}
