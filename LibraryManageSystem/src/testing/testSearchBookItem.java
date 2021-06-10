package testing;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import development.jdbcConnection;

public class testSearchBookItem {

	@Test
	public void test() {
		Connection myConnection = jdbcConnection.dataBaseConnection();
		String bookName = "Elon Musk";
		String bookAuthor = "Ashlee Vance";
		Boolean status = jdbcConnection.searchBookItem(bookName, bookAuthor);
		try {
			Statement myStatement = myConnection.createStatement();
			ResultSet myResultSet = myStatement.executeQuery("select count(*) from bookItem where bookName = '"+ bookName +"' or bookAuthor = '"+ bookAuthor +"'");
			myResultSet.next();
			int output = myResultSet.getInt("count(*)");
			
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
