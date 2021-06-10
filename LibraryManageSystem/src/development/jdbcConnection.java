package development;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import dataType.*;

public class jdbcConnection {
	
	public static Connection dataBaseConnection() {
		Connection myConnection = null;
		String url = "jdbc:mysql://localhost:3306/librarymanagesystemdb";
		String dbUser = "root";
		String daPassword = "1234";
		try {
			//Get a connection to DB
			myConnection = DriverManager.getConnection(url, dbUser, daPassword);
				
		} catch (Exception e) {
			e.printStackTrace();
		}

		return myConnection;
	}
	
	public static boolean userRegister(UserInfo _userInfo) {
		Connection myConnection = dataBaseConnection();
		String userAccount = _userInfo.getUserAccount();
		String userPassword = _userInfo.getUserPassword();
		boolean status = false;
		
		try {
			Statement myStatement = myConnection.createStatement();
			ResultSet myResultSet = myStatement.executeQuery("select count(*) from userinfo where userAccount = '"+userAccount+"'");
			myResultSet.next();
			
			if(myResultSet.getInt("count(*)") == 0) {
				status = true;
				myStatement.executeUpdate("insert into userInfo(userAccount, userPassword) values('"+ userAccount +"', '"+ userPassword +"')");
				GUI.message = "Nice! " + userAccount + " created successfully";
			}else {
				status = false;
				GUI.message = "Sorry! " + userAccount + " already exists";
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static boolean userLogin(UserInfo _userInfo) {
		Connection myConnection = dataBaseConnection();
		boolean status = false;
		String userAccount = _userInfo.getUserAccount();
		String userPassword = _userInfo.getUserPassword();
		
		Statement myStatement;
		try {
			myStatement = myConnection.createStatement();
			ResultSet checkLoginStatus = myStatement.executeQuery("select count(*) from userInfo where userAccount = '"+ userAccount +"' and userPassword = '"+  userPassword+"'");
			checkLoginStatus.next();
			int output = checkLoginStatus.getInt("count(*)");
			
			if(output == 1) {
				status = true;
				System.out.println(userAccount + " logged in successfully");
			}else if(output == 0) {
				status = false;
				GUI.message = "Sorry! " + userAccount + " login failed";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public static void addBookItem(BookItem _bookItem) {
		Connection myConnection = dataBaseConnection();
		
		String bookName = _bookItem.getBookName();
		String bookAuthor = _bookItem.getBookAuthor();
		int numOfBooks = _bookItem.getNumOfBooks();
		Status bookStatus = Status.AVAILABLE;
		Statement myStatement;
		try {
			myStatement = myConnection.createStatement();
			ResultSet myResultSet = myStatement.executeQuery("select count(*) from bookItem where bookName = '"+ bookName +"' and bookAuthor = '"+ bookAuthor +"'");
			myResultSet.next();
			
			if(myResultSet.getInt("count(*)") == 0) {
				myStatement.executeUpdate("insert into bookItem(bookName, bookAuthor, numOfBooks, bookStatus) values('"+ bookName +"', '"+ bookAuthor +"', '"+ numOfBooks +"', '"+ bookStatus +"')");
				System.out.println(bookName + " by " + bookAuthor + " is created successfully");
				GUI.message = bookName + " by " + bookAuthor + " is created successfully";
			}else {
				myResultSet = myStatement.executeQuery("select * from bookItem where bookName = '"+ bookName +"' and bookAuthor = '"+ bookAuthor +"'");
				myResultSet.next();
				int bookId = myResultSet.getInt("bookId");
				int origNumOfBooks = myResultSet.getInt("numOfBooks");
				int sumNumOfBooks = origNumOfBooks+numOfBooks;
				myStatement.executeUpdate("update bookItem set numOfBooks = "+ sumNumOfBooks +" where bookId = "+ bookId);
				myStatement.executeUpdate("update bookItem set bookStatus = '"+ bookStatus +"' where bookId = "+ bookId);
				System.out.println(bookName + " by " + bookAuthor + " increase to "+ sumNumOfBooks +" successfully");
				GUI.message = bookName + " by " + bookAuthor + " increase to "+ sumNumOfBooks +" successfully";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static boolean searchBookItem(String _bookName, String _bookAuthor) {
		Connection myConnection = dataBaseConnection();
		boolean status = false;
		String bookName = _bookName;
		String bookAuthor = _bookAuthor;
		Vector<Vector<String>> rowData = new Vector<Vector<String>>();
		
		Statement myStatement;
		try {
			myStatement = myConnection.createStatement();
			ResultSet myResultSet = myStatement.executeQuery("select count(*) from bookItem where bookName = '"+ bookName +"' or bookAuthor = '"+ bookAuthor +"'");
			myResultSet.next();
			int output = myResultSet.getInt("count(*)");
			
			if(output == 1) {
				status = true;
				myResultSet = myStatement.executeQuery("select * from bookItem where bookName = '"+ bookName +"' or bookAuthor = '"+ bookAuthor +"'");
				
				while(myResultSet.next()) {
					Vector<String> data = new Vector<String>();
					data.add(myResultSet.getString("bookName"));
					data.add(myResultSet.getString("bookAuthor"));
					data.add(myResultSet.getString("numOfBooks"));
					data.add(myResultSet.getString("bookStatus"));

					rowData.add(data);				
				}
				GUI.rowData = rowData;

			}else if(output == 0) {
				status = false;
				if((bookAuthor).isEmpty()) GUI.message = bookName + " not found";
				else if((bookName).isEmpty()) GUI.message = bookAuthor + " not found";
				else GUI.message = bookName + " by " + bookAuthor + " is not found";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static boolean borrowBooks(UserInfo _userInfo, BookItem _bookItem) {
		boolean status = false;
		Connection myConnection = dataBaseConnection();
		int userId = 0;
		String userAccount = _userInfo.getUserAccount();
		String userPassword = _userInfo.getUserPassword();
		int bookId = 0;
		String bookName = _bookItem.getBookName();
		String bookAuthor = _bookItem.getBookAuthor();
		int numOfBooks = 0;
		String bookStatus = null ;
		Statement myStatement;
		ResultSet myResultSet;
		
		try {
			myStatement = myConnection.createStatement();
			myResultSet = myStatement.executeQuery("select * from bookItem where bookName = '"+ bookName +"' and bookAuthor = '"+ bookAuthor +"'");
			myResultSet.next();
			bookId = myResultSet.getInt("bookId");
			bookStatus = myResultSet.getString("bookStatus");
			numOfBooks = myResultSet.getInt("numOfBooks");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if((bookStatus).equals("AVAILABLE")) {
			try {
				myStatement = myConnection.createStatement();				
				myResultSet = myStatement.executeQuery("select userId from userInfo where userAccount = '"+ userAccount +"' and userPassword = '"+ userPassword +"'");
				myResultSet.next();
				userId = myResultSet.getInt("userId");
				
				myResultSet = myStatement.executeQuery("select count(*) from loan where uId = " + userId + " and bId = " + bookId);
				myResultSet.next();
				if(myResultSet.getInt("count(*)") == 0) {
					status = true;
					numOfBooks = numOfBooks - 1;
					myStatement.executeUpdate("update bookItem set numOfBooks = "+ numOfBooks +" where bookId = " + bookId);
					if(numOfBooks == 0) {
						myStatement.executeUpdate("update bookItem set bookStatus = 'LOANED' where bookId = " + bookId);
					}
					
					myStatement.executeUpdate("insert into loan(uId, bId) values("+ userId +", "+ bookId +")");
					GUI.message = bookName + " by " + bookAuthor + " loan successfully";
				}else {
					status = false;
					GUI.message = userAccount + " has already borrowed the book " + bookName + " by " + bookAuthor;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			status = false;
			GUI.message = bookName + " by " + bookAuthor + " is loaded";
		}
		
		return status;
	}
	
	public static boolean returnBooks(UserInfo _userInfo, BookItem _bookItem) {
		int userId = 0;
		String userAccount = _userInfo.getUserAccount();
		String userPassword = _userInfo.getUserPassword();
		int bookId = 0;
		String bookName = _bookItem.getBookName();
		String bookAuthor = _bookItem.getBookAuthor();
		int numOfBooks = 0;
		int checkLoanRelationExists = 0;
		Boolean status;
		
		Connection myConnection = dataBaseConnection();
		Statement myStatement;
		ResultSet myResultSet;
				
		//get bookId and numOfBooks
		try {			
			myStatement = myConnection.createStatement();
			myResultSet = myStatement.executeQuery("select * from bookItem where bookName = '"+ bookName +"' and bookAuthor = '"+ bookAuthor +"'");
			myResultSet.next();
			bookId = myResultSet.getInt("bookId");
			numOfBooks = myResultSet.getInt("numOfBooks");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//get userId
		try {			
			myStatement = myConnection.createStatement();
			myResultSet = myStatement.executeQuery("select * from userInfo where userAccount = '"+ userAccount +"' and userPassword = '"+ userPassword +"'");
			myResultSet.next();
			userId = myResultSet.getInt("userId");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//check loan relation exists
		try {			
			myStatement = myConnection.createStatement();
			myResultSet = myStatement.executeQuery("select count(*) from loan where uId = " + userId + " and bId = " + bookId);
			myResultSet.next();
			checkLoanRelationExists = myResultSet.getInt("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(checkLoanRelationExists > 0) {
			//increase numOfBooks + 1 and change bookStatus into AVAILABLE
			//drop loan relation
			try {			
				numOfBooks = numOfBooks + 1;
				myStatement = myConnection.createStatement();
				myStatement.executeUpdate("update bookItem set numOfBooks = " + numOfBooks + " where bookId = " + bookId);
				
				myStatement.executeUpdate("update bookItem set bookStatus = 'AVAILABLE' where bookId = " + bookId);
				
				myStatement.executeUpdate("delete from loan where uId = " + userId + " and bId = " + bookId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			status = true;
			GUI.message = bookName + " by " + bookAuthor + " return successfully";
		} else {
			status = false;
			GUI.message = userAccount + " didn't borrowed the book " + bookName + " by " + bookAuthor;
		}
		return status;
	}
	
	public static int showBooksBorrowedByUser(UserInfo _userInfo) {
		String userAccount = _userInfo.getUserAccount();
		String userPassword = _userInfo.getUserPassword();
		int userId = 0;
		int count = 0;
		ArrayList<Integer> bookIdArray = new ArrayList<Integer>();
		Vector<Vector<String>> rowData = new Vector<Vector<String>>();
		
		Connection myConnection = dataBaseConnection();
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
		
		try {
			myStatement = myConnection.createStatement();
			myResultSet = myStatement.executeQuery("select bId from loan where uId = " + userId);

			while(myResultSet.next()) {
				bookIdArray.add(myResultSet.getInt("bId"));
			}
			
			for(int i = 0; i < bookIdArray.size(); i ++) {
				myResultSet = myStatement.executeQuery("select * from bookItem where bookId = " + bookIdArray.get(i));
				myResultSet.next();
				Vector<String> data = new Vector<String>();
				data.add(myResultSet.getString("bookName"));
				data.add(myResultSet.getString("bookAuthor"));
				
				rowData.add(data);
			}
			GUI.rowData = rowData;
				
			myResultSet = myStatement.executeQuery("select count(*) from loan where uId = " + userId);
			myResultSet.next();
			count = myResultSet.getInt("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public static int showBookItems() {
		Connection myConnection = jdbcConnection.dataBaseConnection();
		ResultSet myResultSet;
		int count = 0;
		Vector<Vector<String>> rowData = new Vector<Vector<String>>();
		
		try {
			Statement myStatement = myConnection.createStatement();
			myResultSet = myStatement.executeQuery("select count(*) from bookItem");
			myResultSet.next();
			count = myResultSet.getInt("count(*)");
			
			myResultSet = myStatement.executeQuery("select * from bookItem");
			
			while(myResultSet.next()) {
				Vector<String> data = new Vector<String>();
				data.add(myResultSet.getString("bookName"));
				data.add(myResultSet.getString("bookAuthor"));
				data.add(myResultSet.getString("numOfBooks"));
				data.add(myResultSet.getString("bookStatus"));

				rowData.add(data);				
			}
			GUI.rowData = rowData;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
}
