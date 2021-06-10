package testing;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import dataType.*;
import development.jdbcConnection;

public class testAddBookItem {

	@Test
	public void test() {
		Connection myConnection = jdbcConnection.dataBaseConnection();
		BookItem bookItem = new BookItem("Steve Jobs", "Walter Isaacson", 2, Status.AVAILABLE);
		String testBookName = bookItem.getBookName();
		String testBookAuthor = bookItem.getBookAuthor();
		jdbcConnection.addBookItem(bookItem);
		try {
			Statement myStatement = myConnection.createStatement();
			
			ResultSet myResultSet = myStatement.executeQuery("select count(*) from bookItem where bookName = '"+ testBookName +"' and bookAuthor = '"+ testBookAuthor +"'");
			myResultSet.next();
			int output = myResultSet.getInt("count(*)");
			assertEquals(1, output);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
