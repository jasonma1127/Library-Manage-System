package development;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import dataType.BookItem;
import dataType.UserInfo;


public class GUI {
	public static String userAccount;
	public static String userPassword;
	public static Vector<Vector<String>> rowData;
	public static String message;

	public static void main(String[] args) {
		JFrame mainFrame = new JFrame("Library Management System");          
		loginInterface(mainFrame);
	}
	
	public static void loginInterface(JFrame loginFrame) {
		JPanel loginRegisterTitlePanel = new JPanel(); 
		JLabel loginLabel = new JLabel();
		JPanel userAccountPanel = new JPanel(); 
		JLabel userAccountLable = new JLabel();
		JTextField userAccountTextField = new JTextField(15);
		JPanel userPasswordPanel = new JPanel();
		JLabel userPasswordLable = new JLabel();
		JTextField userPasswordTextField = new JTextField(15);
		JPanel errorMessagePanel = new JPanel();
		JLabel messageLabel = new JLabel();
		JPanel loginButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton signInButton = new JButton();
		JButton registerButton = new JButton();
		
		
		loginFrame.setSize(600, 400);                      
		loginFrame.setLocationRelativeTo(null);
		loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//login
		loginLabel.setText("Login");
		loginLabel.setFont(new Font(null, Font.PLAIN, 50));
		loginRegisterTitlePanel.add(loginLabel);
		
		userAccountLable.setText("User Account : ");
		userAccountLable.setFont(new Font(null, Font.PLAIN, 25));
		
		userAccountTextField.setFont(new Font(null, Font.PLAIN, 25));
		userAccountPanel.add(userAccountLable);
		userAccountPanel.add(userAccountTextField);
		
		userPasswordLable.setText("User Password : ");
		userPasswordLable.setFont(new Font(null, Font.PLAIN, 25));
		
		userPasswordTextField.setFont(new Font(null, Font.PLAIN, 25));
		userPasswordPanel.add(userPasswordLable);
		userPasswordPanel.add(userPasswordTextField);
		
		messageLabel.setFont(new Font(null, Font.PLAIN, 25));
		errorMessagePanel.add(messageLabel);
		
		signInButton.setText("Sign In");
		signInButton.setPreferredSize(new Dimension(120, 50));
		signInButton.setFont(new Font(null, Font.PLAIN, 20));
		registerButton.setText("Register");
		registerButton.setPreferredSize(new Dimension(120, 50));
		registerButton.setFont(new Font(null, Font.PLAIN, 20));
		loginButtonPanel.add(signInButton);
		loginButtonPanel.add(registerButton);

		signInButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				userAccount = userAccountTextField.getText();
				userPassword = userPasswordTextField.getText();
				UserInfo userInfo = new UserInfo(userAccount, userPassword);
				boolean status = jdbcConnection.userLogin(userInfo);
				
				if(status) {
					loginFrame.setVisible(false);
					userInterface();
				}else {
					messageLabel.setText(message);
					messageLabel.setForeground(Color.RED);
				}
			}
		});
		
		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loginLabel.setVisible(false);
				loginButtonPanel.setVisible(false);
				messageLabel.setText("");
				registerInterface(loginFrame);
			}
		});	
		
        Box loginBox = Box.createVerticalBox();
        loginBox.add(loginRegisterTitlePanel);
        loginBox.add(userAccountPanel);
        loginBox.add(userPasswordPanel);
        loginBox.add(errorMessagePanel);
        loginBox.add(loginButtonPanel);

        loginFrame.setContentPane(loginBox);
	    loginFrame.setVisible(true); 
	}
	
	public static void registerInterface(JFrame registerFrame) {
		JPanel loginRegisterTitlePanel = new JPanel(); 
		JLabel registerLabel = new JLabel();
		JPanel userAccountPanel = new JPanel(); 
		JLabel userAccountLable = new JLabel();
		JTextField userAccountTextField = new JTextField(15);
		JPanel userPasswordPanel = new JPanel();
		JLabel userPasswordLable = new JLabel();
		JTextField userPasswordTextField = new JTextField(15);
		JPanel errorMessagePanel = new JPanel();
		JLabel errorMessageLabel = new JLabel();
		JPanel registerButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton signUpButton = new JButton();
		JButton loginButton = new JButton();	
		
		userAccountLable.setText("User Account : ");
		userAccountLable.setFont(new Font(null, Font.PLAIN, 25));
		
		userAccountTextField.setFont(new Font(null, Font.PLAIN, 25));
		userAccountPanel.add(userAccountLable);
		userAccountPanel.add(userAccountTextField);
		
		userPasswordLable.setText("User Password : ");
		userPasswordLable.setFont(new Font(null, Font.PLAIN, 25));
		
		userPasswordTextField.setFont(new Font(null, Font.PLAIN, 25));
		userPasswordPanel.add(userPasswordLable);
		userPasswordPanel.add(userPasswordTextField);
		
		errorMessageLabel.setFont(new Font(null, Font.PLAIN, 25));
		errorMessagePanel.add(errorMessageLabel);
		
		//register
		registerLabel.setText("Register");
		registerLabel.setFont(new Font(null, Font.PLAIN, 50));
		loginRegisterTitlePanel.add(registerLabel);
		
		signUpButton.setText("Sign Up");
		signUpButton.setPreferredSize(new Dimension(120, 50));
		signUpButton.setFont(new Font(null, Font.PLAIN, 20));
		loginButton.setText("Login");
		loginButton.setPreferredSize(new Dimension(120, 50));
		loginButton.setFont(new Font(null, Font.PLAIN, 20));
		registerButtonPanel.add(signUpButton);
		registerButtonPanel.add(loginButton);
		
		signUpButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				userAccount = userAccountTextField.getText();
				userPassword = userPasswordTextField.getText();
				
				if(userAccount.isEmpty() || userPassword.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please do not leave blank", "Failure", JOptionPane.ERROR_MESSAGE);
				} else {
					UserInfo userInfo = new UserInfo(userAccount, userPassword);
					boolean status = jdbcConnection.userRegister(userInfo);
					
					if(status) {
						errorMessageLabel.setText(message);
						errorMessageLabel.setForeground(Color.BLUE);
					}else {
						errorMessageLabel.setText(message);
						errorMessageLabel.setForeground(Color.RED);
					}
				}
			}
		});
		
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				registerLabel.setVisible(false);
				registerButtonPanel.setVisible(false);		
				errorMessageLabel.setText("");
				loginInterface(registerFrame);
			}
		});	
		
		
        Box loginBox = Box.createVerticalBox();
        loginBox.add(loginRegisterTitlePanel);
        loginBox.add(userAccountPanel);
        loginBox.add(userPasswordPanel);
        loginBox.add(errorMessagePanel);
        loginBox.add(registerButtonPanel);
        
        registerFrame.setContentPane(loginBox);
        registerFrame.setVisible(true); 
	}
	
	
	public static void userInterface() {
		JFrame userFrame = new JFrame("Library Management System");
		JPanel userNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel userNameLable = new JLabel();
		
		JPanel functionButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton viewAllButton = new JButton();
		JButton myBookButton = new JButton();		
		JButton addBookButton = new JButton();
		JButton signOutButton = new JButton();
		
		JPanel serachBookItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel bookNameLabel = new JLabel();
		JTextField bookNameTextField = new JTextField(15);
		JLabel bookAuthorLabel = new JLabel();
		JTextField bookAuthorTextField = new JTextField(15);
		JButton searchButton = new JButton();
			
		DefaultTableModel bookDetailsModel = new DefaultTableModel(rowData, bookDetailsTableColumnNames());
		JTable table = new JTable(bookDetailsModel);
		JScrollPane bookDetailsScroll=new JScrollPane(table);		
		
		//userFrame
	    userFrame.setSize(880, 600);             
	    userFrame.setLocationRelativeTo(null);             
	    userFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
		
		userNameLable.setText("Welcome " + userAccount);
		userNameLable.setFont(new Font(null, Font.PLAIN, 30));
		userNamePanel.add(userNameLable);
		
		//viewAllButton
		functionButtonPanel.add(viewAllButton);
		setViewAllButton(viewAllButton, table);
		
		//myBookButton
		functionButtonPanel.add(myBookButton);
		setMyBookButton(myBookButton, table);
		if(userAccount.equals("admin")) myBookButton.setVisible(false);
		
		//addBookButton
		addBookButton.setVisible(false);
		functionButtonPanel.add(addBookButton);
		if(userAccount.equals("admin")) setAddBookButton(addBookButton);
		
		//logoutButton
		functionButtonPanel.add(signOutButton);
		setSignOutButton(signOutButton, userFrame);
		
		//search
		bookNameLabel.setText("Book Name : ");
		bookNameLabel.setFont(new Font(null, Font.PLAIN, 20));
		bookNameTextField.setFont(new Font(null, Font.PLAIN, 20));
		bookAuthorLabel.setText("Author : ");	
		bookAuthorLabel.setFont(new Font(null, Font.PLAIN, 20));
		bookAuthorTextField.setFont(new Font(null, Font.PLAIN, 20));
		serachBookItemPanel.add(bookNameLabel);
		serachBookItemPanel.add(bookNameTextField);
		serachBookItemPanel.add(bookAuthorLabel);
		serachBookItemPanel.add(bookAuthorTextField);
		serachBookItemPanel.add(searchButton);
		setSearchButton(searchButton, table, bookNameTextField, bookAuthorTextField);	

		table.setRowHeight(30);
		table.setFont(new Font(null, Font.PLAIN, 16));
		table.getTableHeader().setFont(new Font(null, Font.PLAIN, 20));		

		Box userBoxv = Box.createVerticalBox();
		userBoxv.add(userNamePanel);
		userBoxv.add(functionButtonPanel);
		userBoxv.add(serachBookItemPanel);
		userBoxv.add(bookDetailsScroll);
		userBoxv.add(Box.createVerticalStrut(20));
		
		Box userBoxh = Box.createHorizontalBox();
		userBoxh.add(Box.createHorizontalStrut(20));
		userBoxh.add(userBoxv);
		userBoxh.add(Box.createHorizontalStrut(20));
		
		userFrame.setContentPane(userBoxh);
		userFrame.pack();
		userFrame.setVisible(true);
	}

	
	public static Vector<String> bookDetailsTableColumnNames() {
		Vector<String> columnNames = new Vector<>();
		columnNames.add("Book Name");
		columnNames.add("Author");
		columnNames.add("Inventory");
		columnNames.add("Status");
		columnNames.add("Borrow"); 
		return columnNames;
	}
	
	public static Vector<String> myBookTableColumnNames() {
		Vector<String> columnNames = new Vector<>();
		columnNames.add("Book Name");
		columnNames.add("Author");
		columnNames.add("Return"); 
		return columnNames;
	}
	
	public static void doBorrow(JTable table) {
		Action borrowBook = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf(e.getActionCommand());
		        String bookName = rowData.get(modelRow).get(0);
		        String bookAuthor = rowData.get(modelRow).get(1);
		        int result = JOptionPane.showConfirmDialog(
                        null,
                        "Brrow " + bookName + " by " + bookAuthor,
                        "Confirm",
                        JOptionPane.YES_NO_OPTION
                );
                
		        if(result == 0) {
		        	UserInfo userInfo = new UserInfo(userAccount, userPassword);
		        	BookItem bookItem = new BookItem(bookName, bookAuthor, 0, null);
		        	Boolean status = jdbcConnection.borrowBooks(userInfo, bookItem);
		        	if(status) {
		        		JOptionPane.showMessageDialog(
		        				null,
		        				message,
		        				"Success",
		        				JOptionPane.INFORMATION_MESSAGE
		        		);
		        		
		        		//Update table
		        		jdbcConnection.showBookItems();
		        		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		        		tableModel.setRowCount(0);
						for(int i = 0; i < rowData.size(); i ++) {

							Object[] objects = {rowData.get(i).get(0), rowData.get(i).get(1), rowData.get(i).get(2), rowData.get(i).get(3), "Borrow"};
							tableModel.addRow(objects);
						}
		        	} else {
		        		JOptionPane.showMessageDialog(
		        				null,
		        				message,
		        				"Failure",
		        				JOptionPane.WARNING_MESSAGE
		        		);
					}
		        }

		    }
		};
		ButtonColumn buttonColumn = new ButtonColumn(table, borrowBook, 4);
	}
	
	public static void doReturn(JTable table) {
		Action borrowBook = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf(e.getActionCommand());
		        String bookName = rowData.get(modelRow).get(0);
		        String bookAuthor = rowData.get(modelRow).get(1);
		        int result = JOptionPane.showConfirmDialog(
                        null,
                        "Return " + bookName + " by " + bookAuthor,
                        "Confirm",
                        JOptionPane.YES_NO_OPTION
                );
                
		        if(result == 0) {
		        	UserInfo userInfo = new UserInfo(userAccount, userPassword);
		        	BookItem bookItem = new BookItem(bookName, bookAuthor, 0, null);
		        	Boolean status = jdbcConnection.returnBooks(userInfo, bookItem);
		        	if(status) {
		        		JOptionPane.showMessageDialog(
		        				null,
		        				message,
		        				"Success",
		        				JOptionPane.INFORMATION_MESSAGE
		        		);
		        		
		        		//Update table
		        		jdbcConnection.showBooksBorrowedByUser(userInfo);
		        		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		        		tableModel.setRowCount(0);
						for(int i = 0; i < rowData.size(); i ++) {

							Object[] objects = {rowData.get(i).get(0), rowData.get(i).get(1), "Return"};
							tableModel.addRow(objects);
						}
		        	} else {
		        		JOptionPane.showMessageDialog(
		        				null,
		        				message,
		        				"Failure",
		        				JOptionPane.WARNING_MESSAGE
		        		);
					}
		        }

		    }
		};
		ButtonColumn buttonColumn = new ButtonColumn(table, borrowBook, 2);
	}
	
	public static void setViewAllButton(JButton viewAllButton, JTable table) {
		viewAllButton.setText("View All");
		viewAllButton.setPreferredSize(new Dimension(120, 40));
		viewAllButton.setFont(new Font(null, Font.PLAIN, 20));
		
		viewAllButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jdbcConnection.showBookItems();
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				tableModel.setRowCount(0);
				tableModel.setColumnCount(0);
				Vector<String> columnNames = bookDetailsTableColumnNames();
				for (int i = 0; i < columnNames.size(); i++) {
					tableModel.addColumn(columnNames.get(i));
				}
				doBorrow(table);
				
				for(int i = 0; i < rowData.size(); i ++) {

					Object[] objects = {rowData.get(i).get(0), rowData.get(i).get(1), rowData.get(i).get(2), rowData.get(i).get(3), "Borrow"};
					tableModel.addRow(objects);
				}
			}
		});
	}
	
	public static void setMyBookButton(JButton myBookButton, JTable table) {
		myBookButton.setText("My Book");
		myBookButton.setPreferredSize(new Dimension(120, 40));
		myBookButton.setFont(new Font(null, Font.PLAIN, 20));
		
		myBookButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UserInfo userInfo = new UserInfo(userAccount, userPassword);
				jdbcConnection.showBooksBorrowedByUser(userInfo);
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				tableModel.setRowCount(0);
				tableModel.setColumnCount(0);
				Vector<String> columnNames = myBookTableColumnNames();
				for (int i = 0; i < columnNames.size(); i++) {
					tableModel.addColumn(columnNames.get(i));
				}
				doReturn(table);

				for(int i = 0; i < rowData.size(); i ++) {

					Object[] rowObjects = {rowData.get(i).get(0), rowData.get(i).get(1), "Return"};
					tableModel.addRow(rowObjects);
				}

			}
		});
	}
	
	public static void setAddBookButton(JButton addBookButton) {
		addBookButton.setVisible(true);
		addBookButton.setText("Add Book");
		addBookButton.setPreferredSize(new Dimension(120, 40));
		addBookButton.setFont(new Font(null, Font.PLAIN, 20));
		
		addBookButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel addBookDialogPanel = new JPanel();
				JTextField bookNameTextField = new JTextField(15);
				JTextField bookAuthorTextField = new JTextField(15);
				JTextField inventoryTextField = new JTextField(15);
				
				addBookDialogPanel.add(new Label("Book Name :"));
				addBookDialogPanel.add(bookNameTextField);
				addBookDialogPanel.add(new Label("Author :"));
				addBookDialogPanel.add(bookAuthorTextField);
				addBookDialogPanel.add(new Label("Inventory :"));
				addBookDialogPanel.add(inventoryTextField);
				
				int result = JOptionPane.showConfirmDialog(
						null,
						addBookDialogPanel,
						"Add Books",
						JOptionPane.OK_CANCEL_OPTION
				);
				
				if(result == 0) {
					String bookName = bookNameTextField.getText();
					String bookAuthor = bookAuthorTextField.getText();
					int numOfBooks;
					if(inventoryTextField.getText().isEmpty()) numOfBooks = 0;
					else numOfBooks = Integer.parseInt(inventoryTextField.getText());
					if (bookName.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please enter Book Name", "Failure", JOptionPane.ERROR_MESSAGE);
						
					}else if (bookAuthor.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please enter Author", "Failure", JOptionPane.ERROR_MESSAGE);
						
					}else if (inventoryTextField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please enter Inventory", "Failure", JOptionPane.ERROR_MESSAGE);
					}else if(numOfBooks > 0) {
						BookItem bookItem = new BookItem(bookName, bookAuthor, numOfBooks, null);
						jdbcConnection.addBookItem(bookItem);
						JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "Please add more than 0 books", "Failure", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	
	public static void setSignOutButton(JButton signOutButton, JFrame userFram) {
		signOutButton.setText("Sign Out");
		signOutButton.setPreferredSize(new Dimension(120, 40));
		signOutButton.setFont(new Font(null, Font.PLAIN, 20));
		
		signOutButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "Are you sure to sign out?", "Confirm", JOptionPane.YES_NO_OPTION);
				if(result == 0) {
					userAccount = "";
					userPassword = "";
					rowData = null;
					userFram.setVisible(false);
					main(null);
				}
			}
		});
	}
	
	public static void setSearchButton(JButton searchButton, JTable table, JTextField bookNameTextField, JTextField bookAuthorTextField) {
		searchButton.setText("Search");
		searchButton.setFont(new Font(null, Font.PLAIN, 20));
		
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String bookName = bookNameTextField.getText();
				String bookAuthor = bookAuthorTextField.getText();
				Boolean status = jdbcConnection.searchBookItem(bookName, bookAuthor);
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				tableModel.setRowCount(0);
				tableModel.setColumnCount(0);
				Vector<String> columnNames = bookDetailsTableColumnNames();
				for (int i = 0; i < columnNames.size(); i++) {
					tableModel.addColumn(columnNames.get(i));
				}
				doBorrow(table);
				
				if(status){
					for(int i = 0; i < rowData.size(); i ++) {
						Object[] objects = {rowData.get(i).get(0), rowData.get(i).get(1), rowData.get(i).get(2), rowData.get(i).get(3), "Borrow"};
						tableModel.addRow(objects);
					}
					
				} else{					
					JOptionPane.showMessageDialog(
	        				null,
	        				message,
	        				"Failure",
	        				JOptionPane.WARNING_MESSAGE
	        		);
				}
			}
		});
	}
	
}