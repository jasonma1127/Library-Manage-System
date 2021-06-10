# Library-Manage-System
## Introduction

1. Implement a simple Library Manage System with the following function :

* Create user
* Login in user
* Sign out user
* View all books
* View all books borrowed by user
* Add bookItems
* Borrowed bookItems
* Return bookItems
* Search bookItems

2. Using TDD (Test-Driven Development) to develope  
`File : LibraryManageSystem/src/testing`  
* AllTests.java
* testAddBookItem.java
* testBorrowBooks.java
* testReturnBooks.java
* testSearchBookItem.java
* testShowBookItems.java
* testShowBooksBorrowedByUser.java
* testUserLogin.java
* testUserRegister.java  
#### execute `AllTests.java` to test all Unit

## Setup
1. Download and setup MySQL  
Reference : https://clay-atlas.com/blog/2019/11/16/mysql-mysqlworkbench-tutorial-download-install-steps/

2. Import Database  
Import `Dump20210610.sql` into database  
`Toolbar : Server >> Data Import`  
  
![image](https://github.com/jasonma1127/Library-Manage-System/blob/main/image/mysqlDataImport.jpg)\
![image](https://github.com/jasonma1127/Library-Manage-System/blob/main/image/mysqlDataImportStart.jpg)

3. Set Database userAccount and userPassword  
`File : LibraryManageSystem/src/development/jdbcConnection.java`  
Modify `String dbUser = "root";` `String daPassword = "1234";` to your own dbUser and dbPassword  
```java
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
```

4. Execute `File : LibraryManageSystem/src/development/GUI.java` and it **WILL** work !!!
