package testing;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import dataType.BookItem;
import dataType.Status;
import dataType.UserInfo;
import development.jdbcConnection;

public class testReturnBooks {

	@Test
	public void test() {
		Connection myConnection = jdbcConnection.dataBaseConnection();
		UserInfo userInfo = new UserInfo("vicky", "0412");
		BookItem bookItem = new BookItem("Steve Jobs", "Walter Isaacson", 0, Status.AVAILABLE);
		int userId = 0;
		String userAccount = userInfo.getUserAccount();
		String userPassword = userInfo.getUserPassword();
		int bookId = 0;
		String bookName = bookItem.getBookName();
		String bookAuthor = bookItem.getBookAuthor();
		Statement myStatement;
		ResultSet myResultSet;
		int beforeNumOfBooks = 0;
		
		try {
			myStatement = myConnection.createStatement();
			myResultSet = myStatement.executeQuery("select userId from userInfo where userAccount = '"+ userAccount +"' and userPassword = '"+ userPassword +"'");
			myResultSet.next();
			userId = myResultSet.getInt("userId");
			
			myResultSet = myStatement.executeQuery("select bookId from bookItem where bookName = '"+ bookName +"' or bookAuthor = '"+ bookAuthor +"'");
			myResultSet.next();
			bookId = myResultSet.getInt("bookId");
			
			myResultSet = myStatement.executeQuery("select numOfBooks from bookItem where bookName = '"+ bookName +"' or bookAuthor = '"+ bookAuthor +"'");
			myResultSet.next();
			beforeNumOfBooks = myResultSet.getInt("numOfBooks");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		boolean status = jdbcConnection.returnBooks(userInfo, bookItem);
		
		if(status) {
			try {
				//check書的數量是否增加
				myStatement = myConnection.createStatement();
				myResultSet = myStatement.executeQuery("select numOfBooks, bookStatus from bookItem where bookName = '"+ bookName +"' or bookAuthor = '"+ bookAuthor +"'");
				myResultSet.next();
				int afterNumOfBooks = myResultSet.getInt("numOfBooks");
				String afterBookStatus = myResultSet.getString("bookStatus");
				
				assertEquals(afterNumOfBooks, beforeNumOfBooks+1);
				
				if(afterNumOfBooks != 0) assertEquals("AVAILABLE", afterBookStatus);
			
				//check是否有LOAN關係				
				myResultSet = myStatement.executeQuery("select count(*) from loan where uId = '"+ userId +"' and bId = '"+ bookId +"'");
				myResultSet.next();
				
				assertEquals(0, myResultSet.getInt("count(*)"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}

}
