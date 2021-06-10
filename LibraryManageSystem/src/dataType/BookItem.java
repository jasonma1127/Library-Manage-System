package dataType;
import dataType.Status;
public class BookItem {
	private String bookName;
	private String bookAuthor;
	private int numOfBooks;
	private Status bookStatus;
	
	public BookItem(String _bookName, String _bookAuthor, int _numOfBooks, Status _status) {
		this.bookName = _bookName;
		this.bookAuthor = _bookAuthor;
		this.numOfBooks = _numOfBooks;
		this.bookStatus = _status;
	}
	
	public String getBookName() {
		return bookName;
	}
	
	public String getBookAuthor() {
		return bookAuthor;
	}
	
	public int getNumOfBooks() {
		return numOfBooks;
	}
	
	public Status getBookStatus() {
		return bookStatus;
	}
}
