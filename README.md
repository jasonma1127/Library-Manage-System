# Library-Manage-System
## Introduction

Implement a simple Library Manage System with the following function :

* Create user
* Login in user
* Sign out user
* View all books
* View all books borrowed by user
* Add bookItems
* Borrowed bookItems
* Return bookItems
* Search bookItems

Using TDD (Test-Driven Development) to develope

## Set Up
1. Download and setup MySQL  
Reference : https://clay-atlas.com/blog/2019/11/16/mysql-mysqlworkbench-tutorial-download-install-steps/

2. Import Database  
Import `Dump20210610.sql` in to database  
`Toolbar : Server >> Data Import`

3. Set Database userAccount and userPassword  
`File : LibraryManageSystem/src/development/jdbcConnection.java`  
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
